package info.camposha.tungusta

import `in`.mayanknagwanshi.imagepicker.ImageSelectActivity
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import info.camposha.tungusta.databinding.CrudBinding
import java.io.File

open class TungustaCrudActivity : TungustaBaseActivity() {

    protected var chosenFile: File? = null
    protected var b: CrudBinding? = null

    protected fun prepareEditTetxts() {
        val editTexts = arrayOf(
            b?.firstTxt, b?.secondTxt, b?.thirdTxt,
            b?.fourthTxt, b?.fifthTxt, b?.sixthTxt, b?.seventhTxt
        )
        for (e in editTexts) {
            if (e?.hint.isNullOrEmpty()) {
                e?.visibility = View.GONE
            } else {
                e?.visibility = View.VISIBLE
            }
        }
    }

    protected fun setupHints(vararg hints: String) {
        val editTexts = arrayOf(
            b?.firstTxt, b?.secondTxt, b?.thirdTxt,
            b?.fourthTxt, b?.fifthTxt, b?.sixthTxt, b?.seventhTxt
        )
        for (e in editTexts) {
            e?.visibility = View.GONE
        }
        for ((i, hint) in hints.withIndex()) {
            if(editTexts[i] != null){
                editTexts[i]?.hint = hint
                editTexts[i]?.visibility=View.VISIBLE
            }
        }
    }

    protected open fun captureImage() {
        val i = Intent(this, ImageSelectActivity::class.java)
        i.putExtra(ImageSelectActivity.FLAG_COMPRESS, false) //default is true
        i.putExtra(ImageSelectActivity.FLAG_CAMERA, true) //default is true
        i.putExtra(ImageSelectActivity.FLAG_GALLERY, true) //default is true
        startActivityForResult(i, 1213)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1213) {
                val filePath =
                    data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH)
                if (filePath != null) {
                    chosenFile = File(filePath)
                    Picasso.get().load(chosenFile!!).error(R.drawable.image_not_found)
                        .into(b!!.topImageView)
                }
            }
        }
//        resumedAfterImagePicker = true
    }

    private fun handleEvents() {
        b?.captureImg?.setOnClickListener {
            captureImage()
        }

    }

    protected open fun onOperationPerformed(status: Int, msg: String?) {
        val r = RequestCall()
        when (status) {
            Constants.SUCCEEDED -> {
                r.status = Constants.SUCCEEDED
            }
            Constants.IN_PROGRESS -> {
                r.status = Constants.IN_PROGRESS
            }
            else -> {
                r.status = Constants.FAILED
            }
        }
        r.message = msg
        makeRequest(r, "ITEMS FETCHING")
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()

        if (chosenFile != null) {
            Picasso.get().load(chosenFile!!).error(R.drawable.image_not_found)
                .into(b!!.topImageView)
        }
    }

    /**
     * Let's override our onCreate() method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.crud)
        handleEvents()
    }
}