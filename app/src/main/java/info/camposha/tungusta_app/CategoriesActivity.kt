package info.camposha.tungusta_app

import android.content.Intent
import android.os.Bundle
import info.camposha.tungusta.Groupable
import info.camposha.tungusta.ItemClickListener
import info.camposha.tungusta.SimpleListActivity

class CategoriesActivity : SimpleListActivity(), ItemClickListener {

    val data = arrayListOf<String>(
        "Mercury", "Venus", "Earth", "Mars", "Jupiter",
        "Saturn", "Uranus", "Neptune", "Pluto"
    )
    val planets = ArrayList<Groupable?>()

    private fun generateData(): ArrayList<Groupable?> {
        var i = 0
        for (d in resources.getStringArray(R.array.countries)) {
            planets.add(Planet(i.toString(), d, "GROUP: " +d.toCharArray()[0]))
            i++
        }
        return planets
    }

    override fun fetch(progressMsg: String) {
        super.fetch(progressMsg)
        onDataFetched(
            1,
            "Successfully Generated Planets",
            generateData()
        )
    }

    override fun onResume() {
        super.onResume()
        b!!.headerTV.text="Categories"
        fetch("Fetching Planets")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachClickListener(this)
    }

    override fun onItemClicked(g: Groupable) {
        show(g.getGroupName())
        val i=Intent(this,GroupedActivity::class.java)
        startActivity(i)
    }
}