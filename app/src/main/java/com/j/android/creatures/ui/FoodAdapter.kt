package com.j.android.creatures.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.j.android.creatures.R
import com.j.android.creatures.app.inflate
import com.j.android.creatures.model.Creature
import com.j.android.creatures.model.Food
import kotlinx.android.synthetic.main.list_item_food.view.*

class FoodAdapter(private val foods: MutableList<Food>) :  RecyclerView.Adapter<FoodAdapter.ViewHolder>()
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		return ViewHolder(parent.inflate(R.layout.list_item_food))
	}

	override fun onBindViewHolder(holder:ViewHolder, position: Int)
	{
		holder.bind(foods[position])
	}

	fun updateFoods(foods: List<Food>){
		this.foods.clear()
		this.foods.addAll(foods)
		notifyDataSetChanged()

	}

	override fun getItemCount(): Int = foods.size

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
		private lateinit var food : Food

		fun bind(food: Food){
			this.food = food
			val context = itemView.context
			itemView.food_Image.setImageResource(context.resources.getIdentifier(food.thumbnail, null, context.packageName))

		}

	}
}