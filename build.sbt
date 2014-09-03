name := "videoMunger"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  javaEbean,
  "commons-io" % "commons-io" % "1.3.2",
  "org.apache.directory.studio" % "org.apache.commons.io" % "2.4",
  "com.google.code.gson" % "gson" % "2.2",
  "org.codehaus.jackson" % "jackson-core-asl" % "1.9.13",
  "org.hibernate" % "hibernate-core" % "4.3.0.CR1",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.0.CR1",
  "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Draft-16",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.googlecode.json-simple" % "json-simple" % "1.1"
)     

play.Project.playScalaSettings
