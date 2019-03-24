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

import android.view.View
import android.widget.RelativeLayout.BELOW
import android.widget.RelativeLayout.LayoutParams
import androidx.annotation.ContentView
import androidx.core.view.updateLayoutParams
import kotlinx.android.synthetic.main.relative.layout_relative

@ContentView(R.layout.relative)
class RelativeFragment : LettersFragment() {
  override fun observer(notify: (View, Int, Letter) -> Unit) =
    ViewGroupObserver<Letter>(layout_relative, R.layout.letter) { view, index, letter ->
      view.id = index + 1
      view.updateLayoutParams<LayoutParams> { addRule(BELOW, index) }
      notify(view, index, letter)
    }
}
