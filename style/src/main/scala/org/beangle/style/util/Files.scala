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

import java.nio.charset.Charset
import java.io.InputStreamReader
import java.io.Writer
import java.io.InputStream
import java.io.Reader
import java.io.File
import java.io.OutputStream
import java.io.BufferedReader

object Files {
  private val defaultBufferSize = 1024 * 4

  private val eof = -1

  val / = File.separator

  def readString(input: InputStream, charset: Charset = Charsets.UTF_8): String = {
    try {
      val sw = new StringBuilderWriter(16)
      copy(new InputStreamReader(input, charset), sw)
      sw.toString
    } finally {
      close(input)
    }
  }

  def readLines(input: InputStream, charset: Charset = Charsets.UTF_8): List[String] = {
    readLines(new InputStreamReader(input, charset))
  }

  def readLines(input: Reader): List[String] = {
    val reader = toBufferedReader(input)
    val list = new collection.mutable.ListBuffer[String]
    var line = reader.readLine()
    while (line != null) {
      list += line
      line = reader.readLine()
    }
    close(input)
    list.toList
  }

  private def copy(input: Reader, output: Writer): Long = {
    val buffer = new Array[Char](defaultBufferSize)
    var count = 0
    var n = input.read(buffer)
    while (eof != n) {
      output.write(buffer, 0, n)
      count += n
      n = input.read(buffer)
    }
    close(input)
    count
  }

  private def toBufferedReader(reader: Reader): BufferedReader = {
    if (reader.isInstanceOf[BufferedReader]) reader.asInstanceOf[BufferedReader] else new BufferedReader(reader)
  }

  def write(data: String, output: OutputStream, charset: Charset = null) : Unit ={
    if (data != null) {
      if (charset == null)
        output.write(data.getBytes())
      else
        output.write(data.getBytes(charset))
    }
  }

  def close(objs: AutoCloseable*): Unit = {
    objs foreach { obj =>
      try {
        if (obj != null) obj.close()
      } catch {
        case ioe: Exception =>
      }
    }
  }
}