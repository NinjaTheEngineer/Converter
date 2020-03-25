package com.example.converter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("Registered")
class SelectIngredient: Activity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private var itemList = mutableListOf<RecyclerViewContainer>()

    companion object{
        fun newInstance(): SelectIngredient{
            return SelectIngredient()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_ingredient_layout)
        viewAdapter = MyAdapter(itemList)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = viewAdapter

        setData()
        setRecyclerView()
    }

    private fun setRecyclerView(){
        viewManager = LinearLayoutManager(this.applicationContext)
        viewAdapter = MyAdapter(itemList)
        println("Passsed hereeeee")

        recyclerView = recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            visibility = View.VISIBLE
        }
    }

    private fun setData() {
        //Sugar & Sweets
        val sugarTheme = Theme("Sugar & Sweets", R.drawable.ic_launcher_background)
        val brownSugar = Ingredient("Brown Sugar", 0.823, false)
        val granulatedSugar = Ingredient("Granulated Sugar", 0.849, false)
        val powderedSugar = Ingredient("Powdered Sugar", 0.511, false)
        val honey = Ingredient("Honey", 1.42, false)
        val stevia = Ingredient("Stevia", 0.443, false)

        //Flour
        val flourTheme = Theme("Flour", R.drawable.ic_launcher_background)
        val allPurposeFlour = Ingredient("All Purpose Flour", 0.529, false)
        val almondFlour = Ingredient("Almond Flour", 0.406, false)
        val breadFlour = Ingredient("Bread Flour", 0.55, false)
        val cakeFlour = Ingredient("Cake Flour", 0.482, false)
        val wholeWheatFlour = Ingredient("Whole Wheat Flour", 0.478, false)
        val bakingPowder = Ingredient("Baking Powder", 0.9, false)
        val bakingSoda = Ingredient("Baking Soda", 0.689, false)

        //Fats & Oils
        val fatsTheme = Theme("Fats & Oils", R.drawable.ic_launcher_background)
        val butter = Ingredient("Butter", 0.959, false)
        val lard = Ingredient("Lard", 0.919, false)
        val margarine = Ingredient("Margarine", 0.973, false)
        val coconutOil = Ingredient("Coconut Oil", 0.92427, false)
        val oliveOil = Ingredient("Olive Oil", 0.92, false)
        val sunflowerOil = Ingredient("Sunflower Oil", 0.96, false)

        //Milk & Cream
        val milkTheme = Theme("Milk & Cream", R.drawable.ic_launcher_background)
        val milk = Ingredient("Milk", 1.035, false)
        val lightCream = Ingredient("Light Cream", 1.012, false)
        val heavyCream = Ingredient("Heavy Cream", 0.994, false)

        //Nuts & Seeds
        val nutsTheme = Theme("Nuts & Seeds", R.drawable.ic_launcher_background)
        val almonds = Ingredient("Almonds", 0.46, false)
        val cashews = Ingredient("Cashwes", 0.5, false)
        val peanuts = Ingredient("Peanuts", 0.272, false)
        val sunflowerSeeds = Ingredient("Sunflower Seeds", 0.62, false)

        //Others
        val othersTheme = Theme("Others", R.drawable.ic_launcher_background)
        val cocoaPowder = Ingredient("Cocoa Powder", 0.641, false)
        val saltFine = Ingredient("Salt Fine", 1.201, false)
        val yeast = Ingredient("Yeast", 0.95, false)
        val yougurt = Ingredient("Yogurt (plain)", 1.06, false)

        //ITEMS TO ADD

        //Sugar & Sweets
        val item0 = RecyclerViewContainer(null, true, sugarTheme)
        val item1 = RecyclerViewContainer(brownSugar, false, null)
        val item2 = RecyclerViewContainer(granulatedSugar, false, null)
        val item3 = RecyclerViewContainer(powderedSugar, false, null)
        val item4 = RecyclerViewContainer(honey, false, null)
        val item5 = RecyclerViewContainer(stevia, false, null)

        //Flour
        val item6 = RecyclerViewContainer(null, true, flourTheme)
        val item7 = RecyclerViewContainer(allPurposeFlour, false, null)
        val item8 = RecyclerViewContainer(almondFlour, false, null)
        val item9 = RecyclerViewContainer(breadFlour, false, null)
        val item10 = RecyclerViewContainer(cakeFlour, false, null)
        val item11 = RecyclerViewContainer(wholeWheatFlour, false, null)
        val item12 = RecyclerViewContainer(bakingPowder, false, null)
        val item13 = RecyclerViewContainer(bakingSoda, false, null)

        //Fats & Oils
        val item14 = RecyclerViewContainer(null, true, fatsTheme)
        val item15 = RecyclerViewContainer(butter, false, null)
        val item16 = RecyclerViewContainer(lard, false, null)
        val item17 = RecyclerViewContainer(margarine, false, null)
        val item18 = RecyclerViewContainer(coconutOil, false, null)
        val item19 = RecyclerViewContainer(oliveOil, false, null)
        val item20 = RecyclerViewContainer(sunflowerOil, false, null)

        //Milk & Cream
        val item21 = RecyclerViewContainer(null, true, milkTheme)
        val item22 = RecyclerViewContainer(milk, false, null)
        val item23 = RecyclerViewContainer(lightCream, false, null)
        val item24 = RecyclerViewContainer(heavyCream, false, null)

        //Nuts & Seeds
        val item25 = RecyclerViewContainer(null, true, nutsTheme)
        val item26 = RecyclerViewContainer(almonds, false, null)
        val item27 = RecyclerViewContainer(cashews, false, null)
        val item28 = RecyclerViewContainer(peanuts, false, null)
        val item29 = RecyclerViewContainer(sunflowerSeeds, false, null)

        //Others
        val item30 = RecyclerViewContainer(null, true, othersTheme)
        val item31 = RecyclerViewContainer(cocoaPowder, false, null)
        val item32 = RecyclerViewContainer(saltFine, false, null)
        val item33 = RecyclerViewContainer(yeast, false, null)
        val item34 = RecyclerViewContainer(yougurt, false, null)

        itemList.add(item0)
        itemList.add(item1)
        itemList.add(item2)
        itemList.add(item3)
        itemList.add(item4)
        itemList.add(item5)
        itemList.add(item6)
        itemList.add(item7)
        itemList.add(item8)
        itemList.add(item9)
        itemList.add(item10)
        itemList.add(item11)
        itemList.add(item12)
        itemList.add(item13)
        itemList.add(item14)
        itemList.add(item15)
        itemList.add(item16)
        itemList.add(item17)
        itemList.add(item18)
        itemList.add(item19)
        itemList.add(item20)
        itemList.add(item21)
        itemList.add(item22)
        itemList.add(item23)
        itemList.add(item24)
        itemList.add(item25)
        itemList.add(item26)
        itemList.add(item27)
        itemList.add(item28)
        itemList.add(item29)
        itemList.add(item30)
        itemList.add(item31)
        itemList.add(item32)
        itemList.add(item33)
        itemList.add(item34)

    }

    fun finishMe(ingredientName: String, ingredientDensity: Double){
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra("ingredient", ingredientName)
        intent.putExtra("density", ingredientDensity)

        setResult(Activity.RESULT_OK,intent)
        finish()
    }
}