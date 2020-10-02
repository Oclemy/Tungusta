package info.camposha.tungusta

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import easyadapter.dc.com.library.EasyAdapter
import info.camposha.tungusta.CacheManager.MEM_CACHE_CATEGORIES
import info.camposha.tungusta.CacheManager.MEM_CACHE_ITEMS
import info.camposha.tungusta.Constants.FAILED
import info.camposha.tungusta.Constants.IN_PROGRESS
import info.camposha.tungusta.Constants.SUCCEEDED
import info.camposha.tungusta.Utils.getForThisCategory
import info.camposha.tungusta.Utils.getImageURLs
import info.camposha.tungusta.Utils.loadBackground
import info.camposha.tungusta.databinding.ActivityListWithCarouselBinding
import info.camposha.tungusta.databinding.HorizontalBinding
import info.camposha.tungusta.databinding.VerticalBinding
import java.util.*
import kotlin.collections.ArrayList

open class TungustaGroupedActivity : TungustaBaseActivity() {
    //We define our instance fields
    protected var b: ActivityListWithCarouselBinding? = null
    private var lm: LinearLayoutManager? = null
    private var networkImages = arrayOf<String?>()
    private var HAS_ALREADY_REACHED_END = false
    private var outerAdapter: EasyAdapter<Item, VerticalBinding>? = null
    private var innerAdapter: EasyAdapter<Item, HorizontalBinding>? = null
    private lateinit var rv: RecyclerView
    var items: ArrayList<Item> = ArrayList()
    protected var pageToFetch = 0
    private var clickListener: ItemClickListener? = null


    fun attachClickListener(itemClickListener: ItemClickListener?) {
        this.clickListener = itemClickListener
    }

    // To set simple images
    private val imageListener =
        ImageListener { position: Int, imageView: ImageView? ->
            if(!networkImages[position].isNullOrEmpty()){
                Picasso.get().load(networkImages[position]).placeholder(R.drawable.load_glass)
                    .error(R.drawable.image_not_found).fit()
                    .centerCrop().into(imageView)
            }
        }

    private fun initStuff() {
        rv = b!!.rv
        lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = lm
        val infiniteScrollProvider = InfiniteScrollProvider()
        infiniteScrollProvider.attach(rv, object : OnLoadMoreListener {
            override fun onLoadMore() {
                pageToFetch += 1
                //this method called when recycler view scrolled to the end
                fetch(pageToFetch.toString())
            }
        })
    }

    //Lets setup our slider
    private fun setupCarousel() {
        b!!.carouselView.pageCount = networkImages.size
        networkImages = getImageURLs(MEM_CACHE_ITEMS)
        b!!.carouselView.setImageListener(imageListener)
        b!!.carouselView.setImageClickListener { position: Int ->
            if (!networkImages.isNullOrEmpty()) {
                show(networkImages[position])
            }
        }
        //hide carousel if there is no image
        b!!.carouselView.visibility = if (networkImages.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupInnerAdapter() {
        innerAdapter = object : EasyAdapter<Item, HorizontalBinding>(R.layout.horizontal) {
            override fun onBind(binding: HorizontalBinding, item: Item) {
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
    }

    private fun setupAdapters() {

        outerAdapter = object : EasyAdapter<Item, VerticalBinding>(R.layout.vertical) {
            override fun onBind(binding: VerticalBinding, p: Item) {
                setupInnerAdapter()
                if(p.getGroupName().isNotEmpty()){
                    binding.tvTitle.text =   p.getGroupName().toUpperCase(Locale.getDefault())
                }else{
                    binding.tvTitle.text =  "UNCATEGORIZED"
                }
                binding.rvHorizontal.setHasFixedSize(true)
                binding.rvHorizontal.layoutManager = LinearLayoutManager(
                    this@TungustaGroupedActivity,
                    LinearLayoutManager.HORIZONTAL, false
                )

                val thisGroup = getForThisCategory(MEM_CACHE_ITEMS, p.getGroupName()!!)
                innerAdapter!!.clear(true)
                innerAdapter!!.addAll(thisGroup, true)
                binding.rvHorizontal.adapter = innerAdapter
                binding.rvHorizontal.isNestedScrollingEnabled = false
                binding.btnMore.setOnClickListener {
                    show(thisGroup.size.toString())
                }
            }
        }
        rv.layoutManager = lm
        outerAdapter!!.addAll(items, true)
        rv.adapter = outerAdapter
    }

    private fun refreshAdapter() {
        val all = Utils.getAllGroupedByCategory(MEM_CACHE_ITEMS)
        //val outer = ArrayList<Item>()
        //items.clear()
        for (l in all) {
            if (!l.isNullOrEmpty()) {
                val item = l[0]
                if (!items.contains(item)) {
                    var group = item.getGroupName()
                    if (group.isEmpty()) {
                        group = "UNCATEGORIZED"
                    }
                    item.setGroupName(group)
                    items.add(item)
                    outerAdapter!!.add(item)
                }

            }
        }
        outerAdapter!!.notifyDataSetChanged()
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
        MEM_CACHE_ITEMS.clear()
        MEM_CACHE_CATEGORIES.clear()
    }

    /**
     * When our activity is created, we will setup stuff
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_list_with_carousel)
        //setupStuff()
        initStuff()
        setupAdapters()
        setupCarousel()

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
                            item.id = g.getItemID()
                            item.name = g.getItemName()
                            item.group = g.getGroupName()
                            item.imageURL = g.getItemImageUrl()
                            MEM_CACHE_ITEMS.add(item)
                        }
                    }
                }
                refreshAdapter()
                //extract image urls from our list and setup carousel
                setupCarousel()
            } else {
                if (MEM_CACHE_ITEMS.isEmpty()) {
                    show("It seems you don't have any data in the server")
                } else {
                    show("Reached End")
                    HAS_ALREADY_REACHED_END = true
                }

            }

        }
    }

}