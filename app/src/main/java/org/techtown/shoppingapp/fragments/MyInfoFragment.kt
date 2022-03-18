package org.techtown.shoppingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.techtown.shoppingapp.R
import org.techtown.shoppingapp.databinding.FragmentCartBinding
import org.techtown.shoppingapp.databinding.FragmentHomeBinding
import org.techtown.shoppingapp.databinding.FragmentMyInfoBinding

class MyInfoFragment : Fragment() {

    lateinit var binding : FragmentMyInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_info, container, false)
        return  binding.root

    }
}