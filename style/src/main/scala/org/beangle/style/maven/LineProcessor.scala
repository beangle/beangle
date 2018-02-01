package org.beangle.style.maven

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

