package app.sp.metadata.converter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("attributes")
public interface CasMetadataService {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    CasMetadataResponseTO attributes(
            @QueryParam("entityId") String spEntityID);

}
