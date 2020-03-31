package com.syr.demo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

/**
 * @author Karen
 * @date 2020-03-25
 */
class ListActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        tvBack.setOnClickListener { onBackPressed() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            QuestionAdapter(this, getQuestions(50))
    }

    private fun getQuestions(size: Int): MutableList<QuestionBean.Questions> {
        val questions = mutableListOf<QuestionBean.Questions>()
        questions.addAll(
            mutableListOf(
                QuestionBean.Questions(
                    "1", "第1题：Python是世界上最好的语言吗？", mutableListOf(
                        QuestionBean.Questions.Answers("1", "想打架吗", 40f),
                        QuestionBean.Questions.Answers("2", "你糊涂了吗", 30f),
                        QuestionBean.Questions.Answers("3", "是的吧", 30f)
                    )
                ),
                QuestionBean.Questions(
                    "2", "第2题：Python是世界上最好的语言吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("1", "是", 20f),
                        QuestionBean.Questions.Answers("2", "不是", 80f)
                    )
                ),
                QuestionBean.Questions(
                    "3", "第3题：你觉得驾照很重要吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("1", "重要", 50f),
                        QuestionBean.Questions.Answers("2", "不重要", 50f)
                    )
                ),
                QuestionBean.Questions(
                    "4", "第4题：为什么有这么多学生使用苹果手机？", mutableListOf(
                        QuestionBean.Questions.Answers("1", "父母有钱", 20f),
                        QuestionBean.Questions.Answers("2", "想装B", 50f),
                        QuestionBean.Questions.Answers("3", "容易勾搭妹子", 10f),
                        QuestionBean.Questions.Answers("4", "谁知道呢", 20f)
                    ), chooseId = "2"
                ),
                QuestionBean.Questions(
                    "5", "第5题：每个月发工资的时候你会买理财产品吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("100", "会", 1f),
                        QuestionBean.Questions.Answers("201", "不会", 99f)
                    ), chooseId = "100"
                ),
                QuestionBean.Questions(
                    "6", "第6题：你觉得应该养成写博客的习惯吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("1", "应该", 60f),
                        QuestionBean.Questions.Answers("2", "不应该", 40f)
                    ), chooseId = "2"
                ),
                QuestionBean.Questions(
                    "7", "第7题：你喜欢吃东西吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("1", "喜欢", 100f),
                        QuestionBean.Questions.Answers("2", "不喜欢", 0f)
                    ), chooseId = "2"
                ),
                QuestionBean.Questions(
                    "8", "第8题：你喜欢睡懒觉吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("1", "喜欢", 100f),
                        QuestionBean.Questions.Answers("2", "不喜欢", 0f)
                    ), chooseId = "1"
                ),
                QuestionBean.Questions(
                    "9", "第9题：你追星吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("1", "追", 0f),
                        QuestionBean.Questions.Answers("2", "不追", 100f)
                    ), chooseId = "2"
                )
            )
        )
        for (index in 10..size + 10) {
            val leftPer = Random().nextInt(100)
            val rightPer = 100 - leftPer
            questions.add(
                QuestionBean.Questions(
                    "$index", "第${index}题：你追星吗？",
                    mutableListOf(
                        QuestionBean.Questions.Answers("$index", "追", leftPer.toFloat()),
                        QuestionBean.Questions.Answers("${index + 1}", "不追", rightPer.toFloat())
                    )
                )
            )
        }
        return questions
    }
}