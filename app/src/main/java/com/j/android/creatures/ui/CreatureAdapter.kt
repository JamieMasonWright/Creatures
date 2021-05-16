package com.j.android.creatures.ui

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Transformations.map
import androidx.recyclerview.widget.RecyclerView
import com.j.android.creatures.R
import com.j.android.creatures.app.inflate
import com.j.android.creatures.model.CompositeItem
import com.j.android.creatures.model.Creature
import com.j.android.creatures.model.Favorites
import kotlinx.android.synthetic.main.list_item_creature.view.*
import kotlinx.android.synthetic.main.list_item_planet_header.view.*
import java.lang.IllegalArgumentException
import java.util.*

class CreatureAdapter(private val creatures: MutableList<Creature>, private val itemDragListener : ItemDragListener): RecyclerView.Adapter<CreatureAdapter.ViewHolder>(), ItemTouchHelperListener {

	inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView), ItemSelectedListener {
		private lateinit var creature: Creature

		init {
			itemView.setOnClickListener(this)
		}

		@SuppressLint("ClickableViewAccessibility")
		fun bind(creature: Creature) {
			this.creature = creature
			val context = itemView.context
			itemView.creature_image.setImageResource(
				context.resources.getIdentifier(creature.uri, null, context.packageName))
			itemView.full_name.text = creature.fullName
			itemView.nickname.text = creature.nickname
			itemView.drag_handle.setOnTouchListener{ _, event ->
				if(event.action == MotionEvent.ACTION_DOWN) {
					itemDragListener.onItemDrag(this)
				}
				false
			}

			animateView(itemView)
		}

		override fun onClick(view: View?) {
			view?.let {
				val context = it.context
				val intent = CreatureActivity.newIntent(context, creature.id)
				context.startActivity(intent)
			}
		}

		private fun animateView(viewToAnimate: View) {
			if (viewToAnimate.animation == null) {
				val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.scale_xy)
				viewToAnimate.animation = animation
			}
		}

		override fun onItemSelected() {
			itemView.list_item_container.setBackgroundColor(
				ContextCompat.getColor(itemView.context, R.color.selectedItem)
			)
		}

		override fun onItemCleared() {
			itemView.list_item_container.setBackgroundColor(0)
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(parent.inflate(R.layout.list_item_creature))
	}

	override fun getItemCount() = creatures.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(creatures[position])
	}

	fun updateCreatures(creatures: List<Creature>) {
		this.creatures.clear()
		this.creatures.addAll(creatures)
		notifyDataSetChanged()
	}

	override fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
		if (fromPosition < toPosition) {
			for (i in fromPosition until toPosition) {
				Collections.swap(creatures, i, i + 1)
			}
		} else {
			for (i in fromPosition downTo toPosition + 1) {
				Collections.swap(creatures, i, i - 1)
			}
		}
		notifyItemMoved(fromPosition, toPosition)
		return true
	}

}