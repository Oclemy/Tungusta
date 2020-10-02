package info.camposha.tungusta

import java.util.*

/**
 * Caching helps make our app faster. We can easily access the variables
 * we need from memory or the lists from hard disk. This alllows us to
 * reduce making unnecessary requests to re-download data.
 */
object CacheManager {
    @JvmField
    var DISK_CACHE_INITIALIZED = false
    @JvmField
    val MEM_CACHE_ITEMS: ArrayList<Item> = ArrayList()
    @JvmField
    val MEM_CACHE_CATEGORIES: ArrayList<Any> = ArrayList()


}