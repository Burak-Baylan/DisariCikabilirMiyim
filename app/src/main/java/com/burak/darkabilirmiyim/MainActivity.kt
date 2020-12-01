package com.burak.darkabilirmiyim

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    /**
     * Made by Burak BAYLAN in 11/22/2020 (22/11/2020) 12:02 (November/Monday)
     * Updated by Burak BAYLAN in 12/1/2020 (1/12/2020) 11:40 (December/Tuesday)
     */

    private var working = false

    private fun getEmo(emoCode : Int) : String{
        return String(Character.toChars(emoCode))
    }

    private var olumluMesaj = arrayOf("s","f","g")

    private var olumsuzMesaj = arrayOf("s","b","s")

    companion object{
        var olumluMesaj = arrayOf(
        "Yehuuu $bisikletEmoString ! Dışarısı seni bekler $partyEmoString",
        "Şu an seni hiçbir kuvvet evde tutamaz. Dışarısı seni bekliyor $punchEmoString",
        "$bisikletEmoString Sen dişarıda olmayacaksın da kim olacak $laughString",
        )

        var olumsuzMesaj = arrayOf(
        "$cryEmoString maalesef dışarı çıkamazsın $cryEmoString",
        "Maalesef şu an evde kalman gerekiyor $sadEmoString",
        "Bu yasaklardan etkilenen kişilerden biri de sensin $sadEmoString. Maalesef şu an dışarı çıkamazsın $cryEmoString",
        )

        var belgeylerBirlikte = arrayOf(
            "$belgeEmoString İşe gittiğin için dışarı çıkmakta özgürsün. FAKAT ELİNDE BELGE OLMASI GEREKİR. $belgeEmoString",
            "$belgeEmoString Çıkabilirsin fakat belgen olması gerekir. $gozKirpEmoString"
        )
    }

    lateinit var mAdView : AdView
    lateinit var mAdView2 : AdView
    private lateinit var mInterstitialAd: InterstitialAd
    @RequiresApi(Build.VERSION_CODES.O)
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
        bilgiVeUyari()
        firstTimeControl()

        mAdView2 = findViewById(R.id.adView2)
        val adRequest2 = AdRequest.Builder().build()
        mAdView2.loadAd(adRequest2)

        /******************************************************************************************/

        val thinkEmo = 0x1F914
        val thinkString = getEmo(thinkEmo)
        textView3.text = "$thinkString $thinkString $thinkString $thinkString $thinkString $thinkString $thinkString "
        textView.text = "$thinkString Şu an dışarı çıkabilir miyim? $thinkString"

        val unlemEmo = 0x2755
        val unlemEmoString = getEmo(unlemEmo)
        mesafeText.text = "$unlemEmoString Eğer dışarıya çıkabiliyorsan lütfen mesafeni koru $unlemEmoString"

        val k : Int = 0

        val maskEmo = 0x1F637
        val maskEmoString = getEmo(maskEmo)
        alertText.text = "Lütfen dışarıda ne olursa olsun maskelerimizi çıkartmayalım $maskEmoString ve ellerimizi sık sık yıkayalım $waterEmoString. Bunların hepsi hepimizin sağlığı için $punchEmoString. SAĞLIKLI GÜNLER! $pleaseEmoString"
        /******************************************************************************************/




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

                val tümKontroller = TumKontroller(infoText)
                val haftaIci = tümKontroller.haftaIcıMi()
                val saat = tümKontroller.saatAl()

                if (yasText.toInt() !in 16..90 && working){
                    alertCreator(
                        R.string.yas_yanlis_mi,
                        "Tekrar Dene",
                        "",
                        cancelable = true,
                        olumsuzsaUygKapat = false,
                        olumluysaSharedKaydet = false,
                        olumsuzButonOlsunMu = false
                    )
                }
                else if (yasText.toInt() > 100){
                    infoText.text = "Bu saatten sonra seni Covide bile yıkamaz $laughString. Tabi yine de dikkat etmek gerek. $pleaseEmoString"
                }
                else {
                    if (haftaIci) {// Hafta içi
                        tümKontroller.haftaIciKontrolEt(saat, yasText, working)
                    } else if (!haftaIci) {// Hafta sonu
                        tümKontroller.haftaSonuKontorlEt()
                    }
                }

                //Toast.makeText(this, "Gün: $a -- Zabarik: ${simpdate.format(date)}", Toast.LENGTH_LONG).show()
                //Toast.makeText(this, "Simpdate Long: ${simpdate.format(date)}", Toast.LENGTH_LONG).show()
                //Toast.makeText(this, "Real: $timeStr (After Change: $timeSubInt [amOrPm: $amOrPm]", Toast.LENGTH_LONG).show()

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

    private fun bilgiVeUyari(){

        uyariButton.setOnClickListener {
            alertCreator(
                R.string.sartlar,
                "Anladım, Kabul Ediyorum",
                "Kabul Etmiyorum",
                cancelable = true,
                olumsuzsaUygKapat = false,
                olumluysaSharedKaydet = true,
                olumsuzButonOlsunMu = false
            )
        }

        bilgiButton.setOnClickListener {
            alertCreator(
                R.string.info,
                "Anladım",
                "",
                cancelable = false,
                olumsuzsaUygKapat = false,
                olumluysaSharedKaydet = false,
                olumsuzButonOlsunMu = false
            )
        }

    }

    private fun firstTimeControl (){
        val preferences = getSharedPreferences("com.burak.darkabilirmiyim", Context.MODE_PRIVATE)
        val sartlar = preferences.getBoolean("sartKabulEdildi", false)
        if (!sartlar){
            alertCreator(
                R.string.sartlar,
                "Anladım, Kabul Ediyorum",
                "Kabul Etmiyorum",
                cancelable = false,
                olumsuzsaUygKapat = true,
                olumluysaSharedKaydet = true,
                olumsuzButonOlsunMu = true
            )
        }
    }

    private fun alertCreator(cumle: Int, olumluButonCumle: String, olumsuzButonCumle: String, cancelable: Boolean, olumsuzsaUygKapat: Boolean, olumluysaSharedKaydet: Boolean, olumsuzButonOlsunMu : Boolean){
        val preferences = getSharedPreferences("com.burak.darkabilirmiyim", Context.MODE_PRIVATE)
        val alert = AlertDialog.Builder(this)
        alert.setMessage(cumle)
        alert.setCancelable(cancelable)
        alert.setPositiveButton(olumluButonCumle) {dialog : DialogInterface, _ : Int ->
            if (olumluysaSharedKaydet){
                val sartlar = preferences.getBoolean("sartKabulEdildi", false)
                if (!sartlar) {
                    val editor = preferences.edit()
                    editor.putBoolean("sartKabulEdildi", true)
                    editor.apply()
                    editor.commit()
                }
                else{
                    dialog.dismiss()
                }
            }
            else if (!olumluysaSharedKaydet){
                dialog.dismiss()
            }
        }
        if (olumsuzButonOlsunMu) {
            alert.setNegativeButton(olumsuzButonCumle) { dialog: DialogInterface, _: Int ->
                if (olumsuzsaUygKapat) {
                    val editor = preferences.edit()
                    editor.putBoolean("sartKabulEdildi", false)
                    editor.apply()
                    editor.commit()
                    loadInterBilmemNeAd()
                    exitProcess(0)
                }
                else if (!olumsuzsaUygKapat) {
                    dialog.dismiss()
                }
            }
        }
        alert.show()
    }
}