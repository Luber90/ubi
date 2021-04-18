package com.example.kalendarz

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class Niedziele : AppCompatActivity() {
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_niedziele)
        val extras = intent.extras ?: return
        val message: Int?
        message = extras.getString("year")?.toInt()
        val rok = message!!
        val title: TextView = findViewById(R.id.niedziele)
        val list: MutableList<String> = ArrayList()
        title.text = getString(R.string.title, rok)
        listView = findViewById<ListView>(R.id.itemList)
        var kal = GregorianCalendar(rok, 0, 31)
        var end = GregorianCalendar(rok, 0, 1)
        val wielk = wielkanoc(rok)
        while (end.before(kal)) {
            if(kal.get(Calendar.DAY_OF_WEEK)==1){
                list += kal.get(Calendar.DAY_OF_MONTH).toString()+".1."+rok.toString()
                break
            }
            kal.add(Calendar.DATE, -1)
        }
        wielk.add(Calendar.DATE, -7)
        list += wielk.get(Calendar.DAY_OF_MONTH).toString()+"."+(wielk.get(Calendar.MONTH)+1).toString()+"."+rok.toString()
        kal = GregorianCalendar(rok, 3, 30)
        end = GregorianCalendar(rok, 3, 1)
        while (end.before(kal)) {
            if(kal.get(Calendar.DAY_OF_WEEK)==1){
                list += kal.get(Calendar.DAY_OF_MONTH).toString()+".4."+rok.toString()
                break
            }
            kal.add(Calendar.DATE, -1)
        }
        kal = GregorianCalendar(rok, 5, 30)
        end = GregorianCalendar(rok, 5, 1)
        while (end.before(kal)) {
            if(kal.get(Calendar.DAY_OF_WEEK)==1){
                list += kal.get(Calendar.DAY_OF_MONTH).toString()+".6."+rok.toString()
                break
            }
            kal.add(Calendar.DATE, -1)
        }
        kal = GregorianCalendar(rok, 7, 31)
        end = GregorianCalendar(rok, 7, 1)
        while (end.before(kal)) {
            if(kal.get(Calendar.DAY_OF_WEEK)==1){
                list += kal.get(Calendar.DAY_OF_MONTH).toString()+".8."+rok.toString()
                break
            }
            kal.add(Calendar.DATE, -1)
        }
        kal = GregorianCalendar(rok, 11, 25)
        end = GregorianCalendar(rok, 11, 1)
        while (end.before(kal)) {
            if(kal.get(Calendar.DAY_OF_WEEK)==1){
                kal.add(Calendar.DATE, -7)
                list += kal.get(Calendar.DAY_OF_MONTH).toString()+".12."+rok.toString()
                kal.add(Calendar.DATE, 7)
                list += kal.get(Calendar.DAY_OF_MONTH).toString()+".12."+rok.toString()
                break
            }
            kal.add(Calendar.DATE, -1)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter
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