package com.example.todolist.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todolist.App
import com.example.todolist.R
import com.example.todolist.TOKEN
import com.example.todolist.databinding.ActivityLoginBinding
import com.example.todolist.entities.User
import com.example.todolist.viewmodels.LoginActivityViewModelFactory
import com.example.todolist.viewmodels.LoginViewModel
import com.example.todolist.viewmodels.MainActivityViewModel
import com.example.todolist.viewmodels.MainActivityViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels { LoginActivityViewModelFactory((application as App).projectsRepository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)


            if(binding.username.text.isNotEmpty() && binding.password.text.isNotEmpty()){
                //получить пользователя
                lifecycleScope.launch {
                    viewModel.login(binding.username.text.toString(),binding.password.text.toString())
                }

                startActivity(intent)
            }
        }
        binding.register.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //ввести и послать данные на сервер

            if(binding.edPass.text.isNotEmpty() && binding.edName.text.isNotEmpty()){
                lifecycleScope.launch {
                    viewModel.register(binding.edPass.text.toString(),binding.edName.text.toString())
                }

                startActivity(intent)
            }
        }

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Действие, когда переключатель включен
                TOKEN = "TRAINER_TOKEN"
            } else {
                // Действие, когда переключатель выключен
                TOKEN = "USER_TOKEN"

            }
        }

    }



}