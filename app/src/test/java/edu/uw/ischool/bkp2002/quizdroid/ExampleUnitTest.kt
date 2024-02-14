package edu.uw.ischool.bkp2002.quizdroid

import org.junit.Test
import org.junit.Before
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CurrentTopicRepositoryTest {

    private lateinit var repository: CurrentTopicRepository

    @Before
    fun setUp() {
        repository = CurrentTopicRepository()
    }

    @Test
    fun testGetAllTopics_returnsAllTopics() {
        val topics = repository.getAllTopics()
        assertEquals(3, topics.size)
    }

    @Test
    fun testGetTopicByName_existingName_returnsTopic() {
        val topic = repository.getTopicByName("Math")
        assertNotNull(topic)
        assertEquals("Math", topic?.title)
    }

    @Test
    fun testGetTopicByName_nonExistingName_returnsNull() {
        val topic = repository.getTopicByName("NonExisting")
        assertNull(topic)
    }

    @Test
    fun testAddTopic_savesNewTopic() {
        val newTopic = Topic(
            title = "Chemistry",
            shortDescription = "Chemistry questions.",
            longDescription = "Basic questions about the fundamental concepts of chemistry.",
            quizzes = listOf(
                Quiz("What is the first element in the periodic table?", listOf("Hydrogen", "Helium", "Zinc", "Boron"), 0)
            )
        )

        repository.addTopic(newTopic)

        val retrievedTopic = repository.getTopicByName("Chemistry")
        assertNotNull(retrievedTopic)
        assertEquals(newTopic.title, retrievedTopic?.title)
        assertEquals(1, retrievedTopic?.quizzes?.size)
    }
}