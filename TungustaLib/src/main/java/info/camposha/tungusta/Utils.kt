package info.camposha.tungusta

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yarolegovich.lovelydialog.LovelyChoiceDialog
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import info.camposha.tungusta.CacheManager.MEM_CACHE_CATEGORIES
import info.camposha.tungusta.CacheManager.MEM_CACHE_ITEMS


/**
 * A Utility class. Contains reusable utility methods we will use throughout our project.
 * This
 * class will save us from typing lots of repetitive code.
 */
object Utils {

    @JvmStatic
    fun show(c: Context?, message: String?) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * This utility method will allow us open any activity.
     */
    @JvmStatic
    fun openActivity(c: Context, clazz: Class<*>?) {
        val intent = Intent(c, clazz)
        c.startActivity(intent)
    }

    /**
     * This method will allow us show an Info dialog anywhere in our app.
     */
    @JvmStatic
    fun showInfoDialog(activity: AppCompatActivity, title: String?, message: String?) {
        LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
            .setTopColorRes(R.color.colorPrimary)
            .setButtonsColorRes(R.color.colorAccent)
            .setIcon(R.drawable.follow)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Relax") { }
            .setNegativeButton("Go Back") { activity.finish() }
            .show()
    }

    /**
     * This method will allow us show a single select dialog where we can select and return an
     * item to an edittext.
     */

