package com.mehedi.mylocationreminder.locationreminders.reminderslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.mehedi.mylocationreminder.R
import com.mehedi.mylocationreminder.authentication.AuthenticationActivity
import com.mehedi.mylocationreminder.base.BaseFragment
import com.mehedi.mylocationreminder.databinding.FragmentRemindersBinding
import com.mehedi.mylocationreminder.utils.setDisplayHomeAsUpEnabled
import com.mehedi.mylocationreminder.utils.setTitle
import com.mehedi.mylocationreminder.utils.setup
import com.udacity.project4.base.NavigationCommand
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderListFragment : BaseFragment() {
    
    override val _viewModel: RemindersListViewModel by viewModel()
    private lateinit var binding: FragmentRemindersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reminders, container, false
        )
        binding.viewModel = _viewModel
        
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))
        
        binding.refreshLayout.setOnRefreshListener { _viewModel.loadReminders() }
        
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()
        binding.addReminderFAB.setOnClickListener {
            navigateToAddReminder()
        }
    }
    
    override fun onResume() {
        super.onResume()
        //load the reminders list on the ui
        _viewModel.loadReminders()
    }
    
    private fun navigateToAddReminder() {
        //use the navigationCommand live data to navigate between the fragments
        _viewModel.navigationCommand.postValue(
            NavigationCommand.To(
                ReminderListFragmentDirections.toSaveReminder()
            )
        )
    }
    
    private fun setupRecyclerView() {
        val adapter = RemindersListAdapter {
        }

//        setup the recycler view using the extension function
        binding.reminderssRecyclerView.setup(adapter)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
                    startActivity(Intent(activity, AuthenticationActivity::class.java))
                    activity?.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
        
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
//        display logout as menu item
        inflater.inflate(R.menu.main_menu, menu)
    }
    
}
