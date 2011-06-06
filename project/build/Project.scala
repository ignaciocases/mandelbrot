


import sbt._

class CardanoProject(info: ProjectInfo) extends DefaultWebProject(info) {

  val scalatoolsRelease = "Scala Tools Snapshot" at
  "http://scala-tools.org/repo-releases/"

  val scalatoolsSnapshot = ScalaToolsSnapshots

  val liftVersion = "2.3"
  val cappuccinoVersion = "0.9"

  // If you're using JRebel for Lift development, uncomment
  // this line
  override def scanDirectories = Nil

	val mapper = "net.liftweb" %% "lift-mapper" % liftVersion
	val postgresql = "postgresql" % "postgresql" % "9.0-801.jdbc4"
	val h2 = "com.h2database" % "h2" % "1.3.146"

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-testkit" % liftVersion % "compile->default",
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "junit" % "junit" % "4.5" % "test->default",
    "org.scala-tools.testing" %% "specs" % "1.6.6" % "test->default"
  ) ++ super.libraryDependencies
}