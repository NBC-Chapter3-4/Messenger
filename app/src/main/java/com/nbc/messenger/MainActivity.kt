package com.nbc.messenger

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nbc.messenger.databinding.ActivityMainBinding
import com.nbc.messenger.model.User
import com.nbc.messenger.ui.main.ViewPagerFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(binding.main.id, ViewPagerFragment())
        fragmentTransaction.commit()


        Log.d("TEST", intent.getCustomParcelableExtra("item", User::class.java).toString())
    }
}


private fun <T> Intent.getCustomParcelableExtra(
    name: String,
    clazz: Class<T>,
) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(name, clazz)
    } else {
        getParcelableExtra(name)
    }
