package org.integrational.spineless

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


/**
 * REST resource for the Spineless API, which responds at all resource paths with a HTTP response defined by the client in its HTTP request.
 * Reasonable defaults as specified in [Defaults] are applied.
 */
@Path("/{path}")
class SpinelessResource(
        @PathParam("path") @DefaultValue("") private val path: String,
        @QueryParam("pathHeader") @DefaultValue(Defaults.pathHeader) private val pathHeader: String,
        @QueryParam("delay") @DefaultValue(Defaults.delay.toString()) private val delay: Long,
        @QueryParam("status") @DefaultValue(Defaults.status.toString()) private val status: Int,
        @QueryParam("type") @DefaultValue(Defaults.type) private val type: String,
        @QueryParam("body") @DefaultValue(Defaults.body) private val body: String) {

    object Defaults {
        const val pathHeader = "X-Spineless-Request-Path"
        const val delay = 0L
        const val status = 200
        const val type = MediaType.APPLICATION_JSON
        const val body = """{ "status" : "OK" }"""
    }

    @GET // JAX-RS automatically adds support for @HEAD and @OPTIONS
    fun get() = respond()

    @PUT
    fun put() = respond()

    @POST
    fun post() = respond()

    @DELETE
    fun delete() = respond()

    private fun respond(): Response {
        Thread.sleep(delay)
        return Response.status(status).type(type).entity(body).header(pathHeader, path).build()
    }
}