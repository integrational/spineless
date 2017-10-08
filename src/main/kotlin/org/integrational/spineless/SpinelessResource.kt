package org.integrational.spineless

import javax.ws.rs.DefaultValue
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/spineless")
class SpinelessResource {

    object Defaults {
        const val status = 200
        const val type = MediaType.APPLICATION_JSON
        const val body = """{ "status" : "OK"}"""
        const val delay = 0L
    }

    // JAX-RS automatically adds support for @HEAD and @OPTIONS
    @GET
    fun respond(@QueryParam("status") @DefaultValue(Defaults.status.toString()) status: Int,
                @QueryParam("type") @DefaultValue(Defaults.type) type: String,
                @QueryParam("body") @DefaultValue(Defaults.body) body: String,
                @QueryParam("delay") @DefaultValue(Defaults.delay.toString()) delay: Long): Response {
        Thread.sleep(delay)
        return Response.status(status).type(type).entity(body).build()
    }
}