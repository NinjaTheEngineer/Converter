package com.example.converter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val MILLILITERS: Int = 0
    val GRAMS: Int = 1
    val OZS: Int = 2
    val POUNDS: Int = 3
    val CUPS: Int = 4

    lateinit var recyclerViewIcon: RecyclerView
    var list: MutableList<IconAmount> = mutableListOf()
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var adapter: MyIconAdapter

    val allSizes = doubleArrayOf(1.0, 0.5, 0.33, 0.25, 0.06, 0.02, 0.01, 0.005)

    var newFragment:Intent? = null

    companion object{
        fun newInstance(): MainActivity = MainActivity()
    }

    private var density: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newInstance()
        initializeView()

    }

    private fun initializeIconRecyclerView(active: Boolean){
        recyclerViewIcon.visibility = View.GONE
        if(active){
            recyclerViewIcon.visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewIcon.layoutManager = layoutManager

            adapter = MyIconAdapter(list, this)
            recyclerViewIcon.adapter = adapter
        }
    }

    private fun clearList(){
        if(list.size > 0){
            list.clear()
        }
    }

    private fun addToList(c: IconAmount){
        val toAdd = c
        if(list.size == 0){
            list.add(toAdd)
        }else{
            list.add(list.size-1, toAdd)
        }
    }

    private fun initializeView(){
        newFragment = Intent(this, SelectIngredient::class.java)

        var lastSelectFrom = MILLILITERS
        var lastSelectTo = GRAMS

        manageCupButtons()
        recyclerViewIcon = findViewById(R.id.recyclerViewIcons)

        initializeIconRecyclerView(false)
        deactiveRadioGroupFrom()
        deactiveRadioGroupTo()

        btnConvert.setOnClickListener { onClickConvert() }
        btnIngredient.setOnClickListener { launchSelectIngredient() }

        ArrayAdapter.createFromResource(
                this,
                R.array.from_array,
                android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerConvertFrom.adapter = adapter
                spinnerConvertTo.adapter = adapter
                spinnerConvertTo.setSelection(GRAMS)
            }

        spinnerConvertFrom.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                doToast("Nothing Selected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == spinnerConvertTo.selectedItemPosition){
                    spinnerConvertTo.setSelection(lastSelectFrom)
                    if(lastSelectFrom == CUPS){
                        activeRadioGroupTo()
                    }
                }
                if(position == CUPS){
                    activeRadioGroupFrom()
                }else{
                    deactiveRadioGroupFrom()
                }
                lastSelectFrom = spinnerConvertFrom.selectedItemPosition
                if(checkIngredientSelection(0, position)){
                    btnIngredient.isEnabled = false
                    btnIngredient.isClickable = false
                }else{
                    btnIngredient.isEnabled = true
                    btnIngredient.isClickable = true
                }
            }
        }
        spinnerConvertTo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                doToast("Nothing Selected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == spinnerConvertFrom.selectedItemPosition){
                    spinnerConvertFrom.setSelection(lastSelectTo)
                    if(lastSelectFrom == CUPS){
                        activeRadioGroupFrom()
                    }
                }
                if(position == CUPS){
                    activeRadioGroupTo()
                    tvResult.visibility = View.GONE
                }else{
                    initializeIconRecyclerView(false)
                    tvResult.visibility = View.VISIBLE
                    deactiveRadioGroupTo()
                }
                lastSelectTo = spinnerConvertTo.selectedItemPosition
                if(checkIngredientSelection(1, position)){
                    btnIngredient.isEnabled = false
                    btnIngredient.isClickable = false
                }else{
                    btnIngredient.isEnabled = true
                    btnIngredient.isClickable = true
                }
            }
        }
    }

    private fun checkIngredientSelection(i: Int, position: Int): Boolean{
        if(i == 0){
            return (spinnerConvertTo.selectedItemPosition == CUPS && position == MILLILITERS) ||
                    (spinnerConvertTo.selectedItemPosition == POUNDS && (position == GRAMS || position == OZS)) ||
                    (spinnerConvertTo.selectedItemPosition == OZS && position == GRAMS) ||
                    (spinnerConvertFrom.selectedItemPosition == CUPS && position == MILLILITERS) ||
                    (spinnerConvertFrom.selectedItemPosition == POUNDS && (position == GRAMS || position == OZS)) ||
                    (spinnerConvertFrom.selectedItemPosition == OZS && position == GRAMS) ||
                    (spinnerConvertFrom.selectedItemPosition == MILLILITERS && position == CUPS) ||
                    ((spinnerConvertFrom.selectedItemPosition == GRAMS || spinnerConvertFrom.selectedItemPosition == OZS) && (position == POUNDS)) ||
                    (spinnerConvertFrom.selectedItemPosition == GRAMS && position == OZS) ||
                    (spinnerConvertTo.selectedItemPosition == MILLILITERS && position == CUPS) ||
                    ((spinnerConvertTo.selectedItemPosition == GRAMS || spinnerConvertTo.selectedItemPosition == OZS) && (position == POUNDS)) ||
                    (spinnerConvertTo.selectedItemPosition == GRAMS && position == OZS)
        }
        else{
            return (spinnerConvertFrom.selectedItemPosition == CUPS && position == MILLILITERS) ||
                    (spinnerConvertFrom.selectedItemPosition == POUNDS && (position == GRAMS || position == OZS)) ||
                    (spinnerConvertFrom.selectedItemPosition == OZS && position == GRAMS) ||
                    (spinnerConvertTo.selectedItemPosition == CUPS && position == MILLILITERS) ||
                    (spinnerConvertTo.selectedItemPosition == POUNDS && (position == GRAMS || position == OZS)) ||
                    (spinnerConvertTo.selectedItemPosition == OZS && position == GRAMS) ||
                    (spinnerConvertTo.selectedItemPosition == MILLILITERS && position == CUPS) ||
                    ((spinnerConvertTo.selectedItemPosition == GRAMS || spinnerConvertTo.selectedItemPosition == OZS) && (position == POUNDS )) ||
                    (spinnerConvertTo.selectedItemPosition == GRAMS && position == OZS) ||
                    (spinnerConvertFrom.selectedItemPosition == MILLILITERS && position == CUPS) ||
                    ((spinnerConvertFrom.selectedItemPosition == GRAMS || spinnerConvertFrom.selectedItemPosition == OZS) && (position == POUNDS )) ||
                    (spinnerConvertFrom.selectedItemPosition == GRAMS && position == OZS)
        }

    }

    private fun launchSelectIngredient() {
        startActivityForResult(newFragment, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            if(resultCode == Activity.RESULT_OK){
                val ingredient:String? = data.getStringExtra("ingredient")
                density = data.getDoubleExtra("density", 0.0)

                btnIngredient.text = ingredient
                println(density)

            }
        }
    }

    private fun deactiveRadioGroupFrom(){
        radioGroupFrom?.isEnabled = false
        radioGroupFrom?.visibility = View.GONE
        radioGroupCups?.isEnabled = false
        radioGroupCups?.visibility = View.GONE
        tvSelectAmount.text = "Enter Amount:"
        etAmount?.isEnabled = true
        etAmount?.visibility = View.VISIBLE
    }

    private fun activeRadioGroupFrom(){
        radioGroupFrom?.isEnabled = true
        radioGroupFrom?.visibility = View.VISIBLE
        radioGroupCups?.isEnabled = true
        radioGroupCups?.visibility = View.VISIBLE
        radioGroupCups.check(rb1cup.id)
        tvSelectAmount.text = "Select Amount:"
        etAmount?.isEnabled = false
        etAmount?.visibility = View.GONE
        radioGroupFrom.check(rbMetricFrom.id)
    }

    private fun deactiveRadioGroupTo(){
        radioGroupTo?.isEnabled = false
        radioGroupTo?.visibility = View.GONE
    }

    private fun activeRadioGroupTo(){
        radioGroupTo?.isEnabled = true
        radioGroupTo?.visibility = View.VISIBLE
        radioGroupTo.check(rbMetricTo.id)
    }

    private fun doToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun manageCupButtons(){
        rb1cup.setOnClickListener {
            rb1cup.isChecked = true
            rb12cup.isChecked = false
            rb13cup.isChecked = false
            rb14cup.isChecked = false
            rb1tbsp.isChecked = false
            rb1tsp.isChecked = false
        }
        rb12cup.setOnClickListener {
            rb1cup.isChecked = false
            rb12cup.isChecked = true
            rb13cup.isChecked = false
            rb14cup.isChecked = false
            rb1tbsp.isChecked = false
            rb1tsp.isChecked = false
        }
        rb13cup.setOnClickListener {
            rb1cup.isChecked = false
            rb12cup.isChecked = false
            rb13cup.isChecked = true
            rb14cup.isChecked = false
            rb1tbsp.isChecked = false
            rb1tsp.isChecked = false
        }
        rb14cup.setOnClickListener {
            rb1cup.isChecked = false
            rb12cup.isChecked = false
            rb13cup.isChecked = false
            rb14cup.isChecked = true
            rb1tbsp.isChecked = false
            rb1tsp.isChecked = false
        }
        rb1tbsp.setOnClickListener {
            rb1cup.isChecked = false
            rb12cup.isChecked = false
            rb13cup.isChecked = false
            rb14cup.isChecked = false
            rb1tbsp.isChecked = true
            rb1tsp.isChecked = false
        }
        rb1tsp.setOnClickListener {
            rb1cup.isChecked = false
            rb12cup.isChecked = false
            rb13cup.isChecked = false
            rb14cup.isChecked = false
            rb1tbsp.isChecked = false
            rb1tsp.isChecked = true
        }

    }

    private fun onClickConvert(){
        //doToast("${spinnerConvertFrom.selectedItemPosition} and ${spinnerConvertTo.selectedItemPosition}")
        if(density == 0.0 && spinnerConvertTo.selectedItemPosition != CUPS){
            doToast("Please select an Ingredient.")
            return
        }
        try {
            val checkBox = etAmount.text.toString().toDouble()
        }catch (e:Exception){
            e.printStackTrace()
            doToast("Please correct your value.")
            return
        }
        when(spinnerConvertFrom.selectedItemPosition){
            MILLILITERS -> ifMilliliters(spinnerConvertTo.selectedItemPosition)
            GRAMS -> ifGrams(spinnerConvertTo.selectedItemPosition)
            OZS -> ifOzs(spinnerConvertTo.selectedItemPosition)
            POUNDS -> ifPounds(spinnerConvertTo.selectedItemPosition)
            CUPS -> ifCups(spinnerConvertTo.selectedItemPosition)
        }
    }

    private fun round(toRound: Double, to: Double): Double {
        return (Math.round(toRound * to)/to)
    }

    private fun toCups(sizes: DoubleArray, cups: Double): HashMap<String, Int> {

        var index = 0
        var allCups = cups
        var endCups: HashMap<String, Int> = HashMap()
        var cup = 0
        var cup12 = 0
        var cup13 = 0
        var cup14 = 0
        var tbsp = 0
        var tsp = 0
        var tsp12 = 0
        var tsp14 = 0

        while (index < sizes.size){
            if (allCups >= sizes[index]) {
                println("$allCups +++ ${sizes[index]}")
                allCups -= sizes[index]
                allCups = round(allCups, 1000.0)
                when(index){
                    0 -> {cup++
                          endCups.put("Cup", cup)}
                    1 -> {cup12++
                          endCups.put("Cup12", cup12)}
                    2 -> {cup13++
                          endCups.put("Cup13", cup13)}
                    3 -> {cup14++
                          endCups.put("Cup14", cup14)}
                    4 -> {tbsp++
                          endCups.put("Tbsp", tbsp)}
                    5 -> {tsp++
                          endCups.put("Tsp", tsp)}
                    6 -> {tsp12++
                          endCups.put("Tsp12", tsp12)}
                    7 -> {tsp14++
                          endCups.put("Tsp14", tsp14)}

                }
                index = 0
            }
            index ++
        }

        return endCups
    }

    private fun convertCups(resultToUse: Double){
        val result: Double = resultToUse
        val map = toCups(allSizes, result)
        clearList()
        for((key, value) in map){
            when(key){
                "Cup" -> addToList(IconAmount("", "Cup", value, R.drawable.ic_measuring_cup, 70))
                "Cup12" -> addToList(IconAmount("½", "Cup", value, R.drawable.ic_measuring_cup, 60))
                "Cup13" -> addToList(IconAmount("⅓", "Cup", value, R.drawable.ic_measuring_cup, 50))
                "Cup14" -> addToList(IconAmount("¼", "Cup", value, R.drawable.ic_measuring_cup, 40))
                "Tbsp" -> addToList(IconAmount("", "Tbsp", value, R.drawable.ic_spoon, 60))
                "Tsp" -> addToList(IconAmount("", "Tsp", value, R.drawable.ic_spoon, 50))
                "Tsp12" -> addToList(IconAmount("½", "Tsp", value, R.drawable.ic_spoon, 45))
                "Tsp14" -> addToList(IconAmount("¼", "Tsp", value, R.drawable.ic_spoon, 35))
            }
        }
        initializeIconRecyclerView(true)
    }

    @SuppressLint("SetTextI18n")
    private fun ifMilliliters(to:Int){
        if(to == GRAMS){
            val result: Double = ((etAmount.text).toString().toDouble()) * density
            tvResult.text = "${round(result, 100.0)} grams"
        }else if(to == OZS){
            val result: Double = 3.53 * density * (((etAmount.text).toString().toDouble())/100)
            tvResult.text = "${round(result, 100.0)} ounces"
        }else if(to == POUNDS){
            val result: Double = 0.22 * density * (((etAmount.text).toString().toDouble())/100)
            tvResult.text = "${round(result, 100.0)} pounds"
        }else{
            var cup = 0.0
            when(radioGroupTo.checkedRadioButtonId){
                rbMetricTo.id -> cup = 250.0
                rbUsTo.id -> cup = 236.59
                rbUkTo.id -> cup = 284.13
            }
            val result: Double = (etAmount.text.toString().toDouble()) / cup
            convertCups(result)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun ifGrams(to:Int){
        if(to == MILLILITERS){
            val result: Double = ((etAmount.text).toString().toDouble()) * (1/density)
            tvResult.text = "${round(result, 100.0)} milliliters"
        }else if(to == OZS){
            val result: Double = ((etAmount.text).toString().toDouble()) * 0.0352739
            tvResult.text = "${round(result, 100.0)} ounces"
        }else if(to == POUNDS){
            val result: Double = ((etAmount.text).toString().toDouble()) * 0.0022046
            tvResult.text = "${round(result, 100.0)} pounds"
        }else{
            var cup = 0.0
            when(radioGroupTo.checkedRadioButtonId){
                rbMetricTo.id -> cup = 250.0
                rbUsTo.id -> cup = 236.59
                rbUkTo.id -> cup = 284.13
            }
            val result: Double = ((etAmount.text.toString().toDouble()) / density) / cup
            convertCups(result)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun ifOzs(to:Int){
        if(to == MILLILITERS){
            val result: Double = 2843.95 * density * (((etAmount.text).toString().toDouble())/100)
            tvResult.text = "${round(result, 100.0)} milliliters"
        }else if(to == GRAMS){
            val result: Double = ((etAmount.text).toString().toDouble()) * 28.3495
            tvResult.text = "${round(result, 100.0)} grams"
        }else if(to == POUNDS){
            val result: Double = ((etAmount.text).toString().toDouble()) * 0.0625
            tvResult.text = "${round(result, 100.0)} pounds"
        }else {
            var cup = 0.0
            when(radioGroupTo.checkedRadioButtonId){
                rbMetricTo.id -> cup = 0.1134
                rbUsTo.id -> cup = 0.1198
                rbUkTo.id -> cup = 0.0998
            }
            val result: Double = ((etAmount.text.toString().toDouble()) * cup) / density
            convertCups(result)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun ifPounds(to:Int){
        if(to == MILLILITERS){
            val result: Double = (453.5924 * ((etAmount.text).toString().toDouble()))/ density
            tvResult.text = "${round(result, 100.0)} milliliters"
        }else if(to == GRAMS){
            val result: Double = ((etAmount.text).toString().toDouble()) * 453.59237
            tvResult.text = "${round(result, 100.0)} grams"
        }else if(to == OZS){
            val result: Double = ((etAmount.text).toString().toDouble()) * 16
            tvResult.text = "${round(result, 100.0)} ounces"
        }else{
            var cup = 0.0
            when(radioGroupTo.checkedRadioButtonId){
                rbMetricTo.id -> cup = 1.8144
                rbUsTo.id -> cup = 1.9172
                rbUkTo.id -> cup = 1.5964
            }
            val result: Double = ((etAmount.text.toString().toDouble()) * cup) / density
            convertCups(result)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun ifCups(to:Int){
        var cup = 0.0
        when(radioGroupFrom.checkedRadioButtonId){
            rbMetricFrom.id -> cup = 250.0
            rbUsFrom.id -> cup = 236.59
            rbUkFrom.id -> cup = 284.13
        }
        if(to == MILLILITERS){
            val result: Double = (getCup()) * cup
            tvResult.text = "${round(result, 100.0)} milliliters"
        }else if(to == GRAMS){
            val result: Double = (getCup()) * cup * density
            tvResult.text = "${round(result, 100.0)} grams"
        }else if(to == OZS){
            when(radioGroupFrom.checkedRadioButtonId){
                rbMetricFrom.id -> cup = 8.82
                rbUsFrom.id -> cup = 8.35
                rbUkFrom.id -> cup = 10.02
            }
            val result: Double = (getCup()) * cup * density
            tvResult.text = "${round(result, 100.0)} ounces"
        }else{
            when(radioGroupFrom.checkedRadioButtonId){
                rbMetricFrom.id -> cup = 0.55
                rbUsFrom.id -> cup = 0.52
                rbUkFrom.id -> cup = 0.63
            }
            val result: Double = (getCup()) * cup * density
            tvResult.text = "${round(result, 100.0)} ounces"
        }
    }

    private fun getCup(): Double{
        var toReturn = 0.0

        if(rb1cup.isChecked){
            toReturn = 1.0
        }else if(rb12cup.isChecked){
            toReturn = 1.0 / 2
        }else if(rb13cup.isChecked){
            toReturn = 1.0 / 3
        }else if(rb14cup.isChecked) {
            toReturn = 1.0 / 4
        }else if(rb1tbsp.isChecked) {
            toReturn = 0.06
        }else {
            toReturn = 0.02
        }
        return toReturn
    }
}


