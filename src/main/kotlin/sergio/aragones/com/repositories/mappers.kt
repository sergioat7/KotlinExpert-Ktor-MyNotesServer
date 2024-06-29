package sergio.aragones.com.repositories

import com.sergio.aragones.kotlinexpert.database.DbNote
import sergio.aragones.com.models.Note

fun DbNote.toNote(): Note =
    Note(
        id = id,
        title = title,
        description = description,
        type = Note.Type.valueOf(type)
    )