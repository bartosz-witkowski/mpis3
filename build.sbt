val Scala3Version = "3.1.2"

ThisBuild / scalaVersion := Scala3Version

lazy val utils = (
  project.in(file("utils"))
  .settings(
    libraryDependencies ++= List(
      "org.scala-lang" %% "scala3-staging" % scalaVersion.value,
      "com.novocode" % "junit-interface" % "0.11" % "test"))
)

def mkProject(name: String, path: String) = (
  Project.apply(name, file(path))
  .settings(
    libraryDependencies ++= List(
      "org.scala-lang" %% "scala3-staging" % scalaVersion.value,
      "com.novocode" % "junit-interface" % "0.11" % "test"))
  .dependsOn(utils)
)

// adt --{
lazy val adt1 = mkProject("adt1", "adt-compiler/adt-1")
// }--
// dot -- {
lazy val dot1 = mkProject("dot1", "dot/dot-1")
lazy val dot2 = mkProject("dot2", "dot/dot-2")
lazy val dot3 = mkProject("dot3", "dot/dot-3")
lazy val dot4 = mkProject("dot4", "dot/dot-4")
lazy val dot5 = mkProject("dot5", "dot/dot-5")
lazy val dot6 = mkProject("dot6", "dot/dot-6")
// }
// power --{
lazy val power1 = mkProject("pow1", "power/power-1")
lazy val power2 = mkProject("pow2", "power/power-2")
// }--
// regexp --{
lazy val regexp1 = mkProject("regexp1", "regexp/regexp-1")
// }
