package com.xenon.quicksettings.fragment

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaButtonReceiver
import com.xenon.quicksettings.databinding.FragmentMediaPanelBinding


class MediaPanelFragment : Fragment() {

    private var _binding: FragmentMediaPanelBinding? = null
    private val binding get() = _binding!!

    private var mediaController: MediaController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMediaPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize MediaSessionManager
        val mediaSessionManager =
            requireContext().getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager

        // Find the active media session
        val activeSessions = mediaSessionManager.getActiveSessions(ComponentName(requireContext(), MediaButtonReceiver::class.java))
        Log.d("MediaPanelFragment", "Active sessions: $activeSessions")

        if (activeSessions.isNotEmpty()) {
            // Get the first active session
            val mediaSession = activeSessions[0]
            Log.d("MediaPanelFragment", "Selected session: $mediaSession")

            // Create MediaController
            mediaController = MediaController(requireContext(), mediaSession.sessionToken)

            // Set click listeners for buttons
            binding.playPauseButton.setOnClickListener {
                // Handle play/pause action
                if (mediaController?.playbackState?.state == PlaybackState.STATE_PLAYING) {
                    mediaController?.transportControls?.pause()
                } else {
                    mediaController?.transportControls?.play()
                }
            }

            binding.skipButton.setOnClickListener {
                // Handle skip action
                mediaController?.transportControls?.skipToNext()
            }

            // Register a callback to receive metadata updates
            mediaController?.registerCallback(mediaControllerCallback)
        } else {
            Log.w("MediaPanelFragment", "No active media sessions found.")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaController?.unregisterCallback(mediaControllerCallback)
        mediaController = null
    }

    // Define a MediaController.Callback to receive updates
    private val mediaControllerCallback = object : MediaController.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadata?) {
            Log.d("MediaPanelFragment", "Metadata changed: $metadata")
            // Get album art bitmap from metadata
            // Update background of mediaPanel using the bitmap
            val albumArt = metadata?.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART)
            if (albumArt != null) {
                binding.mediaPanel.background = BitmapDrawable(resources, albumArt)
            } else {
                Log.w("MediaPanelFragment", "Album art is null.")
            }
        }

        override fun onPlaybackStateChanged(state: PlaybackState?) {
            Log.d("MediaPanelFragment", "Playback state changed: $state")
            // Update play/pause button state based on playback state
            if (state?.state == PlaybackState.STATE_PLAYING) {
                binding.playPauseButton.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                binding.playPauseButton.setImageResource(android.R.drawable.ic_media_play)
            }
        }
    }
}