package io.fajarca.movies.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.fajarca.movies.repository.HomeRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @Mock
    lateinit var repository: HomeRepository

    @InjectMocks
    lateinit var viewModel : HomeViewModel


    /**
     * In this test, LiveData will immediately post values without switching threads.
     */

    /**
     * A rule is a way to run code before and after the execution of a test in JUnit.
     * InstantTaskExecutorRule is a JUnit rule that configures LiveData to immediately post to the main thread while a test is run.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
    }
    
}