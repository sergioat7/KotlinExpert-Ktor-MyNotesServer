package sergio.aragones.com.repositories

import sergio.aragones.com.models.Note

object NotesRepository {

    private val notes = mutableListOf<Note>()
    private var currentId = 1L

    fun save(note: Note): Note =
        note.copy(id = currentId++).also(notes::add)

    fun getAll(): List<Note> = notes

    fun getById(id: Long): Note? =
        notes.firstOrNull { it.id == id }

    fun update(note: Note): Boolean =
        notes.indexOfFirst { it.id == note.id }
            .takeIf { it >= 0 }
            ?.also { notes[it] = note }
            .let { it != null }

    fun deleteAll() = notes.clear()

    fun delete(id: Long): Boolean =
        notes.indexOfFirst { it.id == id }
            .takeIf { it >= 0 }
            ?.also(notes::removeAt)
            .let { it != null }
}