package com.example.a12quiz.model

import android.media.Image

data class Question(
    val id: Int,
    val question: String,
    val image: Int, //Because we'll be storing images in drawables which stores in numbers
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)

