package info.camposha.tungusta_app

import java.util.ArrayList

object Cachee {
    @JvmField
    val PLANETS: ArrayList<Planet> = ArrayList()

    @JvmStatic
    fun findById(id: String): Planet? {
        val hits=PLANETS.filter { id==it.id }
        return hits[0]
    }
}