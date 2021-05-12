package com.j.android.creatures.ui

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.j.android.creatures.R
import com.j.android.creatures.app.inflate
import com.j.android.creatures.model.Creature
import kotlinx.android.synthetic.main.list_item_creature.view.*

class CreatureAdapter(private val creatures: MutableList<Creature>) : RecyclerView.Adapter<CreatureAdapter.ViewHolder>()
{

	class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView){
		private lateinit var creature : Creature

		fun bind(creature: Creature){
			this.creature = creature
			val context = itemView.context
			itemView.creature_image.setImageResource(context.resources.getIdentifier(creature.uri, null, context.packageName))
			itemView.full_name.text = creature.fullName
			itemView.nickname.text = creature.nickname
			animateView(itemView)
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

		private fun animateView(viewToAnimate : View){
			if(viewToAnimate.animation == null){
				val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.scale_xy)
				viewToAnimate.animation = animation
			}
		}


	}

	fun updateCreatrures(creatures: List<Creature>){
		this.creatures.clear()
		this.creatures.addAll(creatures)
		notifyDataSetChanged()

	}
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		return ViewHolder(parent.inflate(R.layout.list_item_creature))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		holder.bind(creatures[position])
	}

	override fun getItemCount(): Int = creatures.size

}