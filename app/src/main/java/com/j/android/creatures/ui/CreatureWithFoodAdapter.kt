package com.j.android.creatures.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.j.android.creatures.R
import com.j.android.creatures.app.inflate
import com.j.android.creatures.model.Creature
import com.j.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.activity_creature.view.*
import kotlinx.android.synthetic.main.list_item_creature.view.*

class CreatureWithFoodAdapter(private val creatures: MutableList<Creature>) : RecyclerView.Adapter<CreatureWithFoodAdapter.ViewHolder>()
{

	private  val viewPool = RecyclerView.RecycledViewPool()

	class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView){
		private lateinit var creature : Creature
		private val adapter = FoodAdapter(mutableListOf())

		fun bind(creature: Creature){
			this.creature = creature
			val context = itemView.context
			itemView.creature_image.setImageResource(context.resources.getIdentifier(creature.uri, null, context.packageName))
			setupFoods()
		}

		init
		{
			itemView.setOnClickListener(this)
		}

		override fun onClick(view: View?)
		{
			view?.let {
				val context = it.context
				val intent = CreatureActivity.newIntent(context, creature.id)
				context.startActivity(intent)
			}
		}
		private fun setupFoods() {
			itemView.food_recycler.layoutManager =
					LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
			itemView.food_recycler.adapter = adapter

			val foods = CreatureStore.getCreautureFoods(creature)
			adapter.updateFoods(foods)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val holder = ViewHolder(parent.inflate(R.layout.list_item_creature_with_food))
		holder.itemView.food_recycler.setRecycledViewPool(viewPool)
		LinearSnapHelper().attachToRecyclerView(holder.itemView.food_recycler)
		return holder
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		holder.bind(creatures[position])
	}

	override fun getItemCount(): Int = creatures.size
}