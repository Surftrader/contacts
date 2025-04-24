package com.example.contacts.screens.contacts

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.ActivityMyContactsBinding
import com.example.contacts.model.Contact
import com.example.contacts.screens.profile.MyProfileActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val ADD_CONTACT_DIALOG = "AddContactDialog"

@AndroidEntryPoint
class MyContactsActivity : AppCompatActivity(), AddContactDialogFragment.OnContactAddedListener {

    private val binding: ActivityMyContactsBinding by lazy {
        ActivityMyContactsBinding.inflate(layoutInflater)
    }

    private val contactsAdapter: ContactAdapter by lazy {
        ContactAdapter { contact, position ->
            deleteContactWithUndo(contact, position) }
    }

    private val viewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contacts_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = initRecycler()

        subscribeToContacts()

        initClickListeners()

        setupSwipeToDelete(recyclerView)
    }

    private fun subscribeToContacts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contacts.collect { contacts ->
                    contactsAdapter.submitList(contacts)
                }
            }
        }
    }

    private fun initRecycler(): RecyclerView {
        val recyclerView = binding.rcvContacts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactsAdapter
        return recyclerView
    }

    private fun initClickListeners() {
        binding.arrowBack.setOnClickListener { goBack() }
        binding.addContacts.setOnClickListener { showAddContactDialog() }
    }

    private fun showAddContactDialog() {
        val dialog = AddContactDialogFragment()
        dialog.setOnContactListener(this)
        dialog.show(supportFragmentManager, ADD_CONTACT_DIALOG)
    }

    private fun deleteContactWithUndo(contact: Contact, position: Int) {
        viewModel.removeContact(contact)
        var isUndoClicked = false

        Snackbar.make(
            binding.root,
            getString(R.string.contact_removed),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.cancel)) {
                if (!isUndoClicked) {
                    isUndoClicked = true
                    viewModel.addContact(contact, position)
                }
            }
            .setDuration(5000)
            .show()
    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView) {
        val swipeCallback = SwipeToDeleteCallback(this) { position ->
            val contact = contactsAdapter.currentList[position]
            deleteContactWithUndo(contact, position)
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onContactAdded(contact: Contact) {
        viewModel.addContact(contact, -1)
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
