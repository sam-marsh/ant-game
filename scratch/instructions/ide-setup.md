# IDE Setup

This document gives a quick overview of how to set up the code part of the project in your IDE. Should hopefully be relatively simple, since all IDEs have automatic setup for the build tool we are using (Gradle).
First you'll need to pull the project from GitHub.

## Netbeans

Netbeans will require you to install the Gradle plugin. Tools -> Plugins, search for 'Gradle Support', install plugin.
This allows Netbeans to open Gradle files as projects: File -> Open Project, browse to the code root folder (the one which contains build.gradle) and open it.

Then for example to run all the tests, you right click on the project, then 'tasks -> test'.

## Eclipse

File -> Import -> Gradle Project, browse to the code root folder (the one which contains build.gradle) and open it.

Then down the bottom of the screen there is a Gradle Tasks panel, where you can run automatic tasks to build, test etc.

## IntelliJ

File -> New -> Project from Existing Sources, browse to the code root folder (the one which contains build.gradle) and open it.

Then you can add a Gradle Tasks panel, where you can run automatic tasks to build, test etc.
