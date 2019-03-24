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

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action
import kotlinx.android.synthetic.main.letter.view.chip_add
import kotlinx.android.synthetic.main.letter.view.chip_remove
import kotlinx.android.synthetic.main.letter.view.view_title

abstract class LettersFragment : Fragment() {
  private val letters = MutableLiveIterable<Letter>()

  abstract fun observer(notify: (View, Int, Letter) -> Unit): Observer<Action<Letter>>

  override fun onViewCreated(root: View, state: Bundle?) {
    setHasOptionsMenu(true)
    super.onViewCreated(root, state)

    letters.observe(this, observer { view, index, letter ->
      view.view_title.text = getString(R.string.title_letter, index, letter.javaClass.simpleName)

      view.chip_add.setOnClickListener { v ->
        PopupMenu(ContextThemeWrapper(v.context, R.style.Popup), v).apply {
          (0..letters.count()).forEach { i ->
            menu.add(0, i, i, "$i").setOnMenuItemClickListener {
              when (val l = letter::class.objectInstance) {
                is Letter -> {
                  letters[it.order] = l
                  true
                }
                else -> false
              }
            }
          }
        }.show()
      }

      view.chip_remove.setOnClickListener { letters -= index }
    })

    load()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.letters, menu)

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.letters_clear -> clear()
    R.id.letters_reload -> clear() and load()
    else -> false
  }

  private fun load() = letters.none().also { if (it) letters += Letter.alphabet }

  private fun clear() = letters.any().also { if (it) letters -= letters }
}
