package com.educational.insertsort.presentation.fragments.practice_screen.sub

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.educational.insertsort.R
import com.educational.insertsort.databinding.ResultReviewItemBinding
import com.educational.insertsort.presentation.fragments.practice_screen.model.UserAnswerModel

class RecyclerAdapterPracticeResult(private var listResult: List<UserAnswerModel> = emptyList()) :
    RecyclerView.Adapter<RecyclerAdapterPracticeResult.MyVH>() {
    inner class MyVH(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ResultReviewItemBinding.bind(view)

        fun bind(model: UserAnswerModel) {

            if (listResult[0] == model) binding.line.visibility = View.GONE
            binding.question.text = model.questionModel.question

            when (model.state) {
                true -> {
                    binding.answer.text = model.userAnswer
                    binding.answer.setTextColor(
                        itemView.resources.getColor(
                            R.color.green,
                            itemView.context.theme
                        )
                    )

                    binding.rightAnswerDefault.visibility = View.GONE
                    binding.rightAnswer.visibility = View.GONE
                    binding.explanationDefault.visibility = View.GONE
                    binding.explanation.visibility = View.GONE
                }

                false -> {
                    binding.answer.text = model.userAnswer
                    binding.answer.setTextColor(
                        itemView.resources.getColor(
                            R.color.red,
                            itemView.context.theme
                        )
                    )

                    binding.rightAnswer.text = model.questionModel.rightAnswer
                    binding.explanation.text = model.questionModel.desc

                }

                null -> {
                    binding.answer.text = itemView.resources.getString(R.string.no_answer_text)
                    binding.rightAnswerDefault.visibility = View.GONE
                    binding.rightAnswer.visibility = View.GONE
                    binding.explanationDefault.visibility = View.GONE
                    binding.explanation.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.result_review_item,
            parent,
            false
        )

        return MyVH(view)
    }

    override fun getItemCount(): Int {
        return listResult.size
    }

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.bind(listResult[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(newList: List<UserAnswerModel>) {
        listResult = newList
        notifyDataSetChanged()
    }
}