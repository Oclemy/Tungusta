package info.camposha.tungusta_app

import android.os.Bundle
import info.camposha.tungusta.TungustaCrudActivity


class CRUDActivity : TungustaCrudActivity() {

    override fun onResume() {
        super.onResume()
        setupHints("ID", "Enter Name", "Enter Group", "Enter Other")

        val s = intent.getSerializableExtra("KEY")
        if (s != null) {
            val p = s as Planet
            b?.firstTxt?.setText(p.id)
            b?.secondTxt?.setText(p.group)
            b?.thirdTxt?.setText(p.name)
            b!!.headerTV.text = "Edit Planet"

        } else {
            b!!.headerTV.text = "Upload New Planet"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}