package sergio.aragones.com

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import sergio.aragones.com.plugins.configureRouting
import sergio.aragones.com.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
}
