package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Produces;
import uce.edu.web.api.matricula.application.MateriaService;
import uce.edu.web.api.matricula.domain.Materia;

@Path("/materias")
public class MateriaResource {

    @Inject
    private MateriaService materiaService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> listarTodos() {
        return this.materiaService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Materia consultarPorId(@PathParam("id") Integer id) {
        return this.materiaService.consultarPorId(id);
    }

    @POST
    @Path("")
    public Response guardar(Materia materia) {
        this.materiaService.crear(materia);
        return Response.status(Response.Status.CREATED).entity(materia).build();
    }

    @PUT
    @Path("/{id}")
    public void actualizar(@PathParam("id") Integer id, Materia materia) {
        this.materiaService.actualizar(id, materia);
    }

    @PATCH
    @Path("/{id}")
    public Response actualizarParcial(@PathParam("id") Integer id, Materia materia) {
        this.materiaService.actualizarParcial(id, materia);
        return Response.status(209).entity(null).build();
    }

    @DELETE
    @Path("/{id}")
    public void borrar(@PathParam("id") Integer id) {
        this.materiaService.eliminar(id);
    }

    @GET
    @Path("/reportes/creditos-mayor-promedio")
    public List<Materia> obtenerMateriasAltaCarga() {
        return this.materiaService.reportarMateriasAltaCarga();
    }

    @GET
    @Path("/reportes/ultimo-semestre")
    public List<Materia> obtenerMateriasUltimoSemestre() {
        return this.materiaService.reportarMateriasFinCarrera();
    }
}