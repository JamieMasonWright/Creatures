package com.j.android.creatures.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.j.android.creatures.R
import com.j.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.fragment_all.*


class AllFragment : Fragment() {

  private val adapter = CreatureCardAdapter(CreatureStore.getCreatures().toMutableList())
  private lateinit var layoutManager: StaggeredGridLayoutManager

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
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean
  {
    val id = item.itemId
    when(id){
      R.id.accessibility_action_clickable_span_1 -> {
        showListView()
        return true
        }
      R.id.accessibility_action_clickable_span_2 -> {
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
}