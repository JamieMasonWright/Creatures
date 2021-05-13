package com.j.android.creatures.ui

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.j.android.creatures.R
import com.j.android.creatures.app.inflate
import com.j.android.creatures.model.CompositeItem
import com.j.android.creatures.model.Creature
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_planet_header.view.*
import java.lang.IllegalArgumentException

class CreatureAdapter(private val compositeItem : MutableList<CompositeItem>) : RecyclerView.Adapter<CreatureAdapter.ViewHolder>()
{

	class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView){
		private lateinit var creature : Creature

		enum class ViewType{
			HEADER, CREATURE
		}

		fun bind(composite: CompositeItem){
			if(composite.isHeader){
				itemView.headerName.text = composite.header.name
			}else{
				this.creature = composite.creature
				val context = itemView.context
				itemView.creature_image.setImageResource(context.resources.getIdentifier(creature.uri, null, context.packageName))
				itemView.full_name.text = creature.fullName
				itemView.nickname.text = creature.nickname
				animateView(itemView)
			}
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

	fun updateCreatrures(creatures: List<CompositeItem>){
		this.compositeItem.clear()
		this.compositeItem.addAll(creatures)
		notifyDataSetChanged()

	}
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		return when (viewType) {
			ViewHolder.ViewType.HEADER.ordinal -> ViewHolder((parent.inflate(R.layout.list_item_planet_header)))
			ViewHolder.ViewType.CREATURE.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_creature))
			else -> throw IllegalArgumentException()
		}
	}


	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		holder.bind(compositeItem[position])
	}

	override fun getItemCount() = compositeItem.size

	override fun getItemViewType(position: Int): Int {
		return if (compositeItem[position].isHeader){
			ViewHolder.ViewType.HEADER.ordinal
		}else{
			ViewHolder.ViewType.CREATURE.ordinal
		}
	}

}