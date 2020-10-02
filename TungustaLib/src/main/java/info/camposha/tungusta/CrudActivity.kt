package info.camposha.tungusta

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import info.camposha.tungusta.databinding.CrudBinding

open class CrudActivity : AppCompatActivity() {
    private var b: CrudBinding?=null

    private fun setupPage() {
        if(b != null){
            val editTexts= arrayOf(b!!.firstTxt, b!!.secondTxt, b!!.thirdTxt,
            b!!.fourthTxt,b!!.fifthTxt,b!!.sixthTxt,b!!.seventhTxt)
            for (e in editTexts){
                if(e.hint.isNullOrEmpty()){
                    e.visibility= View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupPage()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this,R.layout.crud)
    }

}