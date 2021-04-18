package com.example.kalendarz

import android.content.Intent
import android.icu.util.GregorianCalendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {

    fun wielkanoc(rok: Int): GregorianCalendar {
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
        return GregorianCalendar(rok, month-1, day)

    }

    fun adwent(rok: Int): GregorianCalendar{
        val cal = GregorianCalendar(rok, 11, 25)
        cal.add(Calendar.DATE, -21+(1-cal.get(Calendar.DAY_OF_WEEK)))
        return cal
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val year: NumberPicker = findViewById(R.id.year)
        year.minValue = 1900
        year.maxValue = 2200
        year.value = 2020
        year.wrapSelectorWheel = true

        val cal = wielkanoc(2020)
        val wiel: TextView = findViewById(R.id.wielkanoc_date)
        val pop: TextView = findViewById(R.id.popielec_date)
        val cialo: TextView = findViewById(R.id.boze_cialo_date)
        val adwent: TextView = findViewById(R.id.adwent_date)

        wiel.text = getString(R.string.data, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
        cal.add(Calendar.DATE, 60)
        cialo.text = getString(R.string.data, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
        cal.add(Calendar.DATE, -106)
        pop.text = getString(R.string.data, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
        val tmp = adwent(2020)
        adwent.text = getString(R.string.data, tmp.get(Calendar.DAY_OF_MONTH), tmp.get(Calendar.MONTH)+1, tmp.get(Calendar.YEAR))

        year.setOnValueChangedListener(){_, _, newVal ->
            val cal = wielkanoc(newVal)
            val wiel: TextView = findViewById(R.id.wielkanoc_date)
            val pop: TextView = findViewById(R.id.popielec_date)
            val cialo: TextView = findViewById(R.id.boze_cialo_date)
            val adwent: TextView = findViewById(R.id.adwent_date)

            wiel.text = getString(R.string.data, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
            cal.add(Calendar.DATE, 60)
            cialo.text = getString(R.string.data, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
            cal.add(Calendar.DATE, -106)
            pop.text = getString(R.string.data, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
            val tmp = adwent(newVal)
            adwent.text = getString(R.string.data, tmp.get(Calendar.DAY_OF_MONTH), tmp.get(Calendar.MONTH)+1, tmp.get(Calendar.YEAR))

        }

        val robocze: Button = findViewById(R.id.roboczeButt)
        robocze.setOnClickListener(){
            val i = Intent(this, Robocze::class.java)
            startActivity(i)
        }
        val niedziele: Button = findViewById(R.id.niedzieleButt)
        niedziele.setOnClickListener(){
            if(year.value >= 2020) {
                val i = Intent(this, Niedziele::class.java)
                i.putExtra("year", year.value.toString())
                startActivity(i)
            }
            else{
                Toast.makeText(this,"Proszę podać rok po roku 2020.", Toast.LENGTH_LONG).show()
            }
        }
    }
}