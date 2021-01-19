package ru.skillbranch.avito_intern_test

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.skillbranch.avito_intern_test.adapter.RecyclerListOfNumberAdapter
import ru.skillbranch.avito_intern_test.data.DataSourceListOfNumber
import ru.skillbranch.avito_intern_test.data.DataSourceListOfNumber.Companion.itemCount
import ru.skillbranch.avito_intern_test.data.DataSourceListOfNumber.Companion.itemList
import ru.skillbranch.avito_intern_test.data.DataSourceListOfNumber.Companion.poolList
import ru.skillbranch.avito_intern_test.model.ListOfNumber
import ru.skillbranch.avito_intern_test.utils.Utils

class MainActivity : AppCompatActivity(), RecyclerListOfNumberAdapter.IOnItemClickListener {

    companion object {
//        const val TAG_FOR_DEBUG = "TAG_FOR_DEBUG"

        const val KEY_TO_SAVE_ITEM_LIST = "KEY_TO_SAVE_ITEM_LIST"
        const val KEY_TO_SAVE_POOL_LIST = "KEY_TO_SAVE_POOL_LIST"
        const val KEY_TO_SAVE_ITEM_COUNT = "KEY_TO_SAVE_ITEM_COUNT"

    }

    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    private val mListOfNumberAdapter = RecyclerListOfNumberAdapter(itemList, this, this)

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.rv_list_number)

        initRecyclerView()
        GlobalScope.launch(job) {
            DataSourceListOfNumber.addItemInAScope(mListOfNumberAdapter)
        }
    }

    private fun initRecyclerView() {
        mRecyclerView.layoutManager =
            GridLayoutManager(this, Utils.getColumnCount(resources.configuration.orientation))
        mRecyclerView.adapter = mListOfNumberAdapter
        mRecyclerView.smoothScrollToPosition(mListOfNumberAdapter.itemCount - 1)
    }

    override fun onRemoveItemClick(position: Int) {
//        Toast.makeText(this, "Item removed $position positions", Toast.LENGTH_SHORT).show()
        if (itemList.isNotEmpty()) {
            poolList.add(itemList[position])
            itemList.removeAt(position)
            mListOfNumberAdapter.notifyItemRemoved(position)
        }
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.run {

            state.putParcelableArrayList(
                KEY_TO_SAVE_ITEM_LIST,
                itemList as ArrayList<out Parcelable>
            )
            state.putParcelableArrayList(
                KEY_TO_SAVE_POOL_LIST,
                poolList as ArrayList<out Parcelable>
            )
            state.putInt(KEY_TO_SAVE_ITEM_COUNT, itemCount)

        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {

            itemList =
                getParcelableArrayList<ListOfNumber>(KEY_TO_SAVE_ITEM_LIST) as MutableList<ListOfNumber>
            poolList =
                getParcelableArrayList<ListOfNumber>(KEY_TO_SAVE_POOL_LIST) as MutableList<ListOfNumber>
            itemCount = getInt(KEY_TO_SAVE_ITEM_COUNT)

//            Log.d(TAG_FOR_DEBUG, itemList.toString())
//            Log.d(TAG_FOR_DEBUG, poolList.toString())
//            Log.d(TAG_FOR_DEBUG, itemCount.toString())

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}