package info.camposha.tungusta

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import info.camposha.tungusta.databinding.ActivityDetailBinding

class TungustaDetailActivityTungusta : TungustaBaseActivity() {

    protected var b: ActivityDetailBinding? = null
    //Let's define our instance fields

    private fun handleEvents(){
    }
    private fun receiveData(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        handleEvents()
    }
}