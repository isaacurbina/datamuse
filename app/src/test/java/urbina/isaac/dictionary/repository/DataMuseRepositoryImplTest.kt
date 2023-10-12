package urbina.isaac.dictionary.repository

import io.mockk.MockKAnnotations.init
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkObject
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import urbina.isaac.dictionary.service.retrofit.DataMuseService

class DataMuseRepositoryImplTest {

    @MockK
    lateinit var dataMuseService: DataMuseService

    lateinit var dataMuseRepository: DataMuseRepository

    @Before
    fun setUp() {
        init(this, relaxed = true, relaxUnitFun = true)
        dataMuseRepository = DataMuseRepositoryImpl(dataMuseService)
    }

    @After
    fun tearDown() {
        unmockkObject(dataMuseService)
    }

    @Test
    fun `search retrieves data from retrofit service`() = runBlocking {
        dataMuseRepository.search("test").collect {}
        coVerify { dataMuseService.search("test") }
    }
}