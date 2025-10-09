package org.jetbrains.kotlinx.tictactoe.engine.cache

import kotlinx.serialization.json.Json
import java.io.File

object TTPersistence {

    private val json = Json { prettyPrint = true }

    fun save(tt: Map<Long, TTEntry>, path: String = "transposition_table.json") {
        val file = File(System.getProperty("user.dir"), path)
        file.parentFile?.mkdirs()
        val text = json.encodeToString(tt)
        file.writeText(text)
    }

    fun load(path: String = "transposition_table.json"): MutableMap<Long, TTEntry> {
        val file = File(System.getProperty("user.dir"), path)
        if (!file.exists()) return mutableMapOf()
        val text = file.readText()
        return json.decodeFromString(text)
    }
}
