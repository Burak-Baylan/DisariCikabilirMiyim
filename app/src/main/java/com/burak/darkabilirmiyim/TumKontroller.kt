package com.burak.darkabilirmiyim

import android.graphics.Color
import android.widget.TextView
import com.burak.darkabilirmiyim.MainActivity.Companion.olumluMesaj
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class TumKontroller (val messageTextView : TextView){

    val date = Date()

    fun haftaIcıMi() : Boolean{
        // Hafta içiyse true hafta sonu ise false gönderir
        //Hafta sonu ise (Amerikada günler pazardan başlar yani cumartesi pazar için cumartesi=7 pazar=1'dir)
        val dayname = Calendar.getInstance()
        dayname.time = date
        val a = dayname.get(Calendar.DAY_OF_WEEK)
        return a in 2..6
    }

    fun saatAl() : Int{
        val simpdate = SimpleDateFormat("kk"/*:mm:ss"*/)
        return simpdate.format(date).toInt()
    }

    private fun olumluCreator(){
        val text = ThreadLocalRandom.current().nextLong(0, 2)
        messageTextView.setTextColor(Color.parseColor("#6EE161"))
        messageTextView.text = olumluMesaj[text.toInt()]
    }

    private fun olumsuzCreator(){
        val text = ThreadLocalRandom.current().nextLong(0, 2)
        messageTextView.setTextColor(Color.parseColor("#E16161"))
        messageTextView.text = MainActivity.olumsuzMesaj[text.toInt()]
    }

    private fun belgeyleOlumluCreater(){
        val text = ThreadLocalRandom.current().nextLong(0, 1)
        messageTextView.setTextColor(Color.parseColor("##61B8E1"))
        messageTextView.text = MainActivity.belgeylerBirlikte[text.toInt()]
    }

    fun haftaSonuKontorlEt (){
        olumsuzCreator()
    }

    fun haftaIciKontrolEt(saat : Int, yasText : String, working : Boolean){
        if (working){
            if (saat in 5..20) {
                when {
                    yasText.toInt() < 20 -> {
                        /** BELGELEYEREK ÇIKABİLİR **/
                        belgeyleOlumluCreater()
                    }
                    yasText.toInt() > 65 -> {
                        /** BELGELEYEREK ÇIKABİLİR **/
                        belgeyleOlumluCreater()
                    }
                    else -> {
                        /** ÇIKABİLİR **/
                        olumluCreator()
                    }
                }
            }
            else {
                /** ÇIKAMAZ **/
                olumsuzCreator()
            }
        }
        else if (!working){
            if (saat in 5..20){
                when {
                    yasText.toInt() < 20 -> {
                        if (saat in 13..16){
                            /** ÇIKABİLİR **/
                            olumluCreator()
                        }
                        else{
                            /** ÇIKAMAZ **/
                            olumsuzCreator()
                        }
                    }
                    yasText.toInt() > 65 -> {
                        if (saat in 10..13){
                            /** ÇIKABİLİR **/
                            olumluCreator()
                        }
                        else {
                            /** ÇIKAMAZ **/
                            olumsuzCreator()
                        }
                    }
                    else ->{
                        /** ÇIKABİLİR **/
                        if (saat in 5..20) {
                            olumluCreator()
                        }
                        else{
                            olumsuzCreator()
                        }
                    }
                }
            }
            else {
                /** ÇIKAMAZ **/
                olumsuzCreator()
            }
        }
    }

}