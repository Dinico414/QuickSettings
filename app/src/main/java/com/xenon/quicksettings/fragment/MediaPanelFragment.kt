package com.xenon.quicksettings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xenon.quicksettings.databinding.FragmentMediaPanelBinding

class MediaPanelFragment : Fragment() {

    private var _binding: FragmentMediaPanelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Implement media playback control logic here

        // Example: Set click listeners for buttons
        binding.playPauseButton.setOnClickListener {
            // Handle play/pause action
        }

        binding.skipButton.setOnClickListener {
            // Handle skip action
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}