val scala3Version = "3.1.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "mpis3",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= List(
      "org.scala-lang" %% "scala3-staging" % scalaVersion.value,
      "com.novocode" % "junit-interface" % "0.11" % "test")
  )
