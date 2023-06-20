package com.example.plugins

import com.example.plugins.ServiceHelper.dbQuery
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.selectAll

object Notes : Table() {
    val id = integer("id")
    val title = text("title")
    val createdDate = datetime("create_date")
}
class NoteService(database: Database) {
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    suspend fun getAllNotes(): List<NoteModel> = dbQuery {
        Notes.selectAll().map { toNote(it) }
    }


    private fun toNote(row: ResultRow): NoteModel =
        NoteModel(
            id = row[Notes.id],
            title = row[Notes.title],
            createDate = row[Notes.createdDate]
        )
}
