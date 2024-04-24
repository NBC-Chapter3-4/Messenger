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
import com.nbc.messenger.model.ProfileImage
import com.nbc.messenger.model.User

private const val USER_MEMORY = "user"


class DetailFragment : Fragment(), View.OnClickListener {
    private var user: User? = null

    private var _binding: FragmentDatailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(USER_MEMORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDatailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            user?.let {
                icDetailNickName.tvDetailGetNick.text = it.nickname
                icDetailCall.tvDetailGetCall.text = it.phoneNumber
                icDetailEmail.tvDetailGetEmail.text = it.email
                tvDetailUserName.text = it.name
                tvDetailGetName.text = it.name
                icDetailGroup.tvDetailGetGroup.text = it.group.toString()

                when (it.profileImage) {
                    ProfileImage.DefaultImage -> Unit
                    is ProfileImage.ResourceImage -> civDetailProfile.setImageResource((it.profileImage).id)
                }
            }
        }

        binding.btnDetailCall.setOnClickListener(this)
        binding.btnDetailMsg.setOnClickListener(this)

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


    override fun onClick(view: View?) {
        when (view) {
            binding.btnDetailCall -> {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${user?.phoneNumber}")))
            }
            binding.btnDetailMsg -> {
                startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${user?.phoneNumber}")))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}