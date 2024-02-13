package edu.uw.ischool.bkp2002.quizdroid

object AllQuestions {
    private val mathQuestions = listOf(
        Question("What is 1 + 1?", listOf("1", "2", "3", "4"), 1),
        Question("What is 2 + 2?", listOf("1", "2", "3", "4"), 3),
        Question("What is 2 - 1?", listOf("1", "2", "3", "4"), 0),
        Question("What is 2 - 2?", listOf("0", "2", "3", "4"), 0),
        Question("What is 10 * 10?", listOf("1", "2", "3", "100"), 3),
        Question("What is 24 / 12?", listOf("1", "2", "3", "4"), 1),
        Question("What is 3^2?", listOf("1", "2", "9", "4"), 2)
    )

    private val physicsQuestions = listOf(
        Question("What is physics the study of?", listOf("Matter", "Energy", "Forces", "All of the above"), 3),
        Question("Which is not a main branch of physics?", listOf("Particle", "Biology", "Relativity", "Nuclear"), 1),
        Question("Who formulated relativity?", listOf("Albert Einstein", "Thomas Jefferson", "Benjamin Franklin", "Joe Biden"), 0),
        Question("Which of the following is a fundamental force of nature?", listOf("Gravity", "LeBron James", "Heat", "None of the above"), 0),
        Question("How many laws does thermodynamics have?", listOf("1", "2", "3", "4"), 3),
        Question("What is Newton's first law of motion?", listOf("F = ma", "Action and Reaction", "Inertia", "Gravity"), 2),
        Question("What is the popular physics sitcom called?", listOf("Gravitational Pull", "The Big Bang Theory", "Dark Matter", "Nuclear Reaction"), 1)
    )

    private val marvelQuestions = listOf(
        Question("How many infinity stones are there?", listOf("3", "5", "6", "8"), 2),
        Question("What species is Groot?", listOf("Human", "Flora Colossus", "Mutant Pine Tree", "Agnus Botencia"), 1),
        Question("What is Ant Man's real name?", listOf("Scott Lang", "Bruce Wayne", "James Pearson", "Tom Erickson"), 0),
        Question("Who kills Thanos in the first half of Endgame?", listOf("Hulk", "Iron Man", "Ant Man", "Thor"), 3),
        Question("Who can lift Thor's hammer besides Thor?", listOf("No One", "Captain Marvel", "Hulk", "Black Panther"), 1),
        Question("Which infinity stone does Black Widow sacrifice herself for?", listOf("Power Stone", "Time Stone", "Soul Stone", "Reality Stone"), 2),
        Question("What newspaper does Peter Parker work for?", listOf("The Daily Bugle", "New York Times", "The Times", "The Daily Mail"), 0),
    )

    val topics = mapOf(
        "Math" to Topic("Math questions.", mathQuestions),
        "Physics" to Topic("Physics questions.", physicsQuestions),
        "Marvel Super Heroes" to Topic("Marvel Super Heroes questions.", marvelQuestions)
    )
}