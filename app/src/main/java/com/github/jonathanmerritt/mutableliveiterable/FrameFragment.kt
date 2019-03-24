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
import android.widget.FrameLayout.LayoutParams
import androidx.annotation.ContentView
import androidx.core.view.updateLayoutParams
import kotlinx.android.synthetic.main.frame.layout_frame

@ContentView(R.layout.frame)
class FrameFragment : LettersFragment() {

  override fun observer(notify: (View, Int, Letter) -> Unit) =
    ViewGroupObserver<Letter>(layout_frame, R.layout.letter) { view, index, letter ->
      view.updateLayoutParams<LayoutParams> { topMargin = height * index }
      notify(view, index, letter)
    }
}
