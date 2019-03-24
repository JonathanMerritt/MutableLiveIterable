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
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.main.fragment
import kotlinx.android.synthetic.main.main.toolbar
import kotlinx.android.synthetic.main.main.view_navigation

class MainActivity : AppCompatActivity() {

  override fun onCreate(state: Bundle?) {
    super.onCreate(state)
    setContentView(R.layout.main)
  }

  override fun onPostCreate(state: Bundle?) {
    super.onPostCreate(state)
    setSupportActionBar(toolbar)

    fragment.findNavController().run {
      setupActionBarWithNavController(this)
      view_navigation.setupWithNavController(this)
    }
  }

  override fun onSupportNavigateUp() = fragment.findNavController().navigateUp()
}
