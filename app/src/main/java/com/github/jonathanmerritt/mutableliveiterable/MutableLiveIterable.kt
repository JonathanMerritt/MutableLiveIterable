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

import androidx.lifecycle.MutableLiveData
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action.Added
import com.github.jonathanmerritt.mutableliveiterable.MutableLiveIterable.Action.Removed


class MutableLiveIterable<TYPE> : MutableLiveData<Action<TYPE>>(), Iterable<TYPE> {
  override fun iterator() = (value?.types ?: listOf()).iterator()

  operator fun set(index: Int, type: TYPE) = toMutableList().run {
    if (index <= size) (if (index < size) add(index, type) else this += type).let { value = Added(index, this) }
  }

  operator fun plusAssign(type: TYPE) {
    this[count()] = type
  }

  operator fun plusAssign(types: Iterable<TYPE>) = types.forEach(::plusAssign)

  fun minusAssign(index: Int, type: TYPE) = toMutableList().run {
    if (index < size && this[index] == type) removeAt(index).let { value = Removed(index, this) }
  }

  operator fun minusAssign(index: Int) {
    elementAtOrNull(index)?.let { minusAssign(index, it) }
  }

  operator fun minusAssign(type: TYPE) = minusAssign(indexOf(type), type)

  operator fun minusAssign(types: Iterable<TYPE>) = types.forEach(::minusAssign)


  sealed class Action<TYPE>(
    open val index: Int,
    open val types: Iterable<TYPE>
  ) {

    class Added<TYPE>(
      override val index: Int,
      override val types: Iterable<TYPE>
    ) : Action<TYPE>(index, types)

    class Removed<TYPE>(
      override val index: Int,
      override val types: Iterable<TYPE>
    ) : Action<TYPE>(index, types)
  }
}
