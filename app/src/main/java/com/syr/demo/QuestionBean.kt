package com.syr.demo

import com.syr.demo.QuestionAdapter.Companion.TYPE_ONE
import com.syr.demo.QuestionAdapter.Companion.TYPE_THREE
import com.syr.demo.QuestionAdapter.Companion.TYPE_TWO

/**
 * @author Karen
 * @date 2020-03-26
 */
data class QuestionBean(var questions: MutableList<Questions>) {
    data class Questions(
        var id: String,
        var title: String,
        var answers: MutableList<Answers>?,

        var startAnim: Boolean = false,
        var chooseId: String? = null
    ) {
        data class Answers(
            var id: String,
            var option: String,
            var ratio: Float
        )

        var itemType: Int
            get() {
                return if (2 == answers?.size ?: 0) if (chooseId == null) TYPE_TWO else TYPE_THREE else TYPE_ONE
            }
            set(value) {}
    }
}