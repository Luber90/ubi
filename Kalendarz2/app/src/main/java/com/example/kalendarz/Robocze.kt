package com.example.kalendarz

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Robocze : AppCompatActivity() {
    fun ruchome(rok: Int): Array<Int>{
        val wiel = wielkanoc(rok)
        wiel.add(Calendar.DATE, 1)
        val wielpd = wiel.get(Calendar.DAY_OF_MONTH); val wielpm = wiel.get(Calendar.MONTH)
        wiel.add(Calendar.DATE, 59)
        val cialod = wiel.get(Calendar.DAY_OF_MONTH); val cialom = wiel.get(Calendar.MONTH)
        return arrayOf(wielpd, wielpm, cialod, cialom)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robocze)
        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        val dnid: NumberPicker = findViewById(R.id.dayd);val dnim: NumberPicker = findViewById(R.id.daym);val dnir: NumberPicker = findViewById(R.id.dayr)
        val dnird: NumberPicker = findViewById(R.id.dayrd);val dnirm: NumberPicker = findViewById(R.id.dayrm);val dnirr: NumberPicker = findViewById(R.id.dayrr)
        val butt: Button = findViewById(R.id.button)
        dnir.minValue = 1900
        dnirr.minValue = 1900
        dnir.maxValue = 2200
        dnirr.maxValue = 2200
        dnir.value = 2020
        dnirr.value = 2020
        dnim.minValue = 0
        dnim.maxValue = 11
        dnirm.minValue = 0
        dnirm.maxValue = 11
        dnim.wrapSelectorWheel = false
        dnim.displayedValues = months
        dnirm.displayedValues = months
        dnid.minValue = 1
        dnid.maxValue = 31
        dnird.minValue = 1
        dnird.maxValue = 31
        dnim.setOnValueChangedListener(){picker, oldVal, newVal ->
            val rok = dnir.value
            if (newVal == 1){
                if (rok % 4 == 0 && rok % 100 != 0 || rok % 400 == 0){
                    dnid.maxValue = 29
                }
                else{
                    dnid.maxValue = 28
                }
            }
            else if(newVal == 3 || newVal == 5 || newVal == 8 || newVal == 10){
                dnid.maxValue = 30
            }
            else{
                dnid.maxValue = 31
            }
            true
        }
        dnirm.setOnValueChangedListener(){picker, oldVal, newVal ->
            val rok = dnirr.value
            if (newVal == 1){
                if (rok % 4 == 0 && rok % 100 != 0 || rok % 400 == 0){
                    dnird.maxValue = 29
                }
                else{
                    dnird.maxValue = 28
                }
            }
            else if(newVal == 3 || newVal == 5 || newVal == 8 || newVal == 10){
                dnird.maxValue = 30
            }
            else{
                dnird.maxValue = 31
            }
            true
        }
        dnir.setOnValueChangedListener(){picker, oldVal, newVal ->
            val rok = newVal
            if ((rok % 4 == 0 && rok % 100 != 0 || rok % 400 == 0)&&dnim.value==1) {
                dnid.maxValue = 29
            }
            else if(dnim.value==1){
                dnid.maxValue = 28
            }
        }
        dnirr.setOnValueChangedListener(){picker, oldVal, newVal ->
            val rok = newVal
            if ((rok % 4 == 0 && rok % 100 != 0 || rok % 400 == 0)&&dnirm.value==1) {
                dnird.maxValue = 29
            }
            else if(dnim.value==1){
                dnird.maxValue = 28
            }
        }
        butt.setOnClickListener(){
            val start = GregorianCalendar(dnir.value, dnim.value, dnid.value)
            val end = GregorianCalendar(dnirr.value, dnirm.value, dnird.value)
            var date = start.time
            var dnik: Int = 0; var dnirob: Int = 0
            var rok = start.get(Calendar.YEAR)
            var rucho = ruchome(rok)
            while (start.before(end)) {
                if(rok!=start.get(Calendar.YEAR)){
                    rok = start.get(Calendar.YEAR)
                    rucho = ruchome(rok)
                }
                if(start.get(Calendar.DAY_OF_WEEK)==1||start.get(Calendar.DAY_OF_WEEK)==7||
                        start.get(Calendar.DAY_OF_YEAR)==1||start.get(Calendar.DAY_OF_YEAR)==3||
                        (start.get(Calendar.MONTH)==4&&(start.get(Calendar.DAY_OF_MONTH)==1||start.get(Calendar.DAY_OF_MONTH)==3))||
                        (start.get(Calendar.MONTH)==7&&start.get(Calendar.DAY_OF_MONTH)==15)||
                        (start.get(Calendar.MONTH)==10&&(start.get(Calendar.DAY_OF_MONTH)==1||start.get(Calendar.DAY_OF_MONTH)==11))||
                        (start.get(Calendar.MONTH)==11&&(start.get(Calendar.DAY_OF_MONTH)==25||start.get(Calendar.DAY_OF_MONTH)==26))||
                        (start.get(Calendar.MONTH)==rucho[1]&&start.get(Calendar.DAY_OF_MONTH)==rucho[0])||
                        (start.get(Calendar.MONTH)==rucho[3]&&start.get(Calendar.DAY_OF_MONTH)==rucho[2])){
                    dnik += 1
                }
                else{
                    dnik+=1
                    dnirob+=1
                }
                start.add(Calendar.DATE, 1)
                date = start.time
            }
            val kal: TextView = findViewById(R.id.iledni)
            val kalr: TextView = findViewById(R.id.ilednir)
            kal.text = dnik.toString()
            kalr.text = dnirob.toString()
            }
        }
    fun wielkanoc(rok: Int): android.icu.util.GregorianCalendar {
        val a = rok % 19
        val b = kotlin.math.floor((rok/100).toDouble()).toInt()
        val c = rok % 100
        val d = kotlin.math.floor((b/4).toDouble()).toInt()
        val e = b % 4
        val f = kotlin.math.floor(((b+8)/25).toDouble()).toInt()
        val g = kotlin.math.floor(((b-f+1)/3).toDouble()).toInt()
        val h = (19*a+b-d-g+15)%30
        val i = kotlin.math.floor((c/4).toDouble()).toInt()
        val k = c % 4
        val l = (32+2*e+2*i-h-k)%7
        val m = kotlin.math.floor(((a+11*h+22*l)/451).toDouble()).toInt()
        val p = (h+l-7*m+114)%31
        val day = p+1
        val month = kotlin.math.floor(((h+l-7*m+114)/31).toDouble()).toInt()
        return android.icu.util.GregorianCalendar(rok, month - 1, day)

    }

    }