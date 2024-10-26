package com.xenon.quicksettings.fragment

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.xenon.quicksettings.databinding.FragmentControlPanelBinding

class ControlPanelFragment : Fragment() {

    private var _binding: FragmentControlPanelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControlPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Keep screen on while testing
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Brightness Sliders
        binding.brightnessSeekBar1.addOnChangeListener { slider, value, fromUser ->
            if (fromUser) {
                setBrightness(value)
                binding.brightnessSeekBar2.value = value
            }
        }

        binding.brightnessSeekBar2.addOnChangeListener { slider, value, fromUser ->
            if (fromUser) {
                setBrightness(value)
                binding.brightnessSeekBar1.value = value
            }
        }

        // Initialize brightness sliders
        val currentBrightness = getCurrentBrightness().toFloat() / 255f // Scale to 0-1
        binding.brightnessSeekBar1.value = currentBrightness
        binding.brightnessSeekBar2.value = currentBrightness

        // Volume Sliders (Placeholder - Implement your volume control logic)
        binding.volumeSeekBar1.addOnChangeListener { slider, value, fromUser ->
            if (fromUser) {
                setVolume(value) // Assuming you have a setVolume function
                binding.volumeSeekBar2.value = value
            }
        }

        binding.volumeSeekBar2.addOnChangeListener { slider, value, fromUser ->
            if (fromUser) {
                setVolume(value) // Assuming you have a setVolume function
                binding.volumeSeekBar1.value = value
            }
        }

        // Initialize volume sliders (Placeholder - Implement your volume control logic)
        val currentVolume = getCurrentVolume().toFloat() / 100f // Normalize to 0-1
        binding.volumeSeekBar1.value = currentVolume
        binding.volumeSeekBar2.value = currentVolume
    }

    private fun getCurrentBrightness(): Int {
        val contentResolver = requireContext().contentResolver
        return Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
    }

    private fun setBrightness(brightness: Float) {
        val layoutParams = requireActivity().window.attributes
        layoutParams.screenBrightness = brightness // brightness value between 0.0f and 1.0f
        requireActivity().window.attributes = layoutParams
    }


    private fun getCurrentVolume(): Int {
        // Implement your logic to get the current volume here
        return 0 // Replace with your actual volume retrieval
    }

    private fun setVolume(volume: Float) {
        // Implement your logic to set the volume here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
