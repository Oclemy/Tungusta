package info.camposha.tungusta_app

import info.camposha.tungusta.Groupable

/**
 * ANDROID: http://www.camposha.info : Oclemy.
 */
class Planet: Groupable {
    var id=""
    var name=""
    var group=""

    constructor(id: String, name: String, group: String) {
        this.id = id
        this.name = name
        this.group = group
    }


    override fun setItemID(id: String) {
        this.id=id
    }

    override fun getItemID(): String {
        return  id
    }

    override fun setItemName(name: String) {
        this.name=name
    }

    override fun getItemName(): String {
        return name
    }

    override fun setGroupName(category: String) {
        this.group=category
    }

    override fun getGroupName(): String {
        return group
    }

    override fun setItemImageUrl(imageUrl: String) {

    }

    override fun getItemImageUrl(): String {
        return ""
    }
}