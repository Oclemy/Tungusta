package info.camposha.albireo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import info.camposha.tungusta.R
import info.camposha.tungusta.databinding.LoginBinding

class LoginActivity : AppCompatActivity() {
    private var b: LoginBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this, R.layout.login)
    }

}