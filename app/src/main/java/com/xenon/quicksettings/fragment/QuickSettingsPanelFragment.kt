package com.xenon.quicksettings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xenon.quicksettings.databinding.FragmentQuickSettingsBinding

class QuickSettingPanelFragment : Fragment() {

    private var _binding: FragmentQuickSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuickSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listeners for the buttons in the GridLayout
        binding.wifiButton.setOnClickListener {
            // Handle Wi-Fi button click
        }

        binding.bluetoothButton.setOnClickListener {
            // Handle Bluetooth button click
        }

        // Add more buttons and click listeners as needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}