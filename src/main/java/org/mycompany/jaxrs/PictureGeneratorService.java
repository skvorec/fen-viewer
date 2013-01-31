package org.mycompany.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.mycompany.jaxrs.theme.ThemeFactory;

/**
 *
 * @author artyukhov
 */
@Path("/")
public class PictureGeneratorService
{
    @GET
    @Path("/chess")
    @Produces("image/*")
    public Response chessBoard(@QueryParam("fen") String fen)
    {
        PictureGenerator generator = new PictureGenerator();
        generator.setTheme(new ThemeFactory().createTheme("default"));
        byte[] image;
        try {
            image = generator.createBoard(fen);
        } catch (IllegalStateException exc) {
            image = new ExceptionImageGenerator().createErrorPicture(exc.getMessage());
        }
        return Response.ok(image, MediaType.WILDCARD_TYPE).build();
    }
}
