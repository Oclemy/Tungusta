package info.camposha.tungusta

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import easyadapter.dc.com.library.EasyAdapter
import info.camposha.tungusta.CacheManager.MEM_CACHE_CATEGORIES
import info.camposha.tungusta.CacheManager.MEM_CACHE_ITEMS
import info.camposha.tungusta.Constants.FAILED
import info.camposha.tungusta.Constants.IN_PROGRESS
import info.camposha.tungusta.Constants.SUCCEEDED
import info.camposha.tungusta.Utils.loadBackground
import info.camposha.tungusta.databinding.ActivitySimpleListBinding
import info.camposha.tungusta.databinding.GridBinding


open class SimpleListActivity : TungustaBaseActivity() {
    //We define our instance fields
    protected var b: ActivitySimpleListBinding? = null
    private var lm: GridLayoutManager? = null
    private var adapter: EasyAdapter<Item, GridBinding>? = null
    private lateinit var rv: RecyclerView
    var items: ArrayList<Item> = ArrayList()
    protected var pageToFetch = 0

    private var clickListener: ItemClickListener? = null


    fun attachClickListener(itemClickListener: ItemClickListener?) {
        this.clickListener = itemClickListener
    }

    private fun initStuff() {
        rv = b!!.rv
//        lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lm = GridLayoutManager(this, 3)

        rv.layoutManager = lm
        val infiniteScrollProvider = InfiniteScrollProvider()
        infiniteScrollProvider.attach(rv, object : OnLoadMoreListener {
            override fun onLoadMore() {
                pageToFetch += 1
            }
        })
    }

    private fun sendResult(item: Groupable) {
        intent.putExtra("_KEY", item)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setupAdapter() {
        adapter = object : EasyAdapter<Item, GridBinding>(R.layout.grid) {
            override fun onBind(binding: GridBinding, item: Item) {
                binding.textView.text = item.getItemName()
                if (item.getItemImageUrl().isNotEmpty()) {
                    loadBackground(item.getItemImageUrl(), binding.conLayout)
                }
                binding.conLayout.setOnClickListener {
                    if (clickListener != null) {
                        clickListener!!.onItemClicked(item)
                    }
                }
            }
        }
        adapter!!.addAll(MEM_CACHE_ITEMS, true)
        rv.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        /*
        We are holding our list in our memory cache. For most apps
        this isn't an issue, especially since we are paging data.
        However still if your app grows and you have
        massive amounts of data, and the user happens to download most
        of them, we would prefer to release that cache
        when the user navigates away from this page.
        */
        MEM_CACHE_CATEGORIES.clear()
        MEM_CACHE_ITEMS.clear()
    }

    /**
     * When our activity is created, we will setup stuff
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_simple_list)
        //setupStuff()
        initStuff()
        setupAdapter()

    }


    protected open fun fetch(progressMsg: String) {
        val r = RequestCall()
        r.status = IN_PROGRESS
        r.message = progressMsg
        val result = makeRequest(r, "ITEMS FETCHING")
    }


    protected open fun onDataFetched(status: Int, msg: String?, data: ArrayList<Groupable?>?) {
        val r = RequestCall()
        when (status) {
            SUCCEEDED -> {
                r.status = SUCCEEDED
            }
            IN_PROGRESS -> {
                r.status = IN_PROGRESS
            }
            else -> {
                r.status = FAILED
            }
        }
        r.message = msg
        val result = makeRequest(r, "ITEMS FETCHING")
        if (result == SUCCEEDED) {
            if (!data.isNullOrEmpty()) {
                r.items = data
                show("Found " + r.items!!.size)
                for (i in r.items!!) {
                    if (i != null) {
                        if (!Utils.alreadyExists(i)) {
                            val g: Groupable = i
                            val item = Item()
                            item.name = g.getItemName()
                            item.group = g.getGroupName()
                            item.imageURL =  g.getItemImageUrl()
                            MEM_CACHE_ITEMS.add(item)
                        }
                    }
                }
                setupAdapter()
            } else {
                if (MEM_CACHE_ITEMS.isEmpty()) {
                    show("It seems you don't have any data in the server")
                } else {
                    show("Reached End")
                }

            }

        }
    }

}