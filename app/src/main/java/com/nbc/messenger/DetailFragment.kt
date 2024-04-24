package com.nbc.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nbc.messenger.databinding.FragmentDetailBinding
import com.nbc.messenger.model.User

private const val USER_MEMORY = "user"



/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private var user: User? = null

//    private val binding by lazy {
//        FragmentDetailBinding.inflate(layoutInflater) }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(USER_MEMORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            user?.let {
                tvDetailGetNick.text = it.nickname
                tvDetailGetCall.text = it.phoneNumber
                tvDetailGetEmail.text = it.email
                tvDetailUserName.text = it.name
                tvDetailGetName.text = it.name
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_MEMORY, user)

                }
            }
    }
}
