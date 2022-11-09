package md.sesrta.udianork.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import md.sesrta.udianork.R
import md.sesrta.udianork.ui.model.ItemModel

class MainViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private val image = parentView.findViewById<ImageView>(R.id.imageView)
    private val root = parentView.findViewById<View>(R.id.root)

    fun onBind(model: ItemModel, clickDelegate: (Int) -> Unit) {
        if (model.isClickable) {
            root.setOnClickListener {
                clickDelegate.invoke(model.id)
            }
        } else {
            root.setOnClickListener(null)
        }

        if (model.isOpenChest) {
            if (model.isWinner) {
                image.setImageResource(R.drawable.coin)
            } else {
                image.setImageResource(R.drawable.x_red)
            }
        } else {
            image.setImageResource(R.drawable.chest)
        }

        if (model.isOpenLine) {
            root.setBackgroundColor(itemView.context.getColor(android.R.color.holo_green_light))
        } else {
            root.setBackgroundColor(itemView.context.getColor(android.R.color.holo_red_light))
        }
    }
}