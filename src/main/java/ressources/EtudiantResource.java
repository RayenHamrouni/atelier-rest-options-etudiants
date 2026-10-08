package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("etudiants")
public class EtudiantResource {

    private final EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    private final OptionBusiness optionBusiness = new OptionBusiness();

    // POST /etudiants
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!etudiantBusiness.addEtudiant(etudiant)) {      // false si l'option n'existe pas
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.ok(etudiantBusiness.getAllEtudiants()).build();
    }

    // GET /etudiants/option?codeOption=1   (reponse en XML)
    // NB : chemin litteral "option" => prioritaire sur "{id}"
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new EtudiantList(etudiantBusiness.getEtudiantsByOption(option))).build();
    }

    // GET /etudiants/{id}
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiant(@PathParam("id") String id) {
        Etudiant e = etudiantBusiness.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    // PUT /etudiants/{id}
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // On remplace l'option du JSON par l'option complete connue du serveur
        Option option = optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption());
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        etudiant.setOption(option);
        if (!etudiantBusiness.updateEtudiant(id, etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // DELETE /etudiants/{id}
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (etudiantBusiness.deleteEtudiant(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
