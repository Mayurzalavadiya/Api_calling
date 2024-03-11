package com.restapi.ui.activity.task2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.restapi.R
import com.restapi.databinding.ActivityUserListBinding
import com.restapi.ui.activity.task2.fragment.UsersListFragment

class UserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UsersListFragment()).commit()
    }
}