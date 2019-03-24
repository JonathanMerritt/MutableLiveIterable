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

sealed class Letter {
  companion object {
    val alphabet get() = listOf(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z)
  }

  object A : Letter()
  object B : Letter()
  object C : Letter()
  object D : Letter()
  object E : Letter()
  object F : Letter()
  object G : Letter()
  object H : Letter()
  object I : Letter()
  object J : Letter()
  object K : Letter()
  object L : Letter()
  object M : Letter()
  object N : Letter()
  object O : Letter()
  object P : Letter()
  object Q : Letter()
  object R : Letter()
  object S : Letter()
  object T : Letter()
  object U : Letter()
  object V : Letter()
  object W : Letter()
  object X : Letter()
  object Y : Letter()
  object Z : Letter()
}
