package info.camposha.tungusta

import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import info.camposha.tungusta.Constants.FAILED
import info.camposha.tungusta.Constants.IN_PROGRESS
import info.camposha.tungusta.Constants.SUCCEEDED
import kotlinx.android.synthetic.main._state.*
import java.io.File

abstract class TungustaBaseActivity : AppCompatActivity() {
    @JvmField
    protected var a = this

    protected fun show(message: String?) {
        var msg=message
        if (msg==null)msg=""
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    protected fun openPage(clazz: Class<*>?) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
    /**
     * This method will allow us send a serialized p objec  to a specified
     * activity
     */
    fun sendToActivity(g: Groupable?, clazz: Class<*>?) {
        val i = Intent(this, clazz)
        i.putExtra("KEY", g)
        startActivity(i)
    }

    /**
     * This method will allow us receive a serialized scientist, deserialize it and return it,.
     */
    fun receive(intent: Intent): Item? {
        try {
            return intent.getSerializableExtra("KEY") as Item
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    protected fun validate(file: File?,isFileRequired: Boolean,
        vararg editTexts: EditText
    ): Boolean {
        val nameTxt = editTexts[0]
        val descriptionTxt = editTexts[1]
        val galaxyTxt = editTexts[2]
        if (file == null && isFileRequired) {
            show("Image is required")
            return false
        }
        if (nameTxt.text == null || nameTxt.text.toString().isEmpty()) {
            nameTxt.error = "Name is Required Please!"
            return false
        }
        if (descriptionTxt.text == null || descriptionTxt.text.toString().isEmpty()) {
            descriptionTxt.error = "Description is Required Please!"
            return false
        }
        if (galaxyTxt.text == null || galaxyTxt.text.toString().isEmpty()) {
            galaxyTxt.error = "Galaxy is Required Please!"
            return false
        }
        return true
    }

    protected fun clearEditTexts(vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.setText("")
        }
    }




    /**
     * This is our progress and messages card to be re-used across several activities.
     * @param title - Title of card
     * @param msg - Message
     * @param isShowing
     * @param STATE
     */
    private fun createStateCard(title: String?, msg: String?, isShowing: Boolean, STATE: Int) { //state widgets
        val handler = Handler()
        val delayedHiding = Runnable { sectionLayout.visibility = View.GONE }
        if (isShowing) {
            sectionLayout.visibility = View.VISIBLE
            titleTV.text = title
            msgTV.text = msg
            when (STATE) {
                FAILED -> {
                    stateImg.setImageResource(R.drawable.error_icon)
                    stateImg.visibility=View.VISIBLE
                    sectionLayout.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
                    pb.visibility = View.GONE
                    handler.postDelayed(delayedHiding, 10000)
                }
                IN_PROGRESS -> {
                    sectionLayout.setBackgroundColor(resources.getColor(R.color.color_7))
                    stateImg.visibility=View.GONE
                    pb.visibility = View.VISIBLE
                }
                SUCCEEDED -> {
                    sectionLayout.setBackgroundColor(resources.getColor(R.color.color_7))
                    stateImg.setImageResource(R.drawable.ok_check)
                    stateImg.visibility=View.VISIBLE
                    pb.visibility = View.GONE
                    handler.postDelayed(delayedHiding, 10000)
                }

            }
        } else {
            sectionLayout.visibility = View.GONE
        }
        closeBtn.setOnClickListener { v: View? ->
            sectionLayout.visibility = View.GONE
        }
    }

    fun makeRequest(r: RequestCall?, OPERATION: String): Int {
        if (r == null) {
            createStateCard("$OPERATION FAILED", "Null RequestCall Received", true, FAILED)
        } else {
            if (r.status == IN_PROGRESS) {
                createStateCard("$OPERATION IN PROGRESS", r.message, true, IN_PROGRESS)
            } else if (r.status == FAILED) {
                createStateCard("WHOOPS!", r.message, true, FAILED)
            } else if (r.status == SUCCEEDED) {
                if (r.items == null || r.items!!.size == 0) {
                    createStateCard(
                        "CONGRATS",
                        r.message,
                        true,
                        SUCCEEDED
                    )
                } else {
                    if (r.items!!.size == 0) {
                        createStateCard(
                            "SUCCESS!",
                            "However no data found in your server.",
                            true,
                            SUCCEEDED
                        )
                    } else {
                        createStateCard(
                            "CONGRATS!",
                            "Successfully fetched " + r.items!!.size + " users.",
                            true,
                            SUCCEEDED
                        )
                    }
                }
            }
            return r.status
        }
        return -999
    }
}