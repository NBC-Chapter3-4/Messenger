package com.nbc.messenger

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nbc.messenger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        setFragment(MyPageFragment())
    }
    private fun setFragment(frag : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.framelayout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }
}