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

import java.io.File

import scala.collection.mutable.{ ArrayBuffer, Buffer }

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.{ Mojo, Parameter, LifecyclePhase }
import org.apache.maven.project.MavenProject
import org.apache.maven.plugin.MojoExecutionException

@Mojo(name = "ws-check", defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
class WSCheckMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true)
  private var project: MavenProject = _

  def execute(): Unit = {
    import scala.collection.JavaConverters.asScalaBuffer
    val warns = new ArrayBuffer[String]
    asScalaBuffer(project.getCompileSourceRoots) foreach { resource =>
      check(resource, warns)
    }

    asScalaBuffer(project.getTestCompileSourceRoots) foreach { resource =>
      check(resource, warns)
    }

    asScalaBuffer(project.getResources) foreach { resource =>
      check(resource.getDirectory, warns)
    }

    asScalaBuffer(project.getTestResources) foreach { resource =>
      check(resource.getDirectory, warns)
    }
    if (!warns.isEmpty) {
      getLog().warn("Whitespace violations:\n" + warns.mkString("\n"));
      throw new MojoExecutionException("Find violations");
    }
  }

  private def check(path: String, warns: Buffer[String]): Unit = {
    if (new File(path).exists()) {
      getLog.info(s"formating ${path} ...")
      Checker.check(new File(path), warns)
    }
  }
}