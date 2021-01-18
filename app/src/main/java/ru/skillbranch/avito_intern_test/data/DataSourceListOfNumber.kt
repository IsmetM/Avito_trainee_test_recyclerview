package ru.skillbranch.avito_intern_test.data

import android.util.Log
import kotlinx.coroutines.*
import ru.skillbranch.avito_intern_test.adapter.RecyclerListOfNumberAdapter
import ru.skillbranch.avito_intern_test.model.ListOfNumber
import kotlin.random.Random

class DataSourceListOfNumber() {
    companion object {

        const val FIRST_ITEM_COUNT: Int = 14
        const val TIME_TO_DELAY: Long = 5000L

        var itemList: MutableList<ListOfNumber> = mutableListOf()
        var poolList: MutableList<ListOfNumber> = mutableListOf()
        var itemCount: Int = 1

        init {
            itemList = firstInitListOfNumber()
        }

        private fun firstInitListOfNumber(): MutableList<ListOfNumber> {
            val list = mutableListOf<ListOfNumber>()
            for (item in 0..FIRST_ITEM_COUNT) {
                list.add(ListOfNumber("$itemCount"))
                itemCount++
            }
//            Log.d("M_TAG", itemCount.toString())
            return list
        }

        suspend fun addItemInAScope(adapter: RecyclerListOfNumberAdapter) =
            withContext(Dispatchers.IO) {
                while (true) {
                    delay(TIME_TO_DELAY)

                    //Если *пулСписок* НЕ пустой
                    if (poolList.isNotEmpty()) {
                        //Добавление из *пулСписок* если *списокНомеров* пуст
                        if (itemList.isEmpty()) {
                            itemList.add(poolList[poolList.lastIndex])
                            poolList.removeAt(poolList.lastIndex)
                            withContext(Dispatchers.Main) { adapter.notifyItemInserted(itemList.size - 1) }
                        } else {
                            //Добавление из *пулСписок* если *списокНомеров* НЕ пустой
                            val randIndex = Random.nextInt(0, itemList.size)
                            itemList.add(randIndex, poolList[poolList.lastIndex])
                            poolList.removeAt(poolList.lastIndex)
                            withContext(Dispatchers.Main) { adapter.notifyItemInserted(randIndex) }
                        }
                    } else {
                        //Если *пулСписок* пустой
                        if (itemList.isNotEmpty()) {
                            //Добавление если *списокНомеров* НЕ пуст
                            val randIndex = Random.nextInt(0, itemList.size)
                            itemList.add(randIndex, ListOfNumber("$itemCount"))
                            withContext(Dispatchers.Main) { adapter.notifyItemInserted(randIndex) }
                            itemCount++

                        } else {
                            //Добавление если *списокНомеров* пустой
                            itemList.add(ListOfNumber("$itemCount"))
                            withContext(Dispatchers.Main) { adapter.notifyItemInserted(itemList.size - 1) }
                            itemCount++
                        }
                    }
                }
            }


//        private suspend fun addAndUpdateItem(listAddElement: MutableList<ListOfNumber>,
//                                             randomIndex: Int? = null,
//                                             itemCountTitle: Int,
//                                             adapter: RecyclerListOfNumberAdapter){
//            if (randomIndex != null) {
//                listAddElement.add(randomIndex, ListOfNumber("$itemCountTitle"))
//                withContext(Dispatchers.Main){adapter.notifyItemInserted(randomIndex)}
//            }else{
//                listAddElement.add(ListOfNumber("$itemCountTitle"))
//                withContext(Dispatchers.Main){adapter.notifyItemInserted(listAddElement.size-1)}
//            }
//        }
    }
}
