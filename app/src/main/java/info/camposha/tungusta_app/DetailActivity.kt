package info.camposha.tungusta_app

import android.content.Intent
import android.os.Bundle
import info.camposha.tungusta.TungustaDetailActivity


class DetailActivity : TungustaDetailActivity() {

    private var p: Planet?=null

    override fun onResume() {
        super.onResume()
        val s = intent.getSerializableExtra("KEY")
        if (s != null) {
            p=s as Planet
            b!!.mCollapsingToolbar.title= p!!.name
            b!!.contentTV.text = p!!.name
            b!!.firstTV.text = p!!.group

        }
        b?.fab!!.setImageResource(android.R.drawable.ic_menu_edit)

        b?.fab!!.setOnClickListener {
            if(p != null){
                val i = Intent(this, CRUDActivity::class.java)
                i.putExtra("KEY",p)
                startActivity(i)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}