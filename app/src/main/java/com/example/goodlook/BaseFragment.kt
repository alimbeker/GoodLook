package com.example.goodlook

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.goodlook.database.CardDatabase
import com.example.goodlook.viewmodel.FavorFragmentViewModel
import com.example.goodlook.viewmodel.VmFactory

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


inline fun <reified T : ViewModel> Fragment.createViewModel(application: Application, dataSource: Any): T {
    val vmFactory = VmFactory(dataSource, application, T::class.java)
    return ViewModelProvider(this, vmFactory).get(T::class.java)
}
abstract class BaseFragment<VB: ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {
    private var _binding : VB? = null
    private var _viewModel : FavorFragmentViewModel? = null

    val binding get() = _binding ?: throw RuntimeException()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = inflate(inflater, container, false)


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}