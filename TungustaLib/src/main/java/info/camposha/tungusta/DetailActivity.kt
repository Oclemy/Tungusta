package info.camposha.albireo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import info.camposha.tungusta.R
import info.camposha.tungusta.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private var b: ActivityDetailBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=DataBindingUtil.setContentView(this, R.layout.detail1)
    }
}