package md.sesrta.udianork.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import md.sesrta.udianork.R
import md.sesrta.udianork.ui.model.ScoreModel

class ScoreViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {

    private val text = parentView.findViewById<TextView>(R.id.textView)
    private val root = parentView.findViewById<View>(R.id.root)

    fun onBind(model: ScoreModel) {
        text.text = model.score.toString()

        if (model.isOpenLine) {
            root.setBackgroundColor(itemView.context.getColor(android.R.color.holo_green_light))
        } else {
            root.setBackgroundColor(itemView.context.getColor(android.R.color.holo_red_light))
        }
    }
}