package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OptionResource {

    private final OptionBusiness optionBusiness = new OptionBusiness();

    // POST /options
    @POST
    public Response addOption(Option option) {
        if (option == null || !optionBusiness.addOption(option)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // GET /options  et  GET /options?domaine=Mathématiques
    @GET
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> liste = (domaine == null || domaine.isEmpty())
                ? optionBusiness.getListeOptions()
                : optionBusiness.getOptionsByDomaine(domaine);
        return Response.ok(liste).build();
    }

    // GET /options/{code}
    @GET
    @Path("{code}")
    public Response getOption(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // PUT /options/{code}
    @PUT
    @Path("{code}")
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option == null || !optionBusiness.updateOption(code, option)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // DELETE /options/{code}
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optionBusiness.deleteOption(code)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
