package com.example.contacts.screens.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.contacts.R
import com.example.contacts.databinding.DialogAddContactBinding
import com.example.contacts.model.Contact
import com.example.contacts.util.showToast

class AddContactDialogFragment : DialogFragment() {

    interface OnContactAddedListener {
        fun onContactAdded(contact: Contact)
    }

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    private var listener: OnContactAddedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayoutParams()
        inflateText()
        initClickListeners()
    }

    private fun setLayoutParams() {
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun inflateText() {
        val hint = getString(R.string.full_name_hint, getString(R.string.name), getString(R.string.surname))
        binding.editFullname.hint = hint
    }

    private fun initClickListeners() {
        initSaveButtonListener()
        initCancelButtonListener()
    }

    private fun initSaveButtonListener() {
        binding.btnSave.setOnClickListener {
            val contact = createContactFromInput()
            if (isInputValid(contact)) {
                listener?.onContactAdded(contact)
                dismiss()
            } else {
                showValidationError()
            }
        }
    }

    private fun initCancelButtonListener() {
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun createContactFromInput(): Contact {
        val fullName = binding.editFullname.text.toString().trim()
        val names = fullName.split(" ")
        val firstName = names.firstOrNull() ?: ""
        val lastName = names.getOrNull(1) ?: ""

        return Contact(
            firstName = firstName,
            lastName = lastName,
            email = binding.editEmail.text.toString().trim(),
            mobile = binding.editPhone.text.toString().trim(),
            career = binding.editCareer.text.toString().trim(),
            profession = binding.editCareer.text.toString().trim(),
            imageId = R.drawable.ic_person
        )
    }

    private fun isInputValid(contact: Contact): Boolean {
        return contact.firstName.isNotEmpty() && 
               contact.email.isNotEmpty() && 
               contact.mobile.isNotEmpty()
    }

    private fun showValidationError() {
        requireContext().showToast(getString(R.string.required_fields))
    }

    fun setOnContactListener(listener: OnContactAddedListener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
