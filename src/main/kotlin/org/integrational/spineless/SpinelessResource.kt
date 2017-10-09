package org.integrational.spineless

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.integrational.spineless.SpinelessResource.Companion.Defaults
import java.lang.Math.max
import java.lang.Math.round
import java.util.*
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
        @QueryParam("summaryHeader") @DefaultValue(Defaults.summaryHeader) private val summaryHeader: String,
        @QueryParam("delay") @DefaultValue(Defaults.delay.toString()) private val delay: Long,
        @QueryParam("delayStddev") @DefaultValue(Defaults.delayStddev.toString()) private val delayStddev: Long,
        @QueryParam("status") @DefaultValue(Defaults.status.toString()) private val status: Int,
        @QueryParam("type") @DefaultValue(Defaults.type) private val type: String,
        @QueryParam("body") private val body: String?) {

    companion object {
        private val random = Random()
        private val jsonMapper by lazy { jacksonObjectMapper() }
        private val jsonWriter by lazy { jsonMapper.writer() }

        object Defaults {
            const val summaryHeader = "X-Spineless-Summary"
            const val delay = 0L
            const val delayStddev = 0L
            const val status = 200
            const val type = MediaType.APPLICATION_JSON
        }

        data class Summary(val summaryHeader: String,
                           val path: String,
                           val delay: Long,
                           val delayStddev: Long,
                           val actualDelay: Long,
                           val status: Int,
                           val type: String,
                           val body: String?)
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
        val actualDelay = sleep()
        val sum = asJson(summary(actualDelay))
        val entity = body ?: sum
        return Response.status(status).type(type).entity(entity).header(summaryHeader, sum).build()
    }

    private fun randomDelay(): Long = max(0, round((random.nextGaussian() * delayStddev + delay)))
    private fun sleep(): Long {
        val actualDelay = randomDelay()
        Thread.sleep(actualDelay)
        return actualDelay
    }

    private fun summary(actualDelay: Long) = Summary(summaryHeader, path, delay, delayStddev, actualDelay, status, type, body)

    private fun asJson(obj: Any): String = jsonWriter.writeValueAsString(obj)
}
