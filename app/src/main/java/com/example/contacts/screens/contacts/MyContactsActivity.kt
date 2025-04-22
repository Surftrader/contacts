package com.example.contacts.screens.contacts

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.ActivityMyContactsBinding
import com.example.contacts.model.Contact
import com.example.contacts.screens.profile.MyProfileActivity
import com.google.android.material.snackbar.Snackbar

const val ADD_CONTACT_DIALOG = "AddContactDialog"

class MyContactsActivity : AppCompatActivity(), AddContactDialogFragment.OnContactAddedListener {

    private val binding: ActivityMyContactsBinding by lazy {
        ActivityMyContactsBinding.inflate(layoutInflater)
    }

    private val contactsAdapter: ContactAdapter by lazy {
        ContactAdapter { contact, position ->
            deleteContactWithUndo(contact, position) }
    }

    private val viewModel: ContactViewModel by lazy {
        ViewModelProvider(this)[ContactViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contacts_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val recyclerView = binding.rcvContacts
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactsAdapter

        viewModel.contacts.observe(this) { contacts ->
            contactsAdapter.submitList(contacts)
        }

        initClickListeners()

        setupSwipeToDelete(recyclerView)
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
        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val contact = contactsAdapter.currentList[position]
                    deleteContactWithUndo(contact, position)
                }

                override fun onChildDraw(
                    c: Canvas, recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()
                    val icon =
                        ContextCompat.getDrawable(this@MyContactsActivity, R.drawable.arrow_back)!!

                    // Draw red background
                    paint.color = Color.RED
                    c.drawRect(
                        itemView.right + dX, itemView.top.toFloat(),
                        itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                    )

                    // Draw icon
                    val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                    val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    val iconBottom = iconTop + icon.intrinsicHeight

                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(c)

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            })

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
