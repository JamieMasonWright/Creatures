package com.j.android.creatures.ui

import android.content.ContentQueryMap
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.j.android.creatures.R
import com.j.android.creatures.app.inflate
import com.j.android.creatures.model.Creature
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_creature.view.creature_image
import kotlinx.android.synthetic.main.list_item_creature.view.nickname
import kotlinx.android.synthetic.main.list_item_creature_card.view.*

class CreatureCardAdapter(private val creatures: MutableList<Creature>) : RecyclerView.Adapter<CreatureCardAdapter.ViewHolder>()
{

	class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView){
		private lateinit var creature : Creature

		fun bind(creature: Creature){
			this.creature = creature
			val context = itemView.context
			val imageResource = context.resources.getIdentifier(creature.uri, null, context.packageName)
			itemView.creature_image.setImageResource(imageResource)
			itemView.nickname.text = creature.nickname
			setBackgroundColour(context, imageResource)
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

		private fun setBackgroundColour(context: Context, imageResource: Int){
			val image = BitmapFactory.decodeResource(context.resources, imageResource)
			Palette.from(image).generate{ palette ->
				palette?.let {
					val backgroundColor = it.getDominantColor(ContextCompat.getColor(context, R.color.design_default_color_primary_dark))
					itemView.creature_card.setBackgroundColor(backgroundColor)
					itemView.nick_name_holder.setBackgroundColor(backgroundColor)
					val textColour = if(isColourDark(backgroundColor)) Color.WHITE else Color.BLACK
					itemView.nickname.setTextColor(textColour)


				}
			}
		}
		private fun isColourDark(color: Int): Boolean
		{
			val darkness = 1 - (0.299 * Color.red(color)) + 0.587 * Color.green(color) + 0.114 * Color.blue(color) / 25
			return darkness >= 0.5
		}
	}


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		return ViewHolder(parent.inflate(R.layout.list_item_creature_card))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		holder.bind(creatures[position])
	}

	override fun getItemCount(): Int = creatures.size

}