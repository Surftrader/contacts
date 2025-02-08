package com.example.contacts

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.data.DataSource
import com.example.contacts.data.InternDataSource
import com.example.contacts.databinding.ActivityMyContactsBinding
import com.example.contacts.model.Contact
import com.google.android.material.snackbar.Snackbar

const val ADD_CONTACT_DIALOG = "AddContactDialog"

class MyContactsActivity : AppCompatActivity(), AddContactDialogFragment.OnContactAddedListener {

    private val binding: ActivityMyContactsBinding by lazy {
        ActivityMyContactsBinding.inflate(layoutInflater)
    }

    private lateinit var contactList: MutableList<Contact>
    private lateinit var dataSource: DataSource<Contact>
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contacts_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataSource = InternDataSource()
        contactList = dataSource.getContacts().toMutableList()

        adapter = ContactAdapter { index -> deleteItem(index) }
        adapter.submitList(contactList)

        init()
    }

    private fun deleteItem(index: Int) {
        val removedContact = contactList[index]

        contactList.removeAt(index)
        adapter.notifyItemRemoved(index)

        Snackbar.make(
            binding.root,
            getString(R.string.contact_removed),
            Snackbar.LENGTH_LONG
        ).apply {
            setAction(getString(R.string.cancel)) {
                if (index <= contactList.size) {
                    contactList.add(index, removedContact)
                    adapter.notifyItemInserted(index)
                } else {
                    contactList.add(removedContact)
                    adapter.notifyItemInserted(contactList.size - 1)
                }
            }
            duration = 5000
            show()
        }
    }

    private fun init() = with(binding) {
        rcvContacts.layoutManager = LinearLayoutManager(this@MyContactsActivity)
        rcvContacts.adapter = adapter

        arrowBack.setOnClickListener { goBack() }
        addContacts.setOnClickListener { showAddContactDialog() } // Call DialogFragment
    }

    private fun showAddContactDialog() {
        AddContactDialogFragment().show(supportFragmentManager, ADD_CONTACT_DIALOG)
    }

    override fun onContactAdded(contact: Contact) {
        contactList = contactList.toMutableList().apply { add(contact) }
        adapter.submitList(contactList.toList()) {
            binding.rcvContacts.scrollToPosition(contactList.size - 1)
        }
    }

    private fun goBack() {
        startActivity(Intent(this, MyProfileActivity::class.java))
        finish()
    }

    @Suppress("DEPRECATION")
    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN, R.anim.slide_in_left, R.anim.slide_out_right
            )
        } else {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}
