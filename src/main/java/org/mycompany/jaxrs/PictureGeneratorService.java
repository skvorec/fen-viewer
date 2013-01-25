package org.mycompany.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author artyukhov
 */
@Path("/")
public class PictureGeneratorService
{
    @GET
    @Path("/")
    @Produces("image/*")
    public Response images(@QueryParam("fen") String fen)
    {
        PictureGenerator generator = new PictureGenerator();
        byte[] image;
        try {
            image = generator.createBoard(fen);
        } catch (IllegalStateException exc) {
            image = new ExceptionImageGenerator().createErrorPicture(exc.getMessage());
        }
        return Response.ok(image, MediaType.WILDCARD_TYPE).build();
    }
}