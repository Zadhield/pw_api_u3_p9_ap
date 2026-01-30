package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.application.HijoService;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.application.representation.HijoRepresentation;
import uce.edu.web.api.matricula.application.representation.LinkDto;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;
    @Inject
    private HijoService hijoService;
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstudianteRepresentation> listarTodos() {
        List<EstudianteRepresentation> test = this.estudianteService.listarTodos();

        for (EstudianteRepresentation er : test) {
            construirLinks(er);
        }

        System.out.println(test);
        return test;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EstudianteRepresentation consultarPorId(@PathParam("id") Integer iden) {
        return this.construirLinks(this.estudianteService.consultarPorId(iden));
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardar(EstudianteRepresentation estu) {
        this.estudianteService.crear(estu);
        return Response.status(Response.Status.CREATED).entity(estu).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") Integer id, EstudianteRepresentation estu) {
        this.estudianteService.actualizar(id, estu);
        return Response.status(209).entity(null).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarParcial(@PathParam("id") Integer id, EstudianteRepresentation est) {
        this.estudianteService.actualizarParcial(id, est);
    }

    @DELETE
    @Path("/{id}")
    public void borrar(@PathParam("id") Integer id) {
        this.estudianteService.eliminar(id);
    }

    @GET
    @Path("/provincia/genero")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstudianteRepresentation> buscarPorProvinciaGenero(@QueryParam("provincia") String provincia, @QueryParam("genero") String genero) {
        System.out.println("Buscar por provincia y genere XXXXXXXXXXXXX");
        return this.estudianteService.buscarPorProvinciaGenero(provincia, genero);
    }

    @GET
    @Path("/{id}/hijos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HijoRepresentation> listarPorIdEstudiante(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorIdEstudiante(id);
    }

    private EstudianteRepresentation construirLinks(EstudianteRepresentation er) {
        String self = this.uriInfo.getBaseUriBuilder().path(EstudianteResource.class).path(String.valueOf(er.getId())).build().toString();
        String hijos = this.uriInfo.getBaseUriBuilder().path(EstudianteResource.class).path(String.valueOf(er.getId())).path("hijos").build().toString();

        er.setLinks(List.of(new LinkDto(self, "self"), new LinkDto(hijos, "hijos")));

        return er;
    }
}