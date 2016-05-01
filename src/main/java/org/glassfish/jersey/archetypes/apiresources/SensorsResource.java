package org.glassfish.jersey.archetypes.apiresources;

import net.sf.json.JSONArray;
import org.glassfish.jersey.archetypes.model.Sensor;
import org.glassfish.jersey.archetypes.services.ServiceFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by akhilakishore on 30/04/16.
 */
@Path("/sensors")
public class SensorsResource extends BaseApiResource {
    @GET
    @Path("/{sensorId}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorBySensorId(@PathParam("sensorId") int sensorId, @QueryParam("sessionToken")String sessionToken) throws Exception {
        final Sensor sensor = ServiceFactory.getServiceFactory().getSensorService().getSensorBySensorId(sensorId, sessionToken);
        if (sensor == null || sensor.getSensorId() == 0) {
            return generate404Response("No sensor found");
        } else {
            return genarate200Response(sensor);
        }

    }

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSensorInformation(@QueryParam("sessionToken") String sessionToken)throws Exception {
        final JSONArray allSensors = ServiceFactory.getServiceFactory().getSensorService().getAllSensorInformation(sessionToken);
        if(allSensors==null){
            return generate404Response("No sensor found");
        } else {
            return genarate200Response(allSensors);
        }
    }
}
