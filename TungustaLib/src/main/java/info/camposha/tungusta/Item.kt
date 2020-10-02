package info.camposha.tungusta

import java.io.Serializable

class Item : Serializable, Groupable {
    var id = ""
    var name = ""
    var group = ""
    var imageURL = ""

    override fun setItemID(id: String) {
        this.id = id
    }

    override fun getItemID(): String {
        return id
    }

    override fun setItemName(name: String) {
        this.name=name
    }

    override fun getItemName(): String {
        return name
    }


    override fun setGroupName(group: String) {
        this.group = group
    }

    override fun getGroupName(): String {
        return group
    }

    override fun setItemImageUrl(imageUrl: String) {
        this.imageURL = imageUrl
    }

    override fun getItemImageUrl(): String {
        return imageURL
    }

}