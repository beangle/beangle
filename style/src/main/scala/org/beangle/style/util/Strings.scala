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

object Strings {

  private val Empty = ""

  private val Index_not_found = -1

  def isEmpty(cs: CharSequence): Boolean = {
    (cs eq null) || 0 == cs.length
  }

  def trimEnd(str: String): String = {
    if (str == null) { null }
    else {
      var len = str.length
      while (len > 0 && (str.charAt(len - 1) <= ' ')) {
        len -= 1
      }
      if (len < str.length) str.substring(0, len) else str
    }
  }

  def substringAfterLast(str: String, separator: String): String = {
    if (isEmpty(str)) return str
    if (isEmpty(separator)) return Empty
    val pos = str.lastIndexOf(separator)
    if (pos == Index_not_found || pos == str.length - separator.length) return Empty
    str.substring(pos + separator.length)
  }

  def substringAfter(str: String, separator: String): String = {
    if (isEmpty(str)) return str
    if (separator == null) return Empty
    val pos = str.indexOf(separator)
    if (pos == Index_not_found) return Empty
    str.substring(pos + separator.length)
  }

  def substringBetween(str: String, open: String, close: String): String = {
    if (str == null || open == null || close == null) return null
    val start = str.indexOf(open)
    if (start != Index_not_found) {
      val end = str.indexOf(close, start + open.length)
      if (end != Index_not_found) return str.substring(start + open.length, end)
    }
    null
  }
  def replace(text: String, searchString: String, replacement: String): String = {
    if (isEmpty(text) || isEmpty(searchString) || replacement == null) {
      return text
    }
    var start = 0
    var end = text.indexOf(searchString, start)
    if (end == -1) return text
    val replLength = searchString.length
    var increase = replacement.length - replLength
    increase = if (increase < 0) 0 else increase
    increase *= 16
    val buf = new StringBuilder(text.length + increase)
    while (end != -1) {
      buf.append(text.substring(start, end)).append(replacement)
      start = end + replLength
      end = text.indexOf(searchString, start)
    }
    buf.append(text.substring(start))
    buf.toString
  }

  def leftPad(str: String, size: Int, padChar: Char): String = {
    if (str == null) return null
    val pads = size - str.length
    if (pads <= 0) return str
    repeat(padChar, pads).concat(str)
  }

  def isBlank(cs: CharSequence): Boolean = {
    if ((cs eq null) || cs.length == 0) return true
    val strLen = cs.length
    for (i <- 0 until strLen if Character.isWhitespace(cs.charAt(i)) == false) return false
    true
  }

  def isNotBlank(cs: CharSequence): Boolean = !isBlank(cs)

  def repeat(ch: Char, repeat: Int): String = {
    val buf = new Array[Char](repeat)
    var i = repeat - 1
    while (i >= 0) {
      buf(i) = ch
      i -= 1
    }
    new String(buf)
  }

  def split(target: String, separatorChars: Array[Char]): Array[String] = {
    if (null == target) return new Array[String](0)

    val sb = target.toCharArray
    for (separator <- separatorChars if separator != ','; i <- 0 until sb.length if sb(i) == separator) sb(i) = ','
    val targets = split(new String(sb), ',')
    val list = new collection.mutable.ListBuffer[String]
    for (one <- targets if isNotBlank(one)) list += one.trim
    list.toArray
  }

  def split(str: String, separatorChar: Char): Array[String] = {
    if (str == null) return null
    val len = str.length
    if (len == 0) return new Array[String](0)
    val list = new collection.mutable.ListBuffer[String]
    var i = 0
    var start = 0
    val length = str.length
    val chars = new Array[Char](length)
    str.getChars(0, length, chars, 0)
    while (i < len) {
      if (chars(i) == separatorChar) {
        //ignore continue seperator
        if (start < i) list += new String(chars, start, i - start)
        start = i + 1
      }
      i += 1
    }
    if (start < i) list += new String(chars, start, i - start)
    list.toArray
  }
}