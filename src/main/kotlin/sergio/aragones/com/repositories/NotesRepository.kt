package sergio.aragones.com.repositories

import com.sergio.aragones.kotlinexpert.database.AppDatabase
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import sergio.aragones.com.models.Note
import java.io.File

private const val DATABASE_NAME = "database.db"

object NotesRepository {

    private val database = JdbcSqliteDriver("jdbc:sqlite:$DATABASE_NAME").let {

        if (!File(DATABASE_NAME).exists()) {
            AppDatabase.Schema.create(it)
        }
        AppDatabase(it)
    }
    private val notesQueries = database.noteQueries

    fun save(note: Note): Note {
        notesQueries.insert(note.title, note.description, note.type.name)
        return notesQueries.selectLastInsertedNote().executeAsOne().toNote()
    }

    fun getAll(): List<Note> = notesQueries.select().executeAsList().map { it.toNote() }

    fun getById(id: Long): Note? =
        notesQueries.selectById(id).executeAsOneOrNull()?.toNote()

    fun update(note: Note): Boolean {

        if (getById(note.id) == null) return false

        notesQueries.update(note.title, note.description, note.type.name, note.id)
        return true
    }

    fun deleteAll() = notesQueries.delete()

    fun delete(id: Long): Boolean {

        if (getById(id) == null) return false

        notesQueries.deleteById(id)
        return true
    }
}