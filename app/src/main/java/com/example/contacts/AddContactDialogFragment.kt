package com.example.contacts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.contacts.databinding.DialogAddContactBinding
import com.example.contacts.model.Contact

class AddContactDialogFragment : DialogFragment() {

    interface OnContactAddedListener {
        fun onContactAdded(contact: Contact)
    }

    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!

    private var listener: OnContactAddedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnContactAddedListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnContactAddedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val hint = getString(R.string.full_name_hint, getString(R.string.name), getString(R.string.surname))
        binding.editFullname.hint = hint

        binding.btnSave.setOnClickListener {
            val fullName = binding.editFullname.text.toString().trim()
            val names = fullName.split(" ")
            val firstName = names.firstOrNull() ?: ""
            val lastName = names.getOrNull(1) ?: ""

            val email = binding.editEmail.text.toString().trim()
            val phone = binding.editPhone.text.toString().trim()
            val career = binding.editCareer.text.toString().trim()

            if (fullName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                val newContact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    mobile = phone,
                    career = career,
                    profession = career,
                    imageId = R.drawable.ic_error
                )
                listener?.onContactAdded(newContact)
                dismiss()
            } else {
                Toast.makeText(requireContext(), R.string.required_fields, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
