package com.nbc.messenger.ui.add_contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.nbc.messenger.R
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentAddContactDialogBinding
import com.nbc.messenger.model.ProfileImage
import com.nbc.messenger.model.User

private const val EMAIL_ADDRESS_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
private const val PHONE_NUMBER_REGEX = "^\\+?\\d{1,3}[- ]?\\d{3,}(?:[- ]?\\d{3,})?\$"

private data class Contact(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val group: List<String> = emptyList(),
) {
    fun isValid(): Boolean {
        return name.isNotBlank() &&
                PHONE_NUMBER_REGEX.toRegex().containsMatchIn(phoneNumber) &&
                EMAIL_ADDRESS_REGEX.toRegex().containsMatchIn(email)
    }
}

class AddContactDialog(
    private val onSuccessListener: (User) -> Unit
) : DialogFragment() {

    private var _binding: FragmentAddContactDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = Contact()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddContactDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener { clickSaveButton() }

        watchEditText()
    }

    private fun clickSaveButton() {
        if (binding.btnSave.isEnabled) {
            onSuccessListener(contact.asExternalModel())
            dialog?.dismiss()
        }
    }

    private fun watchEditText() {
        with(binding) {
            listOf(
                etName,
                etPhoneNumber,
                etEmail,
                etGroup
            )
                .forEach { editText ->
                    editText.addTextChangedListener { editable ->
                        if (editable == null) return@addTextChangedListener

                        updateContactState(editText.id, editable.toString())
                        checkSuccess()
                    }
                }
        }
    }

    private fun checkSuccess() {
        if (contact.isValid()) {
            binding.btnSave.isEnabled = true
        }
    }

    private fun updateContactState(
        @IdRes viewId: Int,
        state: String,
    ) = with(binding) {
        when (viewId) {
            etName.id -> {
                contact = contact.copy(name = state)
            }

            etPhoneNumber.id -> {
                contact = contact.copy(phoneNumber = state)
            }

            etEmail.id -> {
                contact = contact.copy(email = state)
            }

            etGroup.id -> {
                contact = contact.copy(group = state.split(","))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setLayout()
        setBackground()
    }

    private fun setLayout() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setBackground() {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.add_contact_dialog_background)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "add_contact_dialog"
    }

}

private fun Contact.asExternalModel(): User {
    val lastUser = DataSource.getUsers().last()

    return User(
        id = "${lastUser.id.toInt() + 1}",
        name = name,
        nickname = name,
        phoneNumber = phoneNumber,
        email = email,
        group = group,
        profileImage = ProfileImage.ResourceImage(R.drawable.ic_profile_default),
        isLike = false,
        isChecked = false
    )
}