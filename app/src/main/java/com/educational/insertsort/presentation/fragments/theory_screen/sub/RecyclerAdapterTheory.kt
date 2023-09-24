package com.educational.insertsort.presentation.fragments.theory_screen.sub

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.educational.insertsort.R
import com.educational.insertsort.data.model.TheoryModel
import com.educational.insertsort.databinding.TheoryItemBinding

class RecyclerAdapterTheory(
    private var listOfLevelsTheory: List<TheoryModel> = emptyList(),
    private val callback: CallBackClickItemTheoryRecycler
) : RecyclerView.Adapter<RecyclerAdapterTheory.TheoryViewHolder>() {
    inner class TheoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TheoryItemBinding.bind(view)

        fun inflateView(model: TheoryModel) {
            binding.cardTitle.text = model.title
            binding.cardLvl.text = model.level
            if (model.complete) binding.completeStatus.setImageResource(R.drawable.complete)

            itemView.setOnClickListener {
                callback.callbackAfterClick(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.theory_item, parent, false
        )
        return TheoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfLevelsTheory.size
    }

    override fun onBindViewHolder(holder: TheoryViewHolder, position: Int) {
        holder.inflateView(listOfLevelsTheory[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(newList: List<TheoryModel>) {
        listOfLevelsTheory = newList
        notifyDataSetChanged()
    }
}