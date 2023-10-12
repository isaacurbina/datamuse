package urbina.isaac.dictionary.repository

import io.mockk.MockKAnnotations.init
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkObject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import urbina.isaac.dictionary.model.APIResponse
import urbina.isaac.dictionary.service.retrofit.DataMuseService
import java.io.IOException

class DataMuseRepositoryImplTest {

    @MockK
    lateinit var retrofitService: DataMuseService

    private lateinit var repository: DataMuseRepository

    @Before
    fun setUp() {
        init(this, relaxed = true, relaxUnitFun = true)
        repository = DataMuseRepositoryImpl(retrofitService)
    }

    @After
    fun tearDown() {
        unmockkObject(retrofitService)
    }

    @Test
    fun `search retrieves data from retrofit service`() = runBlocking {
        repository.search("test").collect {}
        coVerify { retrofitService.search(any()) }
    }

    @Test
    fun `search when network call fails, throws exception`() = runBlocking {
        coEvery { retrofitService.search(any()) } throws IOException()
        var exception: Throwable? = null
        repository.search("test").catch {
            exception = it
        }.collect { }
        Assert.assertNotNull(exception)
    }

    @Test
    fun `search when network call succeeds, collects results`() = runBlocking {
        var result: List<APIResponse?>? = null
        repository.search("test").collect {
            result = it
        }
        Assert.assertNotNull(result)
    }
}