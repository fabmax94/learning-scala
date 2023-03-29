val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "learning-scala",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    scalacOptions ++= List("-Xmax-inlines", "1000000"),
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += "dev.zio" %% "zio-http" % "0.0.5",
    libraryDependencies += "com.google.code.gson" % "gson" % "2.10.1",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.2.11",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % "1.2.11",

    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % "1.2.11"
  )
