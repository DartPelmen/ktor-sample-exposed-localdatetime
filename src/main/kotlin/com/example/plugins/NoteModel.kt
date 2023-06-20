package com.example.plugins


import java.time.LocalDateTime
import java.util.Random

data class NoteModel(
    val id: Int = Random().nextInt(),
    val title: String = "sample-t",
    val createDate: LocalDateTime = LocalDateTime.now()
)