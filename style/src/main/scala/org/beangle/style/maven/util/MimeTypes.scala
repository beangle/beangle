package org.beangle.style.maven.util

import java.net.URL
import javax.activation.MimeType

object MimeTypes {

  def main(args:Array[String]){
    println(readMimeTypes());
  }
  private val types: Map[String, MimeType] = readMimeTypes()

  private def readMimeTypes(): Map[String, MimeType] = {
    val me = getClass.getClassLoader
    val url = me.getResource("org/beangle/style/maven/text.types")
    if (null == url) return Map.empty
    val buf = new collection.mutable.HashMap[String, MimeType]
    Files.readLines(url.openStream(), Charsets.UTF_8) foreach { line =>
      if (Strings.isNotBlank(line) && !line.startsWith("#")) {
        val mimetypeStr = Strings.substringBetween(line, "=", "exts").trim
        assert(!buf.contains(mimetypeStr), "duplicate mime type:" + mimetypeStr)
        val mimetype = new MimeType(mimetypeStr)
        buf.put(mimetypeStr, mimetype)

        val exts = Strings.substringAfter(line, "exts").trim.substring(1)
        if (Strings.isNotBlank(exts)) {
          Strings.split(exts, ',') foreach { ext =>
            val extension = ext.trim
            val exists = buf.get(extension)
            assert(exists.isEmpty, s"exists $extension = " + exists.get + ", the newer is " + mimetype)
            buf.put(extension, mimetype)
          }
        }
      }
    }
    buf.toMap
  }

  def getMimeType(ext: String, defaultValue: MimeType): MimeType = {
    types.get(ext).getOrElse(defaultValue)
  }

  def getMimeType(ext: String): Option[MimeType] = {
    types.get(ext)
  }

  def parse(str: String): Seq[MimeType] = {
    if (null == str) return Seq.empty

    val mimeTypes = new collection.mutable.ListBuffer[MimeType]
    Strings.split(str, ',') foreach { token =>
      val commaIndex = token.indexOf(";")
      val mimetype = if (commaIndex > -1) token.substring(0, commaIndex).trim else token.trim
      types.get(mimetype) match {
        case Some(mt) => mimeTypes += mt
        case None     => new MimeType(mimetype)
      }
    }
    mimeTypes.toList
  }
}
