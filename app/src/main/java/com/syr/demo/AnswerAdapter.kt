package com.syr.demo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lhcredit.commonres.widget.ProgressBarView

/**
 * @author Karen
 * @date 2020-03-26
 */
class AnswerAdapter(
    private val mContext: Context,
    private val data: QuestionBean.Questions, val onItemClick: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val answer = data.answers!![position]
            holder.tvTitle.text = answer.option
            holder.tvPer.text = "${answer.ratio}%"

            holder.tvPer.visibility = if (data.chooseId.isNullOrBlank()) View.GONE else View.VISIBLE
            if (!data.chooseId.isNullOrBlank()) {
                val color =
                    mContext.resources.getColor(if (data.chooseId == answer.id) R.color.colorAccent else R.color.color_dcdcdc)
                holder.progress.setDrawable(
                    color,
                    ProgressBarView.CornerType.NONE,
                    backgroundColor = Color.parseColor("#f5f5f5"), id = answer.id
                )
                if (data.startAnim) {
                    holder.progress.startAnim(answer.ratio.toInt()) { data.startAnim = false }
                } else {
                    holder.progress.progress = answer.ratio.toInt()
                }
            }
            holder.itemView.setOnClickListener {
                if (data.chooseId.isNullOrBlank()) {
                    data.startAnim = true
                    data.chooseId = answer.id
                    onItemClick()
                }
            }
        }
    }

    override fun getItemCount(): Int = data.answers?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_answer,
                parent,
                false
            )
        )
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvPer = view.findViewById<TextView>(R.id.tvPer)
        val progress = view.findViewById<ProgressBarView>(R.id.progress)
    }
}