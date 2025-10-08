package org.jetbrains.kotlinx.tictactoe.engine.cache

import kotlinx.serialization.json.Json
import java.io.File

object TTPersistence {

    private val json = Json { prettyPrint = true }

    fun save(tt: Map<Long, TTEntry>, path: String = "transposition_table.json") {
        val text = json.encodeToString(tt)
        File(path).writeText(text)
    }

    fun load(path: String = "transposition_table.json"): MutableMap<Long, TTEntry> {
        val file = File(path)
        if (!file.exists()) return mutableMapOf()
        val text = file.readText()
        return json.decodeFromString(text)
    }
}
