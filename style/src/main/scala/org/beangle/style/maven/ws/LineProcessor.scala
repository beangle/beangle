/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.style.maven.ws

import org.beangle.style.maven.util.Strings


trait LineProcessor {
  def process(line: String): String
}

class Tab2Space(tablength: Int = 2) extends LineProcessor {
  private val spaces = " " * tablength

  override def process(line: String): String = {
    Strings.replace(line, "\t", spaces)
  }
}

object TrimTrailingWhiteSpace extends LineProcessor {

  override def process(line: String): String = {
    Strings.trimEnd(line)
  }
}

