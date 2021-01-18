package ru.skillbranch.avito_intern_test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.avito_intern_test.R
import ru.skillbranch.avito_intern_test.model.ListOfNumber


class RecyclerListOfNumberAdapter(
    private var mListOfNumbers: MutableList<ListOfNumber>,
    private val listener: IOnItemClickListener,
    private var mContext: Context
) : RecyclerView.Adapter<RecyclerListOfNumberAdapter.MutListOfNumbersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutListOfNumbersViewHolder {
        mContext = parent.context
        return MutListOfNumbersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MutListOfNumbersViewHolder, position: Int) {
        holder.itemContainer.animation =
            AnimationUtils.loadAnimation(mContext, R.anim.anim_fade_scale)
        holder.itemNumberTitle.text = mListOfNumbers[position].itemNumber
    }

    override fun getItemCount(): Int = mListOfNumbers.size

    inner class MutListOfNumbersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemContainer: CardView = itemView.findViewById(R.id.item_container)
        val itemNumberTitle: TextView = itemView.findViewById(R.id.tv_itemlist_number)
        val deleteNumberButton: ImageButton = itemView.findViewById(R.id.btn_delete_number)

        init {
            deleteNumberButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val currentAdapterPosition: Int = adapterPosition
            if (currentAdapterPosition != RecyclerView.NO_POSITION) {
                listener.onRemoveItemClick(adapterPosition)
            }
        }
    }

    interface IOnItemClickListener {
        fun onRemoveItemClick(position: Int)
    }
}