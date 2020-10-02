package info.camposha.tungusta

/**
 * Our RequestCall class
 * This class will represent a single request we make against our server
 */
class RequestCall {
    var status = 0
    var message: String? = "UNKNOWN MESSAGE"
    var item: Any? = null
    var items: ArrayList<Groupable?>? = ArrayList()
}