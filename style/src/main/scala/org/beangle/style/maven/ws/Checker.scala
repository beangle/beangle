/*
 * Beangle,Agile Development Scaffold and Toolkits.
 *
 * Copyright (c) 2005, The Beangle Software.
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

import java.io.{ File, FileInputStream }

import scala.collection.mutable.Buffer

import org.beangle.style.maven.util.{ MimeTypes, Strings }
import org.beangle.style.maven.util.Files
import org.beangle.style.maven.util.Files./

object Checker {

  def check(dir: File, warns: Buffer[String]) {
    if (dir.isFile) {
      val fileExt = Strings.substringAfterLast(dir.getName, ".")
      MimeTypes.getMimeType(fileExt) foreach { m =>
        if (m.getPrimaryType == "text" || fileExt == "xml") {
          if (!check(dir)) {
            warns += dir.getAbsolutePath
          }
        }
      }
    } else {
      dir.list() foreach { childName =>
        check(new File(dir.getAbsolutePath + / + childName), warns)
      }
    }
  }
  def check(file: File): Boolean = {
    val content = Files.readString(new FileInputStream(file))
    if (-1 != content.indexOf("\t") || -1 != content.indexOf(Format.CRLF)) {
      false;
    } else {
      val lines = Strings.split(content, '\n');
      lines.find(l => l.endsWith(" ")).isEmpty
    }
    true;
  }
}
