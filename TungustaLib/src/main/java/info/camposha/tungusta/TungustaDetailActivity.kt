package info.camposha.tungusta

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import info.camposha.tungusta.databinding.Detail2Binding

open class TungustaDetailActivity : AppCompatActivity() {
    protected var b: Detail2Binding?=null


    protected fun setupTextViews() {
        if (b?.firstTV!!.text.isNullOrEmpty() && b?.secondTV!!.text.isNullOrEmpty() ){
            b?.row1Layout!!.visibility=View.GONE
            b?.divider1!!.visibility=View.GONE
        }
        if (b?.thirdTV!!.text.isNullOrEmpty() && b?.fourthTV!!.text.isNullOrEmpty() ){
            b?.row2Layout!!.visibility=View.GONE
            b?.divider2!!.visibility=View.GONE
        }
        if (b?.fifthTV!!.text.isNullOrEmpty() && b?.sixthTV!!.text.isNullOrEmpty() ){
            b?.row3Layout!!.visibility=View.GONE
            b?.divider3!!.visibility=View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        setupTextViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this, R.layout.detail2)
    }
}