package info.camposha.tungusta

object Constants {
    //supply your ip address. Type ipconfig while connected to internet to get your
//ip address in cmd. Read the readme file for more details.
// For example below I have used my ip address
const val BASE_URL = "http://192.168.43.91/PHP/user/panaroma/";
//The below line points to our hosted online demo database
    //const val BASE_URL = "https://camposha.info/php/user/ums/"
    //const val BASE_URL = "https://camposha.info/php/user/ums/"

    //The following will work for majority of emulators like
// android studio emulator, bluestacks etc
//private  static  final String base_url = "http://10.0.2.2/php/user/ums/";
//if you use genymotion emulator then use below
//private  static  final String base_url = "http://10.0.3.2/php/user/ums/";
//If you change the base_url don't forget to change the images url
    //const val IMAGES_BASE_URL = "https://camposha.info/php/user/ums/images/"
    const val IMAGES_BASE_URL = BASE_URL+"images/"
    //If you are using phone and hosting your database locally then use
//your ip address
//private  static  final String base_url = "http://YOUR_IP_ADDRESS/php/user/ums/";
    @JvmField
    val GALAXIES = arrayOf(
        "Circunus Galaxy", "Andromeda", "Milky Way", "IC 1101", "Pinwheel",
        "Cartwheel", "Sombrero", "StarBust", "Whirlpool", "Black Eye Galaxy", "Hoag's Object",
        "Large Magellonic Cloud", "Centaurus A", "Leo", "Messier 87",
        "Wolf Lunmark Melotte"
    )
    @JvmField
    val GENDER = arrayOf(
        "MALE", "FEMALE"
    )
    //These variables will represent different states of our operation
    const val IN_PROGRESS = 0
    const val SUCCEEDED = 1
    const val FAILED = -1
    const val REACHED_END = 2
    //These variables will be sent to the server to identify the operations
//we are performing
    const val CREATE_ACCOUNT = "CREATE_ACCOUNT"
    const val UPDATE_ONLY_TEXT = "UPDATE_ONLY_TEXT"
    const val UPDATE_IMAGE_TEXT = "UPDATE_IMAGE_TEXT"
    const val FETCH_PAGINATED_USERS = "FETCH_PAGINATED_USERS"
    const val FETCH_ALL_USERS = "FETCH_ALL_USERS"
    const val DELETE_ACCOUNT = "DELETE_ACCOUNT"
    const val CHANGE_ACCOUNT_STATUS = "CHANGE_ACCOUNT_STATUS"
    const val LOGIN = "LOGIN"
    const val RESET_PASSWORD = "RESET_PASSWORD"
    const val CHANGE_ROLE = "CHANGE_ROLE"
    const val FRIEND = "FRIEND"
    const val UNFRIEND = "UNFRIEND"
    const val UPLOAD = "UPLOAD"
    const val UPLOAD_PHOTO = "UPLOAD_PHOTO"
    const val LIKE = "LIKE"
    const val UNLIKE = "UNLIKE"
    const val DELETE_PHOTO = "DELETE_PHOTO"
    const val FETCH_PAGINATED_PHOTOS = "FETCH_PAGINATED_PHOTOS"


    //will identify our cache
    const val CACHE_KEY_USERS = "CACHE_KEY_USERS"
    const val CACHE_KEY_PHOTOS = "CACHE_KEY_PHOTOS"
}