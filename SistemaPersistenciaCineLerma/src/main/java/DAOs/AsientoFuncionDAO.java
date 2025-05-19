/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import DTOs.GananciaSalaDTO;
import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloMostrarAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloObtencionColeccionException;
import Excepciones.AsientoFuncion.FalloOcuparAsientosFuncionException;
import Excepciones.salas.BuscarSalaException;
import Excepciones.salas.ErrorCalculoEstadisticasSalaException;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IFuncionDAO;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UnwindOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Clase AsientoFuncionDAO encargada de manipular los AsientoFuncion persistidos
 * en la base de datos
 *
 * @author Ramon Valencia
 */
public class AsientoFuncionDAO implements IAsientoFuncionDAO {

    private static AsientoFuncionDAO instance; // Se crea un objeto de AsientoFuncionDAO para poder ejecutar el Singletone
    private final MongoConexion conexion = new MongoConexion(); // Se inicia una conexionMongo
    private final IFuncionDAO funcionDAO = FuncionDAO.getInstanceDAO();
    private final String nombreColeccion = "AsientosFuncion";

    /**
     * Constructor privado y vacio para la ejecuccion del SingleTone
     */
    private AsientoFuncionDAO() {
    }

    /**
     * Metodo para regresar la instancia de esta clase y que de esa manera nomas
     * exista una instancia de clase
     *
     * @return
     */
    public static AsientoFuncionDAO getInstanceDAO() {
        if (instance == null) {
            instance = new AsientoFuncionDAO();
        }
        return instance;
    }

    /**
     * Metodo para agregar nuevos Asientos de una funcion en la base de datos
     *
     * @param asientosFuncion
     * @return
     * @throws FalloCreacionAsientosFuncionException
     */
    @Override
    public List<AsientoFuncion> agregarAsientosFuncion(List<AsientoFuncion> asientosFuncion) throws FalloCreacionAsientosFuncionException {
        MongoClient clienteMongo = null;
        try {
            // Se llama al metodo para obtener la coleccion de asientos funcion de la base de datos
            MongoCollection coleccionAF = obtenerColeccionAsientoFuncion(clienteMongo);
            // Se inserta toda la lista de asientosFuncion
            coleccionAF.insertMany(asientosFuncion);

            return asientosFuncion;
        } catch (FalloObtencionColeccionException e) {
            throw new FalloCreacionAsientosFuncionException("Hubo un error al insertar los asientos en la base de datos: " + e.getMessage());
        }
    }

    /**
     * Metodo para mostrar los asientos que esten relacionados a una funcion
     *
     * @param funcion
     * @param mostrarDisponibles
     * @return
     * @throws FalloMostrarAsientosFuncionException
     */
    @Override
    public List<AsientoFuncion> mostrarListaAsientosPorFuncion(Funcion funcion, Boolean mostrarDisponibles) throws FalloMostrarAsientosFuncionException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<AsientoFuncion> coleccionAF = obtenerColeccionAsientoFuncion(clienteMongo);

            Bson filtro = filtroFuncion(funcion.getIdString(), mostrarDisponibles);

            List<AsientoFuncion> asientosFuncion = coleccionAF.find(filtro).into(new ArrayList<>());

