package com.mikaocto.mikacore.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikaocto.mikacore.R
import com.mikaocto.mikacore.databinding.ActivityMainBinding
import com.mikaocto.mikacore.model.InputItem
import com.mikaocto.mikacore.ui.adapter.InputItemAdapter
import com.mikaocto.mikacore.ui.input.InputActivity
import com.mikaocto.mikacore.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), InputItemAdapter.ItemClicklistener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private val itemListAdapter = InputItemAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.fabAddItem.setOnClickListener {
            startActivity(Intent(this, InputActivity::class.java))
        }
        binding.tbHome.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    viewModel.deleteUser()
                    val intent = Intent(this, SplashActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
        with(binding.rvInputList) {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = itemListAdapter
        }
        viewModel.inputItemLiveData.observe(this) {
            when (it) {
                MainActivityViewModel.MainActivityViewState.OnEmptyList -> {
                    emptyState()
                }
                is MainActivityViewModel.MainActivityViewState.OnItemAvailable -> {
                    showData()
                    itemListAdapter.submitList(it.item)
                }
            }
        }
        viewModel.getAllData()
        viewModel.getUserData()
        viewModel.user.observe(this) {
            binding.tvGreeting.text = "Welcome, ${it.name}"
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllData()
    }

    private fun showData() {
        with(binding) {
            tvHistoryInput.isVisible = true
            rvInputList.isVisible = true
            emptyView.root.isGone = true
        }
    }

    private fun emptyState() {
        with(binding) {
            tvHistoryInput.isGone = true
            rvInputList.isGone = true
            emptyView.root.isGone = false
        }
    }

    override fun deleteItem(item: InputItem) {
        viewModel.deleteData(item)
        Toast.makeText(this, "Data Deleted", Toast.LENGTH_LONG).show()
    }
}