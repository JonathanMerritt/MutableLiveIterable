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
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action.Added
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action.Removed

class ViewGroupObserver<TYPE>(
  private val group: ViewGroup,
  private val layout: Int,
  private val notify: (View, Int, TYPE) -> Unit
) : Observer<Action<TYPE>> {
  private var types: Iterable<TYPE> = listOf()

  init {
    clear()
  }

  private fun add(index: Int) =
    if (index <= group.children.count() && types.count() - 1 == group.children.count())
      group.addView(inflate(), index).run { adjust(index) } else refresh()

  private fun remove(index: Int) = if (index < group.children.count())
    group.removeViewAt(index).run { adjust(index) } else refresh()

  private fun adjust(index: Int) = types.forEachIndexed { i, type ->
    if (index <= i) group.children.elementAtOrNull(i)?.let { notify(it, i, type) }
  }

  private fun refresh() {
    clear()
    types.forEachIndexed { index, type ->
      group.addView(inflate(), index)
      group.children.elementAtOrNull(index)?.let { notify(it, index, type) }
    }
  }

  private fun clear() { if (group.children.any()) group.removeAllViews() }

  private fun inflate() = from(group.context).inflate(layout, group, false)

  override fun onChanged(action: Action<TYPE>) {
    types = action.types
    when (action) {
      is Added<*> -> add(action.index)
      is Removed<*> -> remove(action.index)
    }
  }
}
