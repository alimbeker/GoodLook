package com.example.goodlook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.databinding.FragmentFavorBinding
import com.example.goodlook.databinding.FragmentKorzinBinding
import com.example.goodlook.themeManager.ThemeManager
import com.example.goodlook.view.ItemAdapter
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory


class KorzinFrag : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ThemeManager.applyTheme(requireContext())
        val binding = FragmentKorzinBinding.inflate(inflater, container, false)


        binding.switchcompat.setOnClickListener {
            toggleTheme()
        }

        return binding.root

    }


    private fun toggleTheme() {
        val currentThemeMode = AppCompatDelegate.getDefaultNightMode()
        val newThemeMode = if (currentThemeMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }

        ThemeManager.saveThemeMode(requireContext(), newThemeMode)
        requireActivity().recreate()
    }


}