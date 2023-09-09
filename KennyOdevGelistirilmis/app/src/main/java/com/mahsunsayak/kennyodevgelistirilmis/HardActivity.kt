package com.mahsunsayak.kennyodevgelistirilmis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.mahsunsayak.kennyodevgelistirilmis.databinding.ActivityHardBinding
import com.mahsunsayak.kennyodevgelistirilmis.databinding.ActivityMediumBinding
import kotlin.random.Random

class HardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHardBinding //View Binding
    private var runnable : Runnable = Runnable{} //Her saniyede Kenny'i hareket ettirebilmek için Runnable ve Handler kullanıldı.
    private var handler : Handler = Handler(Looper.getMainLooper())
    private var score = 0 //Kullanıcı Kenny'e her dokunuşunda skoru artırmak için değişken tanımlandı.
    private var randomIndex = 0 //Kenny'i hareket ettirebilmek için konumunun rastgele ayarlanması için değişken tanımlandı.
    private val img: List<ImageView> by lazy {
        listOf(
            binding.imageView1, binding.imageView2, binding.imageView3, binding.imageView4,
            binding.imageView5, binding.imageView6, binding.imageView7, binding.imageView8,
            binding.imageView9, binding.imageView10, binding.imageView11, binding.imageView12,
            binding.imageView13, binding.imageView14, binding.imageView15, binding.imageView16
        )
    } //Tüm Kenny ler bir liste içinde tanımlandı.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Her defasında ekranda sadece bir Kenny görünsün diye ilk başta hepsi gizlendi.
        binding.imageView1.isVisible = false
        binding.imageView2.isVisible = false
        binding.imageView3.isVisible = false
        binding.imageView4.isVisible = false
        binding.imageView5.isVisible = false
        binding.imageView6.isVisible = false
        binding.imageView7.isVisible = false
        binding.imageView8.isVisible = false
        binding.imageView9.isVisible = false
        binding.imageView10.isVisible = false
        binding.imageView11.isVisible = false
        binding.imageView12.isVisible = false
        binding.imageView13.isVisible = false
        binding.imageView14.isVisible = false
        binding.imageView15.isVisible = false
        binding.imageView16.isVisible = false

        runnable()

    }

    private fun runnable(){

        object : CountDownTimer(20000,1000){
            override fun onTick(millisUntilFinished: Long) {

                // CountDownTimer kullanılarak oyunun süresi ayarlandı.
                binding.timeText.text = "Time: ${millisUntilFinished / 1000}" //Ekranda her saniyede "Time: " azalıyor.

            }
            override fun onFinish() {
                handler.removeCallbacks(runnable) //Oyun süresi bittiğinde Kenny ler de hareket etmesin runnable durduruldu.
                binding.timeText.text = "Time: 0"

                // Oyun bittiği zaman ekranda uyarı bildirimi görünecek.Kullanıcı yaptığı seçime göre oyunu bitirecek ya da devam edecek.
                val alertDialog = AlertDialog.Builder(this@HardActivity)
                alertDialog.setTitle("Game Over")
                alertDialog.setMessage("Restart The Game?")
                alertDialog.setPositiveButton("Yes"){ dialog,which ->
                    //recreate()

                    val intent = Intent(this@HardActivity,HardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // main aktiviteye dönmeden önceki tüm activiteleri yok eder.
                    startActivity(intent)

                }

                alertDialog.setNegativeButton("No"){ dialog,which ->
                    val intent = Intent(this@HardActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // main aktiviteye dönmeden önceki tüm activiteleri yok eder.
                    startActivity(intent)
                }

                alertDialog.show()
            }
        }.start()

        runnable = object : Runnable{
            override fun run() {

                //Her saniyede tüm resimler gizlendi.Sadece rastgele seçilen resim görünür yapılacak.
                for (image in img){
                    image.visibility = View.INVISIBLE
                }

                //Rastgele resim dizini seçildi.
                randomIndex = Random.nextInt(0, 16)
                img[randomIndex].isVisible = true

                handler.postDelayed(runnable, 330)
            }

        }
        handler.post(runnable)

    }

    fun kennyClick(view : View){

        // Kullanıcı Kenny'e dokunğunda hem skor artıyor hem de dokunduğu resim görünmez oluyor ve yeni resim farklı konumda görünüyor.
        if (img[randomIndex].isVisible) {
            score += 1
            binding.scoreText.text = "Score: $score"
            img[randomIndex].isVisible = false // Tıklanan resmi gizle
        }

        //En son elde edilen skor ile en yüksek skoru karşılaştırıp en yükseği kaydedildi.
        if (score > userScorePref) {
            userScorePref = score
            sharedPref.edit().putInt("score", userScorePref).apply()
        }
    }
}