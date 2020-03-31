package com.syr.demo

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhcredit.commonres.widget.ProgressBarView

/**
 * @author Karen
 * @date 2020-03-26
 */
class QuestionAdapter(
    private val mContext: Context,
    private val data: MutableList<QuestionBean.Questions>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
        const val TYPE_THREE = 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val question = data[position]
            when (getItemViewType(position)) {
                TYPE_ONE -> {
                    holder.tvQuestion.text = question.title
                    holder.recyclerView.layoutManager = LinearLayoutManager(mContext)
                    if (question.answers?.size ?: 0 < 3) return
                    holder.recyclerView.adapter =
                        AnswerAdapter(mContext, question) { notifyItemChanged(position) }
                }
                TYPE_TWO -> {
                    holder.tvQuestion.text = question.title
                    if (question.answers?.size ?: 0 < 2) return
                    val answer1 = question.answers!![0]
                    val answer2 = question.answers!![1]
                    holder.tvYes.text = answer1.option
                    holder.tvNo.text = answer2.option

                    holder.tvYes.setOnClickListener {
                        question.chooseId = answer1.id
                        question.startAnim = true
                        notifyItemChanged(position)
                    }
                    holder.tvNo.setOnClickListener {
                        question.chooseId = answer2.id
                        question.startAnim = true
                        notifyItemChanged(position)
                    }
                }
                else -> {
                    holder.tvQuestion.text = question.title
                    if (question.chooseId.isNullOrBlank() || question.answers?.size ?: 0 < 2) return
                    val answer1 = question.answers!![0]
                    val answer2 = question.answers!![1]
                    val yesPer = answer1.ratio
                    val noPer = answer2.ratio
                    var yesPerInt = yesPer.toInt()
                    var noPerInt = 100 - yesPerInt
                    if (yesPer < 3 && yesPer > 0) {//防止进度太小导致圆角问题
                        yesPerInt = 3
                        noPerInt = 97
                    } else if (noPer < 3 && noPer > 0) {
                        yesPerInt = 97
                        noPerInt = 3
                    }
                    holder.tvYesPer.text = "$yesPer%"
                    holder.tvNoPer.text = "$noPer%"
                    when (question.chooseId) {
                        answer1.id -> {
                            holder.tvYes.text = "已选择「${answer1.option}」"
                            holder.tvNo.text = answer2.option
                            holder.progress1.setDrawable(
                                mContext.resources.getColor(R.color.color_f0142a),
                                if (yesPerInt == 100) ProgressBarView.CornerType.ALL else ProgressBarView.CornerType.LEFT,
                                id = answer1.id
                            )
                            holder.progress2.setDrawable(
                                mContext.resources.getColor(R.color.color_801d98e8),
                                if (noPerInt == 100) ProgressBarView.CornerType.ALL else ProgressBarView.CornerType.RIGHT,
                                Gravity.END, id = answer2.id
                            )
                        }
                        answer2.id -> {
                            holder.tvYes.text = answer1.option
                            holder.tvNo.text = "已选择「${answer2.option}」"
                            holder.progress1.setDrawable(
                                mContext.resources.getColor(R.color.color_80f0142a),
                                if (yesPerInt == 100) ProgressBarView.CornerType.ALL else ProgressBarView.CornerType.LEFT,
                                id = answer1.id
                            )
                            holder.progress2.setDrawable(
                                mContext.resources.getColor(R.color.color_1d98e8),
                                if (noPerInt == 100) ProgressBarView.CornerType.ALL else ProgressBarView.CornerType.RIGHT,
                                Gravity.END, id = answer2.id
                            )
                        }
                    }
                    if (question.startAnim) {
                        holder.progress1.startAnim(yesPerInt)
                        holder.progress2.startAnim(noPerInt) { question.startAnim = false }
                    } else {
                        holder.progress1.progress = yesPerInt
                        holder.progress2.progress = noPerInt
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int = data[position].itemType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_ONE -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.item_question_one,
                        parent,
                        false
                    )
                )
            }
            TYPE_TWO -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.item_question_two,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ViewHolder(
                    LayoutInflater.from(mContext).inflate(
                        R.layout.item_question_three,
                        parent,
                        false
                    )
                )
            }
        }
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvQuestion = view.findViewById<TextView>(R.id.tvQuestion)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val tvYes = view.findViewById<TextView>(R.id.tvYes)
        val tvNo = view.findViewById<TextView>(R.id.tvNo)

        val tvYesPer = view.findViewById<TextView>(R.id.tvYesPer)
        val tvNoPer = view.findViewById<TextView>(R.id.tvNoPer)
        val progress1 = view.findViewById<ProgressBarView>(R.id.progress1)
        val progress2 = view.findViewById<ProgressBarView>(R.id.progress2)
    }
}