package com.example.converter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

enum class RowType{
    THEME,
    ROW
}


class MyAdapter (val dataset: MutableList<RecyclerViewContainer>) : RecyclerView.Adapter<MyViewHolder>(){

    val mutableListIngredient: MutableList<Ingredient>? = null
    var activity: SelectIngredient? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        activity = parent.context as SelectIngredient

        dataset.forEach{recyclerViewContainer: RecyclerViewContainer ->
            if(!recyclerViewContainer.isTheme){
                recyclerViewContainer.ingredient?.let { mutableListIngredient?.add(it) }
            }
        }

        val inflateView : View = when (viewType){
            RowType.ROW.ordinal -> layoutInflater.inflate(R.layout.row_ingredient_layout, parent, false)
            else -> layoutInflater.inflate(R.layout.row_theme_layout, parent, false)
        }
        return MyViewHolder(inflateView)
    }

    override fun getItemCount() = dataset.size

    var lastView: View? = null

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataset[position]

        if(item.isTheme){
            val theme = item.theme
            theme?.let { holder.setTheme(R.id.tvThemeName, theme.themeName!!,R.id.ivIngredient1, R.id.ivIngredient2, theme.image)}
        }else{
            holder.setItems(item.ingredient, R.id.tvIngredientName)
            val ingredient = item.ingredient
            holder.itemView.setOnClickListener {
                if(ingredient != null){
                    activity?.finishMe(ingredient.name, ingredient.density)
                }
            }
        }


    }



    override fun getItemViewType(position: Int): Int {
        if (dataset[position].isTheme) {
            return 0
        }else {
            return 1
        }
    }
}

class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val intList: MutableList<Int>? = null
    private val filteredMap: MutableMap<Int, View> = HashMap()
    private val viewMap: MutableMap<Int, View> = HashMap()

    init {
        findViewItems(itemView)
    }

    private fun findViewItems(itemView: View) {
        addToMap(itemView)
        if (itemView is ViewGroup) {
            val childCount = itemView.childCount
            (0 until childCount)
                .map { itemView.getChildAt(it) }
                .forEach { findViewItems(it) }
        }
    }

    private fun addToMap(itemView: View) {
        viewMap[itemView.id] = itemView
    }

    fun setTheme(
        @IdRes id: Int,
        text: String,
        @IdRes imageViewId: Int,
        @IdRes imageViewId2: Int,
        image: Int
    ) {

        val view =
            (viewMap[id] ?: throw IllegalArgumentException("View for $id not found")) as? TextView
                ?: throw IllegalArgumentException("View for $id is not a TextView")

        val imageView = (viewMap[imageViewId]
            ?: throw IllegalArgumentException("View for $imageViewId not found")) as ImageView
        val imageView2 = (viewMap[imageViewId2]
            ?: throw IllegalArgumentException("View for $imageViewId2 not found")) as ImageView

        val res = itemView.context.resources

        val bm = BitmapFactory.decodeResource(res, image)
        imageView.setImageResource(R.drawable.ic_launcher_background)
        imageView2.setImageResource(image)

        view.text = text


    }

    @SuppressLint("ResourceAsColor")
    fun setSelected(view: View) {
        for ((k, v) in viewMap) {
           /* if (view == v) {
                v.setBackgroundColor(R.color.colorPrimary)
                println("Yes Selected - $v ++ $view")
            } else {
                v.setBackgroundColor(R.color.colorPrimaryDark)
                println("No Selected - $v ++ $view")
            }*/
        }
    }
    @SuppressLint("ResourceAsColor")
    fun setUnselected(view: View) {
        for ((k, v) in viewMap) {
            if (view == v) {
                println("Yes Unselected - $v ++ $view")
                v.setBackgroundColor(R.color.colorPrimary)
            } else {
                println("No Unselected - $v ++ $view")
                v.setBackgroundColor(R.color.colorAccent)
            }
        }
    }



    fun setItems(item: Ingredient?, @IdRes textViewId: Int) {
        val view = (viewMap[textViewId]
            ?: throw IllegalArgumentException("View for $textViewId not found")) as? TextView
            ?: throw IllegalArgumentException("View for $textViewId is not a TextView")
        intList?.add(textViewId)
        view.text = item?.name
    }

}