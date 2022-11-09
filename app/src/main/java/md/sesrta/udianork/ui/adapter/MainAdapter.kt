package md.sesrta.udianork.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import md.sesrta.udianork.R
import md.sesrta.udianork.ui.model.BaseModel
import md.sesrta.udianork.ui.model.ItemModel
import md.sesrta.udianork.ui.model.ScoreModel

class MainAdapter(
    val clickDelegate: (Int) -> Unit
) : ListAdapter<BaseModel, RecyclerView.ViewHolder>(MainDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if (viewType == 0) {
            MainViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main, parent, false)
            )
        } else {
            ScoreViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_score, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItem(position).type == 0) {
            (holder as MainViewHolder).onBind(getItem(position) as ItemModel, clickDelegate)
        } else {
            (holder as ScoreViewHolder).onBind(getItem(position) as ScoreModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}