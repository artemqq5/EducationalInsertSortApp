package com.educational.insertsort.presentation.fragments.practice_screen.sub

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.educational.insertsort.R
import com.educational.insertsort.data.model.PracticeModel
import com.educational.insertsort.databinding.PracticeItemBinding

class RecyclerAdapterPractice(
    private var listOfLevelsPractice: List<PracticeModel> = emptyList(),
    private val callback: CallBackClickItemPracticeRecycler
) : RecyclerView.Adapter<RecyclerAdapterPractice.PracticeViewHolder>() {
    inner class PracticeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PracticeItemBinding.bind(view)

        private val listImages by lazy {
            arrayOf(
                binding.cardImg1, binding.cardImg2, binding.cardImg3, binding.cardImg4,
                binding.cardImg5,
            )
        }

        fun inflateView(model: PracticeModel) {
            binding.cardTitle.text = model.title
            binding.cardLvl.text = model.level
            binding.countQuestion.text = itemView.resources.getString(
                R.string.count_question_text,
                model.countQuestions.toString()
            )
            if (model.complete) binding.completeStatus.setImageResource(R.drawable.complete)

            repeat(model.difficulty) {
                listImages[it].setImageResource(R.drawable.target)
            }

            itemView.setOnClickListener {
                callback.callbackAfterClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.practice_item, parent, false
        )
        return PracticeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfLevelsPractice.size
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        holder.inflateView(listOfLevelsPractice[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(newList: List<PracticeModel>) {
        listOfLevelsPractice = newList
        notifyDataSetChanged()
    }
}