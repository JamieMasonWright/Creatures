package com.j.android.creatures.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.j.android.creatures.R
import com.j.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.fragment_all.*


class AllFragment : Fragment() {

  private val adapter = CreatureCardAdapter(CreatureStore.getCreatures().toMutableList())
  private lateinit var layoutManager: StaggeredGridLayoutManager
  private lateinit var listItemDecoration: RecyclerView.ItemDecoration
  private lateinit var gridItemDecoration: RecyclerView.ItemDecoration
  private lateinit var listItemMenu : MenuItem
  private lateinit var gridItemMenu: MenuItem
  private var gridState = GridState.GRID

  private enum class GridState{
    LIST, GRID
  }

  companion object {
    fun newInstance(): AllFragment {
      return AllFragment()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
  {
    super.onCreateOptionsMenu(menu, inflater)
    inflater.inflate(R.menu.menu_all, menu)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_all, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    layoutManager = StaggeredGridLayoutManager(2,  GridLayoutManager.VERTICAL)
    creature_recycler_view.layoutManager = layoutManager
    creature_recycler_view.adapter = adapter
    val spacingInPixels = resources.getDimensionPixelSize(R.dimen.creauture_card_grid_layout_margin)
    listItemDecoration = SpacingItemDecoration(1, spacingInPixels)
    gridItemDecoration= SpacingItemDecoration(2, spacingInPixels)
    creature_recycler_view.addItemDecoration(gridItemDecoration)
    
    creature_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener()
    {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
      {
        super.onScrolled(recyclerView, dx, dy)
        adapter.scrollDirection = if(dy > 0 )
        {
          CreatureCardAdapter.ScrollDirection.DOWN
        }else{
          CreatureCardAdapter.ScrollDirection.UP
        }
      }
    })

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean
  {
    when(item.itemId){
      R.id.accessibility_action_clickable_span_1 -> {
        gridState = GridState.LIST
        updateRecyclerView(1, listItemDecoration, gridItemDecoration)
        showListView()
        return true
        }
      R.id.accessibility_action_clickable_span_2 -> {
        gridState = GridState.GRID
        updateRecyclerView(2, gridItemDecoration, listItemDecoration)
        showGridView()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showListView(){
    layoutManager.spanCount = 1
  }

  private fun showGridView(){
    layoutManager.spanCount = 1
  }

  private fun updateRecyclerView(spanCount: Int, addItemDecoration: RecyclerView.ItemDecoration, removeItemDecoration: RecyclerView.ItemDecoration){
    layoutManager.spanCount = spanCount
    creature_recycler_view.removeItemDecoration(removeItemDecoration)
    creature_recycler_view.addItemDecoration(addItemDecoration)
  }

  override fun onPrepareOptionsMenu(menu: Menu)
  {
    super.onPrepareOptionsMenu(menu)
    listItemMenu = menu.findItem(R.id.accessibility_action_clickable_span_1)
    gridItemMenu = menu.findItem(R.id.accessibility_action_clickable_span_2)
    when(gridState){
      GridState.LIST -> {
        listItemMenu.isEnabled = false
        gridItemMenu.isEnabled = true
      }
      GridState.GRID -> {
        listItemMenu.isEnabled = true
        gridItemMenu.isEnabled = false
      }
    }
  }
}