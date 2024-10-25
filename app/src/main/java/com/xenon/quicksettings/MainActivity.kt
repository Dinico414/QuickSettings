package com.xenon.quicksettings

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showBottomSheetButton = findViewById<Button>(R.id.showBottomSheetButton)
        showBottomSheetButton.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheetDialog =
            BottomSheetDialog(this) // Apply style
        val view = layoutInflater.inflate(R.layout.quickpanel_layout, null)
        bottomSheetDialog.setContentView(view)

        // Set peek height (optional)
        val behavior = bottomSheetDialog.behavior
        behavior.peekHeight = 2000 // Adjust as needed

        bottomSheetDialog.show()
    }
}