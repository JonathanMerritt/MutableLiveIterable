/*
 *     Copyright 2018 Jonathan Merritt
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.github.jonathanmerritt.mutableliveiterable

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action.Added
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action.Removed
import com.github.jonathanmerritt.mutableliveiterable.RecyclerViewObserver.Adapter.Holder

class RecyclerViewObserver<TYPE>(
  private val recycler: RecyclerView,
  private val layout: Int,
  private val notify: (View, Int, TYPE) -> Unit
) : Observer<Action<TYPE>> {
  private var types: Iterable<TYPE> = listOf()

  inner class Adapter : androidx.recyclerview.widget.RecyclerView.Adapter<Holder>() {
    inner class Holder(group: ViewGroup) : ViewHolder(from(group.context).inflate(layout, group, false)) {
      infix fun notify(index: Int) { types.elementAtOrNull(index)?.let { notify(itemView, index, it) } }
    }

    override fun getItemCount() = types.count()
    override fun getItemViewType(index: Int) = index
    override fun onCreateViewHolder(group: ViewGroup, index: Int) = Holder(group).also { it notify index }
    override fun onBindViewHolder(holder: Holder, index: Int) = holder notify index
  }

  init {
    recycler.run {
      layoutManager = layoutManager ?: LinearLayoutManager(context, VERTICAL, false)
      itemAnimator = null
      adapter = Adapter()
    }
  }

  override fun onChanged(action: Action<TYPE>) {
    types = action.types
    recycler.adapter?.run {
      when (action) {
        is Added<*> -> notifyItemInserted(action.index)
        is Removed<*> -> notifyItemRemoved(action.index)
      }
    }
  }
}
