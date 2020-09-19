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
package org.beangle.style.maven

import java.io.File

import org.apache.maven.plugin.{AbstractMojo, MojoExecutionException}
import org.apache.maven.plugins.annotations.{LifecyclePhase, Mojo, Parameter}
import org.apache.maven.project.MavenProject
import org.beangle.style.core.WhiteSpaceFormater
import org.beangle.style.util.Files./
import org.beangle.style.util.Strings

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

@Mojo(name = "ws-check", defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
class WSCheckMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true)
  private var project: MavenProject = _

  def execute(): Unit = {
    import scala.jdk.CollectionConverters._

    val locs = new ArrayBuffer[String]
    project.getCompileSourceRoots.asScala foreach { resource =>
      check(resource, locs)
    }
    project.getTestCompileSourceRoots.asScala foreach { resource =>
      check(resource, locs)
    }
    project.getResources.asScala foreach { resource =>
      check(resource.getDirectory, locs)
    }
    project.getTestResources.asScala foreach { resource =>
      check(resource.getDirectory, locs)
    }
    val warns = new ArrayBuffer[String]
    locs foreach { loc =>
      getLog.info(s"checking $loc ...")
      WhiteSpaceFormater.check(new File(loc), warns)
    }
    if (warns.nonEmpty) {
      val files = warns.map(f => Strings.substringAfter(f, project.getBasedir.getAbsolutePath + /))
      getLog.warn("Whitespace violations:\n" + files.mkString("\n"))
      throw new MojoExecutionException("Find violations")
    }
  }

  private def check(path: String, dirs: mutable.Buffer[String]): Unit = {
    val pathDir = new File(path)
    if (pathDir.exists() && !path.startsWith(project.getBasedir.getAbsolutePath + / + "target")) {
      val loc = pathDir.getCanonicalPath
      if (!dirs.contains(loc)) {
        dirs += loc
      }
    }
  }

}
