package org.beangle.style.maven

import java.io.{ File, FileInputStream, BufferedReader, InputStreamReader, Reader }
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.project.MavenProject
import org.apache.maven.plugins.annotations.{ Mojo, Parameter, LifecyclePhase, ResolutionScope }
import org.beangle.style.maven.util.Files./
import org.beangle.style.maven.util.{ Strings, Files, MimeTypes }

@Mojo(name = "loc", defaultPhase = LifecyclePhase.PREPARE_PACKAGE, requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME)
class LocMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true)
  private var project: MavenProject = _

  def execute(): Unit = {
    val stats = new collection.mutable.HashMap[String, Int]

    countDir(project.getBasedir, stats)

    var sum = 0
    val rs = stats.toList.sortBy(_._2).reverse
    var maxLength = 0
    rs foreach {
      case (e, c) => {
        if (e.length > maxLength) maxLength = e.length
        sum += c
      }
    }

    println(s"Project has $sum lines codes.")
    rs foreach { t =>
      println(Strings.leftPad(t._1, maxLength, ' ') + "  " + t._2)
    }
  }

  private def countDir(path: File, stats: collection.mutable.Map[String, Int]): Unit = {
    if (path.exists()) {
      println(s"counting ${path.getAbsolutePath} ...")
      count(path, stats)
    }
  }

  private def count(dir: File, stats: collection.mutable.Map[String, Int]): Unit = {
    if (!dir.exists()) return ;

    if (dir.getName == "target") return
    if (dir.isFile) {
      val fileExt = Strings.substringAfterLast(dir.getName, ".")
      if (Strings.isNotBlank(fileExt) && isText(fileExt)) {
        val reader = toBufferedReader(new InputStreamReader(new FileInputStream(dir)))
        var line = reader.readLine()
        var loc = 0
        while (line != null) {
          if (Strings.isNotBlank(line)) loc += 1
          line = reader.readLine()
        }
        Files.close(reader)
        stats.get(fileExt) match {
          case Some(c) => stats.put(fileExt, c + loc)
          case None    => stats.put(fileExt, loc)
        }
      }
    } else {
      dir.list() foreach { childName =>
        count(new File(dir.getAbsolutePath + / + childName), stats)
      }
    }
  }

  private def toBufferedReader(reader: Reader): BufferedReader = {
    if (reader.isInstanceOf[BufferedReader]) reader.asInstanceOf[BufferedReader] else new BufferedReader(reader)
  }

  private def isText(fileExt: String): Boolean = {
    MimeTypes.getMimeType(fileExt) match {
      case Some(m) => (m.getPrimaryType == "text" || fileExt == "xml" || fileExt == "js")
      case None    => false
    }
  }
}