package org.integrational.spineless

import com.jrestless.aws.gateway.GatewayFeature
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler
import org.glassfish.jersey.server.ResourceConfig

class RequestHandler : GatewayRequestObjectHandler() {
    init {
        val config = ResourceConfig()
                .register(GatewayFeature::class.java)
                .packages("org.integrational.spineless")
        init(config)
        start()
    }
}