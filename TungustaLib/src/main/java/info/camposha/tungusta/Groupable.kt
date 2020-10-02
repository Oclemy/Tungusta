package info.camposha.tungusta

import java.io.Serializable

/**
 * ANDROID: http://www.camposha.info : Oclemy.
 */
interface Groupable : Serializable {
    fun setItemID(id: String)
    fun getItemID(): String
    fun setItemName(name: String)
    fun getItemName(): String
    fun setGroupName(group: String)
    fun getGroupName(): String
    fun setItemImageUrl(imageUrl: String)
    fun getItemImageUrl(): String


}