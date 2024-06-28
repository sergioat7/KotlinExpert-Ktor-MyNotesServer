package sergio.aragones.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sergio.aragones.com.models.Note
import sergio.aragones.com.repositories.NotesRepository

fun Application.configureRouting() {
    routing {
        route("/notes") {

            post {
                try {
                    val note = call.receive<Note>()
                    val savedNote = NotesRepository.save(note)
                    call.respond(
                        HttpStatusCode.Created,
                        savedNote
                    )
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Bad JSON data body: ${e.message}"
                    )
                }
            }
            get {
                call.respond(NotesRepository.getAll())
            }
            get("{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or malformed id"
                )
                val note = NotesRepository.getById(id.toLong()) ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    "No note with id $id"
                )
                call.respond(note)
            }
            put {
                try {
                    val note = call.receive<Note>()
                    if (NotesRepository.update(note)) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(
                            HttpStatusCode.NotFound,
                            "No note with id ${note.id}"
                        )
                    }
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Bad JSON data body: ${e.message}"
                    )
                }
            }
            delete {
                NotesRepository.deleteAll()
                call.respond(HttpStatusCode.Accepted)
            }
            delete("{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or malformed id"
                )
                if (NotesRepository.delete(id.toLong())) {
                    call.respond(HttpStatusCode.Accepted)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        "No note with id $id"
                    )
                }
            }
        }
    }
}
