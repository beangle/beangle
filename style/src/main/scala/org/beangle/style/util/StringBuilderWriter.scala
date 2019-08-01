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
package org.beangle.style.util


import java.io.Writer

@SerialVersionUID(1L)
class StringBuilderWriter(val builder: StringBuilder) extends Writer with Serializable {

  /**
    * Construct a new {@link StringBuilder} instance with the specified capacity.
    * @param capacity The initial capacity of the underlying { @link StringBuilder}
    */
  def this(capacity: Int = 16) {
    this(new StringBuilder(capacity))
  }

  /**
    * Append a single character to this Writer.
    */
  override def append(value: Char): Writer = {
    builder.append(value)
    this
  }

  /**
    * Append a character sequence to this Writer.
    */
  override def append(value: CharSequence): Writer = {
    builder.append(value)
    this
  }

  /**
    * Append a portion of a character sequence to the {@link StringBuilder}.
    */
  override def append(value: CharSequence, start: Int, end: Int): Writer = {
    builder.append(value, start, end)
    this
  }

  /**
    * Closing this writer has no effect.
    */
  override def close(): Unit = {
  }

  /**
    * Flushing this writer has no effect.
    */
  override def flush(): Unit = {
  }

  /**
    * Write a String to the {@link StringBuilder}.
    * @param value The value to write
    */
  override def write(value: String): Unit = {
    if (value != null) builder.append(value)
  }

  /**
    * Write a portion of a character array to the {@link StringBuilder}.
    */
  override def write(value: Array[Char], offset: Int, length: Int): Unit = {
    if (value != null) builder.appendAll(value, offset, length)
  }

  /**
    * Returns {@link StringBuilder#toString()}.
    */
  override def toString(): String = builder.toString
}