            return asientosFuncion;

        } catch (FalloObtencionColeccionException e) {
            throw new FalloMostrarAsientosFuncionException("Hubo un error al mostrar los asientos de la funcion: " + e.getMessage());
        }
    }

    @Override
    public Boolean ocuparAsientos(List<AsientoFuncion> asientosReservados) throws FalloOcuparAsientosFuncionException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<AsientoFuncion> coleccionAF = obtenerColeccionAsientoFuncion(clienteMongo);

            Bson filtroFuncion = filtroFuncion(
                    asientosReservados.get(0).getIdFuncion(),
                    Boolean.FALSE
            );

            Bson filtroAsientosFuncion = filtroAsientoFuncion(filtroFuncion, asientosReservados);

            Bson ocuparAsiento = Updates.set("disponibilidad", false);

            UpdateResult actualizacion = coleccionAF.updateMany(filtroAsientosFuncion, ocuparAsiento);

            return actualizacion.wasAcknowledged();
        } catch (FalloObtencionColeccionException e) {
            throw new FalloOcuparAsientosFuncionException("Hubo un error al ocupar los asientos: " + e.getMessage());
        }
    }

    @Override
    public List<GananciaSalaDTO> obtenerEstadisticasDeSalas() throws ErrorCalculoEstadisticasSalaException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<Document> coleccionAsientoFuncion = obtenerColeccionDocument(clienteMongo);

            Date now = new Date();

            List<Bson> pipeline = Arrays.asList(
                    Aggregates.lookup(
                            "Salas",
                            "numSala",
                            "numSala",
                            "infoSala"
                    ),
                    Aggregates.lookup(
                            "Funciones",
                            "idFuncion",
                            "_id",
                            "infoFuncion"
                    ),
                    Aggregates.group("$numSala",
                            Accumulators.first("numeroSala", "$numSala"),
                            Accumulators.first("infoSala", "$infoSala"),
                            Accumulators.first("infoFuncion", "$infoFuncion"),
                            Accumulators.sum("asientosVendidos", new Document("$cond", Arrays.asList(
                                    new Document("$eq", Arrays.asList("$disponibilidad", false)), 1, 0
                            )))
                    ),
                    Aggregates.addFields(new Field<>("funcionesRealizadas",
                            new Document("$size", new Document("$filter", new Document()
                                    .append("input", "$infoFuncion")
                                    .append("as", "funcion")
                                    .append("cond", new Document("$and", Arrays.asList(
                                            new Document("$gt", Arrays.asList("$$funcion.fechaHora", now)),
                                            new Document("$eq", Arrays.asList("$$funcion.sala.numSala", "$numeroSala"))
                                    )))
                            ))
                    )),
                    Aggregates.addFields(new Field<>("totalGanado",
                            new Document("$sum", new Document("$map", new Document()
                                    .append("input", new Document("$filter", new Document()
                                            .append("input", "$infoFuncion")
                                            .append("as", "funcion")
                                            .append("cond", new Document("$eq", Arrays.asList("$$funcion.sala.numSala", "$numeroSala")))
                                    ))
                                    .append("as", "f")
                                    .append("in", new Document("$multiply", Arrays.asList(
                                            "$$f.precio",
                                            new Document("$size", new Document("$filter", new Document()
                                                    .append("input", "$$f.asientosFuncion") // este array debe estar en cada funcion
                                                    .append("as", "asiento")
                                                    .append("cond", new Document("$eq", Arrays.asList("$$asiento.disponibilidad", false)))
                                            ))
                                    )))
                            ))
                    )),
                    Aggregates
                            .project(Projections.fields(
                                    Projections.excludeId(),
                                    Projections.include("numeroSala"),
                                    new Document("capacidad", new Document("$arrayElemAt", Arrays.asList("$infoSala.numAsientos", 0))),
                                    Projections.include("asientosVendidos"),
                                    Projections.include("funcionesRealizadas"),
                                    Projections.include("totalGanado")
                            ))
            );

            AggregateIterable<Document> iterable = coleccionAsientoFuncion.aggregate(pipeline);
            List<Document> documentos = iterable.into(new ArrayList<>());

            List<GananciaSalaDTO> lista = new ArrayList<>();

            for (Document doc : iterable) {
                String numSala = doc.getString("numeroSala");
                Integer capacidad = doc.getInteger("capacidad");
//                Double totalGanado = doc.get("totalGanado", Number.class).doubleValue();
                Integer asientosVendidos = doc.getInteger("asientosVendidos");
                Integer funcionesRealizadas = doc.getInteger("funcionesRealizadas");

//                lista.add(new GananciaSalaDTO(numSala, capacidad,
//                        totalGanado, asientosVendidos, funcionesRealizadas));
            }

            return lista;

        } catch (FalloObtencionColeccionException e) {

        }
        throw new UnsupportedOperationException("jns");
    }

    private Bson filtroFuncion(String idFuncion, Boolean mostrarDisponibles) {
        Bson filtro = Filters.eq("idFuncion", idFuncion);
        if (mostrarDisponibles) {
            List<Bson> filtradores = Arrays.asList(
                    Filters.eq("disponibilidad", mostrarDisponibles),
                    filtro
            );
            return Filters.and(filtradores);
        }
        return filtro;
    }

    private Bson filtroAsientoFuncion(Bson filtroAF, List<AsientoFuncion> asientosFuncion) {
        List<Bson> filtradores = new ArrayList();
        for (AsientoFuncion asiento : asientosFuncion) {
            List<Bson> filtrador = Arrays.asList(
                    filtroAF,
                    Filters.eq("numAsiento", asiento.getNumAsiento())
            );
            Bson filtro = Filters.and(filtrador);
            filtradores.add(filtro);
        }
        return Filters.or(filtradores);
    }

    private MongoCollection<AsientoFuncion> obtenerColeccionAsientoFuncion(MongoClient clienteMongo) throws FalloObtencionColeccionException {
        try {
            clienteMongo = conexion.crearConexion(); // Se llama a un metodo de la clase conexion para que se cree la conexion

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<AsientoFuncion> coleccionAF = baseDatos.getCollection(nombreColeccion, AsientoFuncion.class);

            return coleccionAF;
        } catch (MongoException e) {
            throw new FalloObtencionColeccionException("Error al realizar la conexion: " + e.getMessage());
        }
    }

    private MongoCollection<Document> obtenerColeccionDocument(MongoClient clienteMongo) throws FalloObtencionColeccionException {
        try {
            clienteMongo = conexion.crearConexion(); // Se llama a un metodo de la clase conexion para que se cree la conexion

            MongoDatabase baseDatos = conexion.obtenerBaseDatos(clienteMongo);

            MongoCollection<Document> coleccionAF = baseDatos.getCollection(nombreColeccion);

            return coleccionAF;
        } catch (MongoException e) {
            throw new FalloObtencionColeccionException("Error al realizar la conexion: " + e.getMessage());
        }
    }
}
