package com.j.android.creatures.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.j.android.creatures.R
import com.j.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment(), ItemDragListener {

  private val adapter = CreatureAdapter(mutableListOf(), this)
  private lateinit var itemTouchHelper : ItemTouchHelper

  companion object {
    fun newInstance(): FavoritesFragment {
      return FavoritesFragment()
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    favourites_recyclerview.layoutManager = LinearLayoutManager(activity)
    favourites_recyclerview.adapter = adapter
    val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
    context?.let {
      favourites_recyclerview.addItemDecoration(
        DividerItemDecoration(
          ContextCompat.getColor(it, R.color.black), heightInPixels))
    }
    setupItemTouchHelper()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_favorites, container, false)
  }

  override fun onResume() {
    super.onResume()
    activity?.let {
      CreatureStore.getFavourites(it)?.let {
          favorites ->
        adapter.updateCreatures(favorites)
      }
    }
  }

  private fun setupItemTouchHelper() {
    itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
    itemTouchHelper.attachToRecyclerView(favourites_recyclerview)
  }

  override fun onItemDrag(viewHolder: RecyclerView.ViewHolder) {
    itemTouchHelper.startDrag(viewHolder)

  }


}