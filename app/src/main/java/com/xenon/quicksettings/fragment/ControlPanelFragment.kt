package com.xenon.quicksettings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        // Implement logic to control brightness and volume here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}