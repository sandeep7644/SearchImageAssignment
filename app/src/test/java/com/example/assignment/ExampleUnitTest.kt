package com.example.assignment

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringMatcherTest {
    @Test
    fun containStringPass() {
        assertEquals(true, StringMatcher.containsString("extra","The Birds with an Extra Long Title"))
    }


}