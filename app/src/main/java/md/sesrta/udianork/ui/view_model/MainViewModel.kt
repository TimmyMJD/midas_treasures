package md.sesrta.udianork.ui.view_model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import md.sesrta.udianork.ui.model.BaseModel
import md.sesrta.udianork.ui.model.ItemModel
import md.sesrta.udianork.ui.model.ScoreModel
import kotlin.random.Random

class MainViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    val itemsLiveData = MutableLiveData<List<BaseModel>>()
    val gameIsOn = MutableLiveData(false)
    val scoreLiveData = MutableLiveData(0)
    val totalScoreLiveData = MutableLiveData(0)

    init {
        app.applicationContext.getSharedPreferences(MIDAS_SH, Context.MODE_PRIVATE).apply {
            totalScoreLiveData.value = getInt(TOTAL_SCORE, 0)
        }
        generateItems()
    }

    fun generateItems() {
        val items = mutableListOf<BaseModel>()
        for (index in 0..29) {
            val item =
                if (index % 5 == 4) {
                    ScoreModel(
                        id = index,
                        score = scoreHash[index] ?: 0,
                        isOpenLine = false
                    )
                } else {
                    ItemModel(
                        id = index,
                        isClickable = false,
                        isWinner = Random.nextBoolean(),
                        isOpenChest = false,
                        isOpenLine = false
                    )
                }

            items.add(item)
        }

        itemsLiveData.value = items
    }

    fun changeLine(lineNumber: Int, isClickable: Boolean = true, isOpenLine: Boolean = true) {
        val startId = lineNumber * 5
        val endId = startId + 4
        itemsLiveData.value?.let {
            val newItems = it.map {
                if (it.id in startId..endId) {
                    if (it is ItemModel) {
                        it.copy(isClickable = isClickable, isOpenLine = isOpenLine)
                    } else {
                        (it as ScoreModel).copy(isOpenLine = isOpenLine)
                    }
                } else {
                    it
                }
            }
            itemsLiveData.value = newItems
        } ?: generateItems()
    }

    fun openChest(id: Int) {
        var isItemWinner = false
        itemsLiveData.value?.let {
            val newItems = it.map {
                if (it is ItemModel && it.id == id) {
                    isItemWinner = it.isWinner
                    it.copy(isOpenChest = true)
                } else {
                    it
                }
            }
            itemsLiveData.value = newItems
        }

        val lineNumber = id / 5
        if (isItemWinner) {
            changeLine(lineNumber, isClickable = false)

            scoreLiveData.value = scoreLiveData.value?.plus(scoreHash[lineNumber * 5 + 4] ?: 0)

            if (lineNumber == 5) {
                totalScoreLiveData.value = totalScoreLiveData.value?.plus(scoreLiveData.value ?: 0)
                scoreLiveData.value = 0
                gameIsOn.value = false
                saveToSharedPref(totalScoreLiveData.value ?: 0)
            } else {
                changeLine(lineNumber + 1)
            }
        } else {
            gameIsOn.value = false
            scoreLiveData.value = 0
            changeLine(lineNumber, isClickable = false)
        }
    }

    fun startGame() {
        generateItems()
        changeLine(0)
        scoreLiveData.value = 0
        gameIsOn.value = true
    }

    fun stopGame() {
        totalScoreLiveData.value = totalScoreLiveData.value?.plus(scoreLiveData.value ?: 0)
        scoreLiveData.value = 0
        //i'm not sure about this line
        saveToSharedPref(totalScoreLiveData.value ?: 0)
        startGame()
    }

    private fun saveToSharedPref(totalScore: Int) {
        app.applicationContext.getSharedPreferences(MIDAS_SH, Context.MODE_PRIVATE).apply {
            edit()
                .putInt(TOTAL_SCORE, totalScore)
                .apply()
        }
    }

    companion object {
        private val scoreHash = mutableMapOf(
            4 to 100,
            9 to 500,
            14 to 2000,
            19 to 10000,
            24 to 50000,
            29 to 200000
        )

        private const val TOTAL_SCORE = "TotalScore"
        private const val MIDAS_SH = "MidasSH"
    }

}