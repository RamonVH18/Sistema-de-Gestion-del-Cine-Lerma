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
import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Accumulators.sum;
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
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import entidades.AsientoFuncion;
import entidades.Funcion;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final Bson projeccionGanancia = project(fields(
            excludeId(),
            include("numeroSala", "asientosVendidos", "funcionesRealizadas", "precioFuncion"),
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
    /**
     * Metodo para ocupar asientos o mejor dicho reservarlos
     * @param asientosReservados
     * @return
     * @throws FalloOcuparAsientosFuncionException 
     */
    @Override
    public Boolean ocuparAsientos(List<AsientoFuncion> asientosReservados) throws FalloOcuparAsientosFuncionException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<AsientoFuncion> coleccionAF = obtenerColeccionAsientoFuncion(clienteMongo);
            //Filtro que se encarga de buscar los asientos de una funcion
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
    /**
     * Metodo para obtener las estadisticas de la sala
     * @return
     * @throws ErrorCalculoEstadisticasSalaException 
     */
    @Override
    public List<GananciaSalaDTO> obtenerEstadisticasDeSalas() throws ErrorCalculoEstadisticasSalaException {
        MongoClient clienteMongo = null;
        try {
            MongoCollection<Document> coleccionAsientoFuncion = obtenerColeccionDocument(clienteMongo);
            //Tuberia par poder obtener los datos que quiero, tal y como los quiero
            List<Bson> pipeline = Arrays.asList(
                    lookup("Salas", "numSala", "numSala", "infoSala"), // Union de la coleccion de salas y asientofuncion
                    addFields(new Field<>("idFuncionObjId",
                            new Document("$toObjectId", "$idFuncion"))),
                    lookup("Funciones", "idFuncionObjId", "_id", "infoFuncion"), // Union de la coleccion de funcion y asientoFuncion
                    group("$numSala",
                            first("numeroSala", "$numSala"),
                            first("infoSala", "$infoSala"),
                            first("precioFuncion", "$infoFuncion.precio"),
                            sum("asientosVendidos", new Document("$cond",
                                    Arrays.asList(new Document("$eq", Arrays.asList("$disponibilidad", false)), 1, 0))),
                            sum("funcionesRealizadas", 1)
                    ), // Se agrupan por el num de la sala y en base a eso se obtiene la mayoria de datos
                    new Document("$sort", new Document("numeroSala", -1)),
                    projeccionGanancia
            );

            AggregateIterable<Document> iterable = coleccionAsientoFuncion.aggregate(pipeline);

            return crearListaGanancias(iterable);

        } catch (FalloObtencionColeccionException e) {
            throw new ErrorCalculoEstadisticasSalaException("Hubo un erro al calcular las estadisticas de la base de datos: " + e.getMessage());
        }
    }
    /**
     * Metodo para crear la lista de ganancias
     * Resive un iterable y en base a eso crea los demas datos
     * @param iterable
     * @return 
     */
    private List<GananciaSalaDTO> crearListaGanancias(AggregateIterable<Document> iterable) {
        List<GananciaSalaDTO> lista = new ArrayList<>();
        for (Document doc : iterable) {
            String numSala = doc.getString("numeroSala");
            
            Integer capacidad = doc.getInteger("capacidad");
            Integer asientosVendidos = doc.getInteger("asientosVendidos");
            List<Double> precioFuncion = (List<Double>) doc.get("precioFuncion");
            Double precio = precioFuncion.get(0);
            Double totalGanado = precio * asientosVendidos;
            
            
            Integer funcionesRealizadas = doc.getInteger("funcionesRealizadas");
            funcionesRealizadas = (funcionesRealizadas/capacidad);
            
            lista.add(new GananciaSalaDTO(numSala, capacidad,
                    totalGanado, asientosVendidos, funcionesRealizadas));
            
        }
        return lista;
    }
    /**
     * Metodo para crear el filtro necesario para buscar los asientos de una funcion en especifico
     * @param idFuncion
     * @param mostrarDisponibles
     * @return 
     */
    private Bson filtroFuncion(String idFuncion, Boolean mostrarDisponibles) {
        Bson filtro = Filters.eq("idFuncion", idFuncion);
        if (mostrarDisponibles) { // En caso de que mostrarDisponibles sea true entonces al filtro tambien se le añadira el hecho que los asientos esten disponibles
            List<Bson> filtradores = Arrays.asList(
                    Filters.eq("disponibilidad", mostrarDisponibles),
                    filtro
            );
            return Filters.and(filtradores);
        }
        return filtro;
    }
    /**
     * 
     * @param filtroAF
     * @param asientosFuncion
     * @return 
     */
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
