package info.camposha.tungusta_app

import android.content.Intent
import android.os.Bundle
import info.camposha.tungusta.Groupable
import info.camposha.tungusta.ItemClickListener
import info.camposha.tungusta.TungustaGroupedActivity


class GroupedActivity : TungustaGroupedActivity(), ItemClickListener {

    val data = arrayListOf(
        "Mercury", "Venus", "Earth", "Mars", "Jupiter",
        "Saturn", "Uranus", "Neptune", "Pluto"
    )
    val planets = ArrayList<Groupable?>()


    private fun generateData(): ArrayList<Groupable?> {
        Cachee.PLANETS.clear()
        var i = 0
        for (d in resources.getStringArray(R.array.countries)) {
            val p=Planet(i.toString(), d, "Group " +d.toCharArray()[0])
            planets.add(p)
            Cachee.PLANETS.add(p)
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
        b!!.headerTV.text="Countries"
        fetch("Fetching Items")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.attachClickListener(this)
    }

    override fun onItemClicked(item: Groupable) {
        val i = Intent(this, DetailActivity::class.java)
        val p=Cachee.findById(item.getItemID())
        if(p != null){
            i.putExtra("KEY", p)
            startActivity(i)
        }
    }


}