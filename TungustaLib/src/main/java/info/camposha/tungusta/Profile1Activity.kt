package info.camposha.tungusta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import info.camposha.tungusta.databinding.Profile1Binding

class Profile1Activity : AppCompatActivity() {
    private var b: Profile1Binding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this,R.layout.profile1)
    }

}