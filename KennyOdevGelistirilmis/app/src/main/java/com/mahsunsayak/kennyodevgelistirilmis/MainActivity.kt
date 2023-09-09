package com.mahsunsayak.kennyodevgelistirilmis

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mahsunsayak.kennyodevgelistirilmis.databinding.ActivityMainBinding

lateinit var sharedPref: SharedPreferences //Global tanımladım çünkü diğer aktivitelerden de ulaşabilmek için
var userScorePref: Int = 0 // Varsayılan olarak 0 ayarla.
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding //View Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Shared Preferences ile en yüksek skoru kaydettim
        sharedPref = this.getSharedPreferences("com.mahsunsayak.kennyodevgelistirilmis", Context.MODE_PRIVATE)

        userScorePref = sharedPref.getInt("score", 0)
        binding.highScore.text = "High score: $userScorePref"

    }

    //Diğer aktivitelere geçmek için intent kullanıldı.
    fun easyClick(view : View){
        val intent = Intent(this@MainActivity,EasyActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun mediumOnclick(view : View){
        val intent = Intent(this@MainActivity,MediumActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun hardOnclick(view : View){
        val intent = Intent(this@MainActivity,HardActivity::class.java)
        startActivity(intent)
        finish()
    }

}