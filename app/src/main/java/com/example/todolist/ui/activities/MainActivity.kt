package com.example.todolist.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels

import androidx.lifecycle.lifecycleScope


import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import com.example.todolist.R
import com.example.todolist.App

import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.entities.Training
import com.example.todolist.ui.alertDialogs.DialogListener

import com.example.todolist.viewmodels.MainActivityViewModel
import com.example.todolist.viewmodels.MainActivityViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID


class MainActivity : AppCompatActivity(), DialogListener {

    val viewModel: MainActivityViewModel by viewModels { MainActivityViewModelFactory((application as App).projectsRepository) }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val navView: BottomNavigationView = binding.bottomNavigationView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)


  /*      binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    val frag = ProjectsFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.general_layout, frag)
                    transaction.commit()
                    Toast.makeText(this, "menu_profile", Toast.LENGTH_SHORT).show()
                }

                R.id.menu_new -> {
                    val newProjectDialogFragment = NewProjectDialogFragment()
                    val manager = supportFragmentManager
                    val transaction: FragmentTransaction = manager.beginTransaction()
                    newProjectDialogFragment.show(transaction, "dialog")
                }

                R.id.menu_profile -> {
                    val frag = ProfileFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.general_layout, frag)
                    transaction.commit()
                    Toast.makeText(this, "menu_profile", Toast.LENGTH_SHORT).show()
                }

            }
            true
        }*/
    }

    override fun onFinishDialog(data: String) {
        // Toast.makeText(this, "Hey super!", Toast.LENGTH_SHORT).show()
        val uuid = UUID.randomUUID()
        lifecycleScope.launch {
            viewModel.addNewProject(
                Training(
                    id = uuid.toString(),
                    name = data,
                    date = Calendar.getInstance().time,
                    isCompleted = false,
                )
            )
        }
    }
}
