package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

/*
* notes:
*  ID: int primary key not null
*  title: text not null
*  create_date: timestamp not null
* */
@OptIn(ObsoleteCoroutinesApi::class)
fun Application.configureDatabases() {

    val database = Database.connect(
            url = "YOUR_LINK",
            user = "PASS_YOUR_USERNAME",
            driver = "org.postgresql.Driver",
            password = "PASS_YOUR_PASSWORD"
    )
    val noteService = NoteService(database)
    routing {
        get("/") {
            var string = ""
            noteService.getAllNotes().forEach {
                string += "${it.id}\t${it.title}\t${it.createDate}\n"
            }
            call.respondText(string)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@ObsoleteCoroutinesApi
object ServiceHelper {
    private val dispatcher: CoroutineContext

    init {
        dispatcher = newFixedThreadPoolContext(5, "database-pool")
    }

    suspend fun <T> dbQuery(
        block: () -> T
    ): T =
        withContext(dispatcher) {
            transaction { block() }
        }
}