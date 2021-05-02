package com.j.android.creatures.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.j.android.creatures.R


class FavoritesFragment : Fragment() {

  companion object {
    fun newInstance(): FavoritesFragment {
      return FavoritesFragment()
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_favorites, container, false)
  }
}