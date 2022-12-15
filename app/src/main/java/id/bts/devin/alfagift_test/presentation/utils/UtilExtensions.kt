package id.bts.devin.alfagift_test.presentation.utils

import android.view.View
import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import id.bts.devin.alfagift_test.R
import java.time.LocalDate

fun ImageView.loadAvatar(image: Any?) {
    this.load(image) {
        crossfade(true)
        transformations(CircleCropTransformation())
        placeholder(R.drawable.img_avatar_placeholder)
    }
}

fun String.getYear(): String {
    val date = LocalDate.parse(this)
    return date.year.toString()
}

fun View.showSnackBar(message: String?) {
    Snackbar.make(this, message ?: "Error", Snackbar.LENGTH_LONG).show()
}