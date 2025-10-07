package org.jetbrains.kotlinx.tictactoe

import junit.framework.TestCase.assertTrue
import org.jetbrains.kotlinx.tictactoe.model.Mark
import kotlin.test.Test


class BoardTest{
    @Test
    fun `Mark should have X O and EMPTY`() {
        val values = Mark.values().toList()
        assertTrue(values.containsAll(listOf(Mark.X, Mark.O, Mark.EMPTY)))
    }

}
