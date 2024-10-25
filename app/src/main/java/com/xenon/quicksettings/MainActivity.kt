package com.xenon.quicksettings

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showBottomSheetButton = findViewById<Button>(R.id.showBottomSheetButton)
        showBottomSheetButton.setOnClickListener {
            showBottomSheet()
        }
    }

    @SuppressLint("InflateParams")
    private fun showBottomSheet() {
        val bottomSheetDialog =
            BottomSheetDialog(this, R.style.XenonBottomSheetDialogTheme) // Apply style
        val view = layoutInflater.inflate(R.layout.quickpanel_layout, null)
        bottomSheetDialog.setContentView(view)

        // Get the TextView from the inflated layout
        val dateTextView = view.findViewById<TextView>(R.id.date)

        // Format the date
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd.MM.yy EE", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)

        // Set the formatted date to the TextView
        dateTextView.text = formattedDate

        bottomSheetDialog.show()
    }
}