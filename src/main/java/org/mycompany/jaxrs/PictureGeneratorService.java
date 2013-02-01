package org.mycompany.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.mycompany.jaxrs.decrypt.FenDecryptor;
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
    public Response chessBoard(@QueryParam("fen") String fen, @QueryParam("theme") String theme)
    {
        byte[] image;
        try {
            if (fen == null) {
                throw new IllegalStateException("Please, write FEN in \'fen\' query parameter!");
            }
            FenDecryptor decryptor = new FenDecryptor();
            Character[][] position = decryptor.decryptFen(fen);
            PictureGenerator generator = new PictureGenerator();
            generator.setTheme(new ThemeFactory().createTheme(theme));

            image = generator.createBoard(position);
        } catch (IllegalStateException exc) {
            image = new ExceptionImageGenerator().createErrorPicture(exc.getMessage());
        }
        return Response.ok(image, MediaType.WILDCARD_TYPE).build();
    }
}
