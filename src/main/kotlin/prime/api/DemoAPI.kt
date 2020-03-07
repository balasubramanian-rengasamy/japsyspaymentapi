package prime.api

import java.io.Serializable
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/demo")
class DemoAPI : Serializable {

    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    fun sayHello():String {
        return "hello"
    }
}