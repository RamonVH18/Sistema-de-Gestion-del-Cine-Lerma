/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.MongoConexion;
import DTOs.GananciaSalaDTO;
import Excepciones.AsientoFuncion.FalloCreacionAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloEliminacionAsientosFuncion;
import Excepciones.AsientoFuncion.FalloMostrarAsientosFuncionException;
import Excepciones.AsientoFuncion.FalloObtencionColeccionException;
import Excepciones.AsientoFuncion.FalloOcuparAsientosFuncionException;
import Excepciones.salas.ErrorCalculoEstadisticasSalaException;
import Interfaces.IAsientoFuncionDAO;
import Interfaces.IFuncionDAO;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.sum;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Aggregates.addFields;
import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.group;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Updates.push;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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

    private final Bson projeccionGanancia = project(fields(
            excludeId(),
            include("numeroSala", "asientosVendidos", "funcionesRealizadas", "totalGanado"),
            computed("capacidad", new Document("$arrayElemAt", Arrays.asList("$infoSala.numAsientos", 0)))
    ));

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
            MongoCollection<AsientoFuncion> coleccionAF = obtenerColeccionAsientoFuncion(clienteMongo);
            // Se inserta toda la lista de asientosFuncion
            coleccionAF.insertMany(asientosFuncion);

            return asientosFuncion;
        } catch (FalloObtencionColeccionException e) {
            throw new FalloCreacionAsientosFuncionException("Hubo un error al insertar los asientos en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public Boolean eliminarAsientosFuncion(String idFuncion) throws FalloEliminacionAsientosFuncion {
        MongoClient clienteMongo = null;
        try {
            MongoCollection coleccionAf = obtenerColeccionAsientoFuncion(clienteMongo);

            Bson filtro = filtroFuncion(idFuncion, Boolean.FALSE);

            DeleteResult resultado = coleccionAf.deleteMany(filtro);

            return resultado.wasAcknowledged();
        } catch (FalloObtencionColeccionException e) {
            throw new FalloEliminacionAsientosFuncion("Hubo un error al eliminar los asientos: " + e.getMessage());
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

            List<Bson> pipeline = Arrays.asList(
                    lookup("Salas", "numSala", "numSala", "infoSala"),
                    addFields(new Field<>("idFuncionObjId",
                            new Document("$toObjectId", "$idFuncion"))),
                    lookup("Funciones", "idFuncionObjId", "_id", "infoFuncion"),
                    group("$numSala",
                            first("numeroSala", "$numSala"),
                            first("infoSala", "$infoSala"),
                            sum("asientosVendidos", new Document("$cond",
                                    Arrays.asList(new Document("$eq", Arrays.asList("$disponibilidad", false)), 1, 0)))
                    ),
                    //crearAggregateTotalGanado(),
                    new Document("$sort", new Document("numeroSala", -1)),
                    projeccionGanancia
            );

            AggregateIterable<Document> iterable = coleccionAsientoFuncion.aggregate(pipeline);

            AggregateIterable<Document> resultado = coleccionAsientoFuncion.aggregate(Arrays.asList(
                    // Paso 1: Agrupar por idFuncion y numSala para obtener combinaciones únicas
                    Aggregates.group(
                            new Document("idFuncion", "$idFuncion").append("numSala", "$numSala")
                    ),
                    // Paso 2: Agrupar por numSala y contar cuántos idFuncion únicos hay por sala
                    Aggregates.group("$_id.numSala", Accumulators.sum("cantidadFunciones", 1)),
                    new Document("$sort", new Document("numeroSala", -1))
            ));

            List<Document> documentos = iterable.into(new ArrayList<>());
            List<Document> resultados = resultado.into(new ArrayList<>());

            return crearListaGanancias(iterable);

        } catch (FalloObtencionColeccionException e) {

        }
        throw new UnsupportedOperationException("jns");
    }

    private List<GananciaSalaDTO> crearListaGanancias(AggregateIterable<Document> iterable) {
        List<GananciaSalaDTO> lista = new ArrayList<>();
        for (Document doc : iterable) {
            String numSala = doc.getString("numeroSala");
            Integer capacidad = doc.getInteger("capacidad");
            Double totalGanado = 0.0;//doc.get("totalGanado", Number.class).doubleValue();
            Integer asientosVendidos = doc.getInteger("asientosVendidos");
            Integer funcionesRealizadas = doc.getInteger("funcionesRealizadas");

            lista.add(new GananciaSalaDTO(numSala, capacidad,
                    totalGanado, asientosVendidos, funcionesRealizadas));
        }
        return lista;
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

    private Bson crearAggregateFuncionesRealizadas() {
        Date now = new GregorianCalendar(2026, Calendar.MAY, 15).getTime();

        Bson bson = group("$numSala",
                sum("funcionesRealizadas", new Document("$cond",
                        Arrays.asList(new Document("$eq", Arrays.asList("idFuncion", false)), 1, 0))));

        return bson;
    }

    private Bson crearAggregateTotalGanado() {
        Bson bson = addFields(new Field<>("totalGanado", new Document("$let",
                new Document("vars", new Document("funcionesDeSalaActual", new Document("$filter", new Document()
                        .append("input", "$infoFuncion")
                        .append("as", "f")
                        .append("cond", new Document("$eq", Arrays.asList("$$f.sala.numSala", "$numeroSala")))
                )))
                        .append("in", new Document("$sum", new Document("$map", new Document()
                                .append("input", "$$funcionesDeSalaActual")
                                .append("as", "f")
                                .append("in", new Document("$multiply", Arrays.asList(
                                        "$$f.precio",
                                        new Document("$size", new Document("$filter", new Document()
                                                .append("input", new Document("$ifNull", Arrays.asList("$$f.asientosFuncion", Collections.emptyList())))
                                                .append("as", "asiento")
                                                .append("cond", new Document("$eq", Arrays.asList("$$asiento.disponibilidad", false)))
                                        ))
                                )))
                        )))
        )));
        return bson;
    }
}
