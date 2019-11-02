package io.fajarca.movies.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Sets the main dispatcher to testCoroutineDispatcher, runs the test then resets and cleanup.
 * Also creates testCoroutineScope in which we can run our tests.
 */
@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {

        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher) //Replace the real dispatcher with test dispatcher

            base.evaluate()

            Dispatchers.resetMain() //Reset main dispatcher to the original MainDispatcher

            testCoroutineScope.cleanupTestCoroutines()
        }

    }

    fun runBlocking(block : suspend TestCoroutineScope.() -> Unit ) {
        testCoroutineScope.runBlockingTest { block() }
    }

    fun runBlockingTest(block : suspend TestCoroutineScope.() -> Unit) {
        testCoroutineScope.runBlockingTest { block() }
    }

}