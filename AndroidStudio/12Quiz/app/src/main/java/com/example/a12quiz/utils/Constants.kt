package com.example.a12quiz.utils

import com.example.a12quiz.R
import com.example.a12quiz.model.Question

object Constants {
    fun getQuestions(): MutableList<Question> {
        val questions = mutableListOf<Question>()

        val quest1 = Question(
            1, "What country does this flag belong to?",
            R.drawable.brazilflag,
            "India",
            "Italy",
            "Brazil",
            "Ireland",
            1
        )
        questions.add(quest1)

        val quest2 = Question(
            2, "What's the SI unit of length?",
            R.drawable.brazilflag,
            "Meters",
            "Centimeters",
            "Inches",
            "Miles",
            1
        )
        questions.add(quest2)

        val quest3 = Question(
            1, "Which is one?",
            R.drawable.brazilflag,
            "1",
            "2",
            "3",
            "4",
            1
        )
        questions.add(quest3)

        val quest4 = Question(
            1, "Which is english?",
            R.drawable.brazilflag,
            "English",
            "aergvf",
            "arfvga",
            "aerdfg",
            1
        )
        questions.add(quest4)

        val quest5 = Question(
            1, "Second is answer",
            R.drawable.brazilflag,
            "India",
            "2",
            "Brazil",
            "Ireland",
            2
        )
        questions.add(quest5)

        val quest6 = Question(
            1, "What country does this flag belong to?",
            R.drawable.brazilflag,
            "India",
            "Italy",
            "Brazil",
            "Ireland",
            1
        )
        questions.add(quest6)

        val quest7 = Question(
            1, "Second is answer",
            R.drawable.brazilflag,
            "India",
            "2",
            "Brazil",
            "Ireland",
            2
        )
        questions.add(quest7)

        val quest8 = Question(
            1, "Second is answer",
            R.drawable.brazilflag,
            "India",
            "2",
            "Brazil",
            "Ireland",
            2
        )
        questions.add(quest8)

        val quest9 = Question(
            1, "Second is answer",
            R.drawable.brazilflag,
            "India",
            "2",
            "Brazil",
            "Ireland",
            2
        )
        questions.add(quest9)

        val quest10 = Question(
            1, "Second is answer",
            R.drawable.brazilflag,
            "India",
            "2",
            "Brazil",
            "Ireland",
            2
        )
        questions.add(quest10)


        return questions //at last question put this.
    }
}