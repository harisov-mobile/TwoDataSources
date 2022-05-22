package ru.internetcloud.twodatasources.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.lang.IllegalStateException
import ru.internetcloud.twodatasources.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

   private var _binding: FragmentSettingsBinding? = null
   val binding: FragmentSettingsBinding
   get() = _binding ?: throw IllegalStateException("FragmentSettingsBinding is null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
}