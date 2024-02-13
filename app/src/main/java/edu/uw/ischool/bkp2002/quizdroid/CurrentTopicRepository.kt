package edu.uw.ischool.bkp2002.quizdroid

class CurrentTopicRepository : TopicRepository {

    private val topics = mutableListOf(
        Topic(
            title = "Math",
            shortDescription = "Math questions.",
            longDescription = "A series of questions tackling the basics of Mathematics.",
            quizzes = listOf(
                Quiz("What is 1 + 1?", listOf("1", "2", "3", "4"), 1),
                Quiz("What is 2 + 2?", listOf("1", "2", "3", "4"), 3),
                Quiz("What is 2 - 1?", listOf("1", "2", "3", "4"), 0),
                Quiz("What is 2 - 2?", listOf("0", "2", "3", "4"), 0),
                Quiz("What is 10 * 10?", listOf("1", "2", "3", "100"), 3),
                Quiz("What is 24 / 12?", listOf("1", "2", "3", "4"), 1),
                Quiz("What is 3^2?", listOf("1", "2", "9", "4"), 2)
            )
        ),
        Topic(
            title = "Physics",
            shortDescription = "Physics questions.",
            longDescription = "A series of questions tackling the fundamental concepts of Physics.",
            quizzes = listOf(
                Quiz("What is physics the study of?", listOf("Matter", "Energy", "Forces", "All of the above"), 3),
                Quiz("Which is not a main branch of physics?", listOf("Particle", "Biology", "Relativity", "Nuclear"), 1),
                Quiz("Who formulated relativity?", listOf("Albert Einstein", "Thomas Jefferson", "Benjamin Franklin", "Joe Biden"), 0),
                Quiz("Which of the following is a fundamental force of nature?", listOf("Gravity", "LeBron James", "Heat", "None of the above"), 0),
                Quiz("How many laws does thermodynamics have?", listOf("1", "2", "3", "4"), 3),
                Quiz("What is Newton's first law of motion?", listOf("F = ma", "Action and Reaction", "Inertia", "Gravity"), 2),
                Quiz("What is the popular physics sitcom called?", listOf("Gravitational Pull", "The Big Bang Theory", "Dark Matter", "Nuclear Reaction"), 1)
            )
        ),
        Topic(
            title = "Marvel Super Heroes",
            shortDescription = "Marvel Super Heroes questions.",
            longDescription = "Fun questions about Marvel Super Heroes and the marvel cinematic universe.",
            quizzes = listOf(
                Quiz("How many infinity stones are there?", listOf("3", "5", "6", "8"), 2),
                Quiz("What species is Groot?", listOf("Human", "Flora Colossus", "Mutant Pine Tree", "Agnus Botencia"), 1),
                Quiz("What is Ant Man's real name?", listOf("Scott Lang", "Bruce Wayne", "James Pearson", "Tom Erickson"), 0),
                Quiz("Who kills Thanos in the first half of Endgame?", listOf("Hulk", "Iron Man", "Ant Man", "Thor"), 3),
                Quiz("Who can lift Thor's hammer besides Thor?", listOf("No One", "Captain Marvel", "Hulk", "Black Panther"), 1),
                Quiz("Which infinity stone does Black Widow sacrifice herself for?", listOf("Power Stone", "Time Stone", "Soul Stone", "Reality Stone"), 2),
                Quiz("What newspaper does Peter Parker work for?", listOf("The Daily Bugle", "New York Times", "The Times", "The Daily Mail"), 0)
            )
        )
    )

    override fun getTopicByName(name: String): Topic? {
        return topics.find { it.title == name }
    }
    override fun getAllTopics(): List<Topic> {
        return topics
    }

    override fun addTopic(topic: Topic) {
        topics.add(topic)
    }
}
