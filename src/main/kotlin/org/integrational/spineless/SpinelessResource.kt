package org.integrational.spineless

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/spineless")
class SpinelessResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun respond(): Response {
        return Response.ok("OK").build()
    }
}