    @JvmStatic
    fun selectCountry(activity: AppCompatActivity?, galaxyTxt: EditText) {
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            activity!!,
            android.R.layout.simple_list_item_1,
            activity.resources.getStringArray(R.array.countries)
        )
        LovelyChoiceDialog(activity)
            .setTopColorRes(R.color.colorPrimary)
            .setTitle("Country Picker")
            .setTitleGravity(Gravity.CENTER_HORIZONTAL)
            .setIcon(R.drawable.follow)
            .setMessage("Select the Country of this User.")
            .setMessageGravity(Gravity.CENTER_HORIZONTAL)
            .setItems(
                adapter
            ) { _: Int, item: String? ->
                galaxyTxt.setText(
                    item
                )
            }
            .show()
    }

    fun selectGender(activity: AppCompatActivity?, genderTxt: EditText) {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            activity!!,
            android.R.layout.simple_list_item_1,
            activity.resources.getStringArray(R.array.gender)
        )
        LovelyChoiceDialog(activity)
            .setTopColorRes(R.color.colorAccent)
            .setTitle("User Gender Picker")
            .setTitleGravity(Gravity.CENTER_HORIZONTAL)
            .setIcon(R.drawable.follow)
            .setMessage("Select the Gender of this User.")
            .setMessageGravity(Gravity.CENTER_HORIZONTAL)
            .setItems(
                adapter
            ) { _: Int, item: String? ->
                genderTxt.setText(
                    item
                )
            }
            .show()
    }


    @JvmStatic
    fun selectDate(activity: AppCompatActivity, dateTxt: EditText) {
        dateTxt.setOnClickListener {
            val dialog =
                DatePickerFragmentDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
                    var monthOfYear = monthOfYear
                    monthOfYear += 1
                    val month: String
                    month = if (monthOfYear < 10) {
                        "0$monthOfYear"
                    } else {
                        monthOfYear.toString()
                    }
                    val day: String = if (dayOfMonth < 10) {
                        "0$dayOfMonth"
                    } else {
                        dayOfMonth.toString()
                    }
                    dateTxt.setText("$year-$month-$day")
                }
            dialog.show(activity.supportFragmentManager, "DATE_PICKER")
        }
    }

    /**
     * This method will allow us send a serialized p objec  to a specified
     * activity
     */
    @JvmStatic
    fun sendToActivity(c: Context, p: Item?, clazz: Class<*>?) {
        val i = Intent(c, clazz)
        i.putExtra("KEY", p)
        c.startActivity(i)
    }

    /**
     * This method will allow us receive a serialized scientist, deserialize it and return it,.
     */
    @JvmStatic
    fun receive(intent: Intent, c: Context?): Item? {
        try {
            return intent.getSerializableExtra("KEY") as Item
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun loadImageFromNetwork(imageURL: String?, fallBackImage: Int, imageView: ImageView?) {
        if (imageURL != null && imageURL.isNotEmpty()) {
            Picasso.get().load(imageURL).placeholder(R.drawable.placeholder)
                .error(R.drawable.image_not_found).into(imageView)
        } else {
            Picasso.get().load(fallBackImage).into(imageView)
        }
    }

    @JvmStatic
    fun getImageURLs(items: List<Item>): Array<String?> {
        val urls = ArrayList<String>()
//        val imageURLs = arrayOfNulls<String>(items.size)
        for ((i, item) in items.withIndex()) {
            if (item.imageURL.isNotEmpty()) {
//                imageURLs[i] =item.getItemImageUrl()
                urls.add(item.getItemImageUrl())
            }
        }
        return urls.toTypedArray()
    }

    @JvmStatic
    fun getPhotoImageURLs(items: List<Item>): Array<String?> {
        val imageURLs = arrayOfNulls<String>(items.size)
        for ((i, photo) in items.withIndex()) {
            imageURLs[i] = photo.imageURL
        }
        return imageURLs
    }

    @JvmStatic
    fun createItem(g: Groupable): Item {
        var item = Item()
        item.group = g.getGroupName()
        item.imageURL = g.getItemImageUrl()
        return item
    }

    @JvmStatic
    fun alreadyExists(item: Groupable): Boolean {
        var found = false
        for (p in MEM_CACHE_ITEMS) {
            if (MEM_CACHE_ITEMS.contains(createItem(item))) {
                found = true
            }
        }
        return found
    }

    @JvmStatic
    fun categoryAlreadyExists(category: String): Boolean {
        var found = false
        for (c in MEM_CACHE_CATEGORIES) {
            if (MEM_CACHE_CATEGORIES.contains(category)) {
                found = true
            }
        }
        return found
    }

    @JvmStatic
    fun valOf(editText: EditText): String {
        return editText.text.toString()
    }

    @JvmStatic
    fun loadBackground(imageURL: String?, layout: ConstraintLayout?) {
        if (imageURL != null && imageURL.isNotEmpty() && layout != null) {
            val img = ImageView(layout.context)

            Picasso.get().load(imageURL).into(img, object : Callback {
                override fun onSuccess() {
                    layout.background = (img.getDrawable())
                }

                override fun onError(e: java.lang.Exception?) {
                }
            })
        }
    }

    /**
     * Filter a list of news items based on a date
     */
    @JvmStatic
    fun getForThisCategory(all: ArrayList<Item>, category: String): ArrayList<Item> {
        val filtered = ArrayList<Item>()

        if (all == null) {
            return filtered
        }
        for (p in all) {
            if (p.getGroupName().equals(category, true)) {

                filtered.add(p as Item)
            }
        }
        return filtered
    }

    /**
     * Return a List of Lists. Return news grouped by their publishing dates
     */
    @JvmStatic
    fun getAllGroupedByCategory(all: ArrayList<Item>): ArrayList<ArrayList<Item>> {
        val groupedByCategory = ArrayList<ArrayList<Item>>()
        val remaining = ArrayList<Item>()
        remaining.addAll(all)

        for (n in all) {
            if (n.getGroupName() != null) {
                val currentGroup = getForThisCategory(remaining, n.getGroupName().toString())
                if (currentGroup.size > 0) {
                    if (!groupedByCategory.contains(currentGroup)) {
                        groupedByCategory.add(currentGroup)
                        for (currentInner in currentGroup) {
                            remaining.remove(currentInner)
                        }
                    }
                }
            }
        }
        return groupedByCategory
    }
}