package com.j.android.creatures.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.j.android.creatures.R
import com.j.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {

  private val adapter = CreatureAdapter(mutableListOf())

  companion object {
    fun newInstance(): FavoritesFragment {
      return FavoritesFragment()
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?)
  {
    super.onViewCreated(view, savedInstanceState)
    favourites.layoutManager = LinearLayoutManager(activity)
    favourites.adapter = adapter

  }

  override fun onResume()
  {
    super.onResume()
    activity?.let {
      CreatureStore.getFavourites(it)?.let {
        favourites ->
        adapter.updateCreatrures(favourites)
      }
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_favorites, container, false)
  }
}