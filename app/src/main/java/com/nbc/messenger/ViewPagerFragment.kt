package com.nbc.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentViewPagerBinding
import com.nbc.messenger.model.My

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = ViewPagerAdapter(this)

        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tapMain, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.people)
                }

                1 -> tab.setIcon(R.drawable.mypage)
            }
        }.attach()
    }
}
