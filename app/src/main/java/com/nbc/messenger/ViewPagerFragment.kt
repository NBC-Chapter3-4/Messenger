package com.nbc.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentMainBinding
import com.nbc.messenger.databinding.FragmentViewPagerBinding
import com.nbc.messenger.model.My

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPagerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val myData = DataSource.getMyData()

        val viewPagerAdapter = ViewPagerAdapter(this, myData)
        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tapMain, binding.viewPager2) { tab, position ->
            when(position) {
                0 -> tab.setIcon(R.drawable.heart)
                1 -> tab.setIcon(R.drawable.heart2)
            }
        }.attach()

        val viewPager2 = binding.viewPager2
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewPagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(myData: My) : TestFragment {
            val fragment = TestFragment()
            val args = Bundle()
            args.putParcelable("myDataKey", myData)
            fragment.arguments = args
            return fragment
        }
    }
}