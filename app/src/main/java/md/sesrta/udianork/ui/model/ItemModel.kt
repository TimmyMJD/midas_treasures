package md.sesrta.udianork.ui.model

data class ItemModel(
    override val id: Int,
    val isClickable: Boolean,
    val isWinner: Boolean,
    val isOpenChest: Boolean,
    val isOpenLine: Boolean,
    override val type: Int = 0
) : BaseModel

data class ScoreModel(
    override val id: Int,
    val score: Int,
    val isOpenLine: Boolean,
    override val type: Int = 1
) : BaseModel

interface BaseModel {
    val id: Int
    val type: Int
}