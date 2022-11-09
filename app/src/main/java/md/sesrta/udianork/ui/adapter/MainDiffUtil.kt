package md.sesrta.udianork.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import md.sesrta.udianork.ui.model.BaseModel

class MainDiffUtil : DiffUtil.ItemCallback<BaseModel>() {

    override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
        return oldItem == newItem
    }
}