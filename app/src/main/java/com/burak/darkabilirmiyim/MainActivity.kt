package com.burak.darkabilirmiyim

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class MainActivity : AppCompatActivity() {



    private var working = false

    private fun getEmo(emoCode : Int) : String{
        return String(Character.toChars(emoCode))
    }

    private var olumluMesaj = arrayOf("s","f","g")

    private var olumsuzMesaj = arrayOf("s","b","s")

    lateinit var mAdView : AdView
    lateinit var mAdView2 : AdView
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-8014812102703860/2840747189"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mAdView2 = findViewById(R.id.adView2)
        val adRequest2 = AdRequest.Builder().build()
        mAdView2.loadAd(adRequest2)

        /******************************************************************************************/



        val partyEmo = 0x1F973
        val partyEmoString = getEmo(partyEmo)

        val punchEmo = 0x1F44A
        val punchEmoString = getEmo(punchEmo)

        val waterEmo = 0x1F4A7
        val waterEmoString = getEmo(waterEmo)

        val pleaseEmo = 0x1FA7A//0x1F64F
        val pleaseEmoString = getEmo(pleaseEmo)

        val cryEmo = 0x1F622
        val cryEmoString = getEmo(cryEmo)

        val laughEmo = 0x1F92D
        val laughString = getEmo(laughEmo)

        val thinkEmo = 0x1F914
        val thinkString = getEmo(thinkEmo)
        textView3.text = "$thinkString $thinkString $thinkString $thinkString $thinkString $thinkString $thinkString "
        textView.text = "$thinkString Şu an dışarı çıkabilir miyim? $thinkString"

        val workerGirEmo = 0x1F527
        val workerGirlString = getEmo(workerGirEmo)

        val sadEmo = 0x1F625
        val sadEmoString = getEmo(sadEmo)

        val unlemEmo = 0x2755
        val unlemEmoString = getEmo(unlemEmo)
        mesafeText.text = "$unlemEmoString Eğer dışarıya çıkabiliyorsan lütfen mesafeni koru $unlemEmoString"

        val maskEmo = 0x1F637
        val maskEmoString = getEmo(maskEmo)
        alertText.text = "Lütfen dışarıda ne olursa olsun maskelerimizi çıkartmayalım $maskEmoString ve ellerimizi sık sık yıkayalım $waterEmoString. Bunların hepsi hepimizin sağlığı için $punchEmoString. SAĞLIKLI GÜNLER! $pleaseEmoString"
        /******************************************************************************************/

        olumluMesaj = arrayOf(
            "Yehuuu! Dışarısı seni bekler $partyEmoString",
            "Şu an seni hiçbir kuvvet evde tutamaz. Dışarısı seni bekliyor $punchEmoString",
            "Sen dişarıda olmayacaksın da kim olacak $laughString"
        )

        olumsuzMesaj = arrayOf(
            "Ya $cryEmoString maalesef dışarı çıkamazsın $cryEmoString",
            "Maalesef şu an evde kalman gerekiyor $sadEmoString",
            "Bu yasaklardan etkilenen kişilerden biri de sensin $sadEmoString. Maalesef şu an dışarı çıkamazsın $cryEmoString"
        )

        firstLayout.visibility = View.VISIBLE
        secondLayout.visibility = View.INVISIBLE

        working = false

        noButton.setBackgroundResource(R.drawable.yes_no_button_green)

        yesButton.setOnClickListener {

            if (!working){
                working = true
                noButton.setBackgroundResource(R.drawable.yes_no_button)
                yesButton.setBackgroundResource(R.drawable.yes_no_button_green)
            }
        }

        noButton.setOnClickListener {
            if (working){
                working = false
                noButton.setBackgroundResource(R.drawable.yes_no_button_green)
                yesButton.setBackgroundResource(R.drawable.yes_no_button)
            }
        }

        checkButton.setOnClickListener {

            if (ageEditText.text.isNotBlank()){
                val yasText = ageEditText.text.toString()

                firstLayout.visibility = View.INVISIBLE
                secondLayout.visibility = View.VISIBLE

                val calendar = Calendar.getInstance()
                val currentDate = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT)
                    .format(calendar.time)

                val time = DateFormat.getTimeFormat(this)
                val timeStr = time.format(Date()); DateFormat.getTimeFormat(this)

                /*Toast.makeText(
                    this,
                    "${calendar.get(Calendar.DAY_OF_WEEK)} $timeStr $partyEmoString",
                    Toast.LENGTH_LONG
                ).show()*/


                val timeSubStr = timeStr.substring(0, 2)
                val timeSubInt = timeSubStr.toInt()

                if (currentDate == "6" || currentDate == "7") // Hafta sonu ise
                {
                    println("Hafta Soun")

                    Toast.makeText(this, "$timeSubStr", Toast.LENGTH_LONG).show()

                    if (timeSubInt in 10..20) // Saat 10 ve 20 arası ise
                    {
                        if (yasText.toInt() < 20 || yasText.toInt() > 65){
                            /** OLUMSUZ **/
                            olumsuzCreator()
                        }
                        else {
                            /** OLUMLU **/
                            olumluCreator()
                        }
                    } else {
                        /** OLUMSUZ **/
                        olumsuzCreator()
                    }
                } else // Hafta içi ise
                {
                    println("Hafta İçi")

                    if (yasText.toInt() > 120) {
                        /** Çok Yaşlı **/
                        infoText.text = "Bu saatten sonra seni Covid bile yıkamaz $laughString $laughString\nTabii yine de dikkat etmen gerekiyor$unlemEmoString"
                    } else {
                        if (yasText.toInt() < 20) // Eğer yaş 20 altı ise
                        {
                            if (timeSubInt in 13..16) // 13-16 arası ise
                            {
                                /** ÇIKABİLİRSİN **/
                                olumluCreator()
                            } else // 13-16 arası değilse
                            {
                                /** ÇIKAMAZSIN **/
                                olumsuzCreator()
                            }
                        } else if (yasText.toInt() > 65) // Eğer yaş 65 üstü ise
                        {
                            if (timeSubInt in 10..13) {
                                /** ÇIKABİLİRSİN **/
                                olumluCreator()
                            } else {
                                /** ÇIKAMAZSIN **/
                                olumsuzCreator()
                            }
                        }
                        else{
                            olumluCreator()
                        }
                    }
                }

                if (working){
                    if (yasText.toInt() < 15){
                        val alert = AlertDialog.Builder(this)
                        alert.setCancelable(false)
                        alert.setTitle("Hata!")
                        alert.setMessage("Galiba yaşını yazarken bir yanlışlık yaptın. Tekrar denemek ister misin?")
                        alert.setPositiveButton("Tekrar Dene") {dialog : DialogInterface, _ : Int ->
                            dialog.cancel()
                        }
                        alert.show()
                        firstLayout.visibility = View.VISIBLE
                        secondLayout.visibility = View.INVISIBLE
                    }
                    else {
                        infoText.text = "İşi olan evde oturmaz. Haydi sana kolay gelsin. $workerGirlString"
                    }
                }
            }
            else{
                Toast.makeText(this, "Yaşını söylemezsen olur mu sence?", Toast.LENGTH_SHORT).show()
            }
        }

        tekrarlaButton.setOnClickListener {
            firstLayout.visibility = View.VISIBLE
            secondLayout.visibility = View.INVISIBLE
            loadInterBilmemNeAd()
        }
    }

    private fun loadInterBilmemNeAd(){
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
            mInterstitialAd.adListener = object : AdListener() {
                override fun onAdClosed() {
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
            mInterstitialAd.adListener = object : AdListener() {
                override fun onAdClosed() {
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }
        }
    }

    override fun onBackPressed() {

        if (secondLayout.visibility == View.VISIBLE){
            secondLayout.visibility = View.INVISIBLE
            firstLayout.visibility = View.VISIBLE
            loadInterBilmemNeAd()
        }
        else{
            super.onBackPressed()
        }
    }

    private fun olumluCreator(){
        val text = ThreadLocalRandom.current().nextLong(0, 3)
        infoText.text = olumluMesaj[text.toInt()]
    }

    private fun olumsuzCreator(){
        val text = ThreadLocalRandom.current().nextLong(0, 3)
        infoText.text = olumsuzMesaj[text.toInt()]
    }
}