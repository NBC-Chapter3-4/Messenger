package com.nbc.messenger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.data.MemoryStorage
import com.nbc.messenger.databinding.FragmentDatailBinding
import com.nbc.messenger.model.My
import com.nbc.messenger.model.User

private const val USER_MEMORY = "user"


class DetailFragment : Fragment() {
    private var user: User? = null

    private val binding by lazy {
        FragmentDatailBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(USER_MEMORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            icDetailNickName.tvDetailGetNick.text = user?.nickname
            icDetailCall.tvDetailGetCall.text = user?.phoneNumber
            icDetailEmail.tvDetailGetEmail.text = user?.email
            tvDetailUserName.text = user?.name
            tvDetailGetName.text = user?.name
        }
//        binding.civDetailProfile.setImageDrawable() =user?.profileImage

        binding.btnDetailCall.setOnClickListener {
            val phoneNumber = user?.phoneNumber
            val call_intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}"))
            startActivity(call_intent)
        }
        binding.btnDetailMsg.setOnClickListener {
            val msgNumber = user?.phoneNumber
            val msg_intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${msgNumber}"))
            startActivity(msg_intent)
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