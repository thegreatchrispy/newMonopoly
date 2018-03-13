
       /$$   /$$                                                                   
      | $$$ | $$                                                                   
      | $$$$| $$  /$$$$$$  /$$  /$$  /$$                                           
      | $$ $$ $$ /$$__  $$| $$ | $$ | $$                                           
      | $$  $$$$| $$$$$$$$| $$ | $$ | $$                                           
      | $$\  $$$| $$_____/| $$ | $$ | $$                                           
      | $$ \  $$|  $$$$$$$|  $$$$$/$$$$/                                           
      |__/  \__/ \_______/ \_____/\___/                                            
                                                                                   
                                                                                   
                                                                                   
       /$$      /$$                                                   /$$          
      | $$$    /$$$                                                  | $$          
      | $$$$  /$$$$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$ | $$ /$$   /$$
      | $$ $$/$$ $$ /$$__  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$| $$  | $$
      | $$  $$$| $$| $$  \ $$| $$  \ $$| $$  \ $$| $$  \ $$| $$  \ $$| $$| $$  | $$
      | $$\  $ | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$| $$  | $$
      | $$ \/  | $$|  $$$$$$/| $$  | $$|  $$$$$$/| $$$$$$$/|  $$$$$$/| $$|  $$$$$$$
      |__/     |__/ \______/ |__/  |__/ \______/ | $$____/  \______/ |__/ \____  $$
                                                 | $$                     /$$  | $$
                                                 | $$                    |  $$$$$$/
                                                 |__/                     \______/ 


# Contents
1. [Setup](#1-setup)
    * [Gradle](#gradle-setup)
    * [PostgreSQL](#postgresql-setup)
2. [Edit Files](#2-edit-files)
3. [Building the Project](#3-building-the-project)
4. [Deploying the Project](#4-deploying-the-project)

# 1. Setup
In order to build and deploy this project, you need to have the latest version of Gradle, and PostgreSQL.

## Gradle Setup
The following tutorial for setting up Gradle is taken from the install site located [here](https://gradle.org/install).

### Installation
Gradle runs on all major operating systems and requires only a [Java JDK or JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html) version 7 or higher to be installed. To check, run java -version:

```
$ java -version
java version "1.8.0_121"
```

### Install
#### Install with a package manager
[SDKMAN!](http://sdkman.io/) is a tool for managing parallel versions of multiple Software Development Kits on most Unix-based systems.

```
$ sdk install gradle 4.6
```

[Homebrew](https://brew.sh/) is “the missing package manager for macOS”.

```
$ brew install gradle
```

[Scoop](http://scoop.sh/) is a command-line installer for Windows inspired by Homebrew.

```
$ scoop install gradle
```

[Chocolatey](https://chocolatey.org/) is “the package manager for Windows”.

```
$ choco install gradle
```

[MacPorts](https://www.macports.org/) is a system for managing tools on macOS:

```
$ sudo port install gradle
```

[› Proceed to next steps](#upgrade-with-the-gradle-wrapper)

#### Install manually
##### Step 1. [Download](https://gradle.org/releases/) the latest Gradle distribution

The current Gradle release is version 4.6, released on 28 Feb 2018. The distribution zip file comes in two flavors:

  * [Binary-only](https://services.gradle.org/distributions/gradle-4.6-bin.zip) ([sha256](https://services.gradle.org/distributions/gradle-4.6-bin.zip.sha256))
  * [Complete](https://services.gradle.org/distributions/gradle-4.6-all.zip), with docs and sources ([sha256](https://services.gradle.org/distributions/gradle-4.6-all.zip.sha256))

If in doubt, choose the binary-only version and browse [docs](https://docs.gradle.org/current/userguide/userguide.html) and [sources](https://github.com/gradle/gradle) online.

##### Step 2. Unpack the distribution
*Linux & MacOS users*
Unzip the distribution zip file in the directory of your choosing, e.g.:

```
$ mkdir /opt/gradle
$ unzip -d /opt/gradle gradle-4.6-bin.zip
$ ls /opt/gradle/gradle-4.6
LICENSE  NOTICE  bin  getting-started.html  init.d  lib  media
```

*Microsoft Windows users*
Create a new directory C:\Gradle with **File Explorer**.

Open a second **File Explorer** window and go to the directory where the Gradle distribution was downloaded. Double-click the ZIP archive to expose the content. Drag the content folder gradle-4.6 to your newly created C:\Gradle folder.

Alternatively you can unpack the Gradle distribution ZIP into C:\Gradle using an archiver tool of your choice.

##### Step 3. Configure your system environment
*Linux & MacOS users*
Configure your PATH environment variable to include the bin directory of the unzipped distribution, e.g.:

```
$ export PATH=$PATH:/opt/gradle/gradle-4.6/bin
```

*Microsoft Windows users*
In **File Explorer** right-click on the This PC (or Computer) icon, then click Properties -> Advanced System Settings -> Environmental Variables.

Under System Variables select Path, then click Edit. Add an entry for C:\Gradle\gradle-4.6\bin. Click OK to save.

##### Step 4. Verify your installation
Open a console (or a Windows command prompt) and run gradle -v to run gradle and display the version, e.g.:

```
$ gradle -v

------------------------------------------------------------
Gradle 4.6
------------------------------------------------------------
```

### Upgrade with the Gradle Wrapper
If your existing Gradle-based build uses the [Gradle Wrapper](https://docs.gradle.org/4.6/userguide/gradle_wrapper.html), you can easily upgrade by running the wrapper task, specifying the desired Gradle version:

```
$ ./gradlew wrapper --gradle-version=4.6 --distribution-type=bin
```

Note that it is not necessary for Gradle to be installed to use the Gradle wrapper. The next invocation of gradlew or gradlew.bat will download and cache the specified version of Gradle.

```
$ ./gradlew tasks
Downloading https://services.gradle.org/distributions/gradle-4.6-bin.zip
...
```

## PostgreSQL Setup
The setup for PostgreSQL is easier, just simply download the installer and let it install PostgreSQL for you. Tutorials for installing PostgreSQL can be found [here for all platforms](http://postgresguide.com/setup/install.html).
Remember the username and password you use to set up the database, as you will need that information in the next step.

# 2. Edit Files
Currently, the only file that needs editing is the `application.properties` located at `src/main/resources/application.properties`. Open this file in a text editor, and change the following 3 lines:
    * On line 2, ensure that the database name matches the name you create for your database. The only thing that should change here is the database name after the final slash.
    * On line 3, change the value `postgres_username` to match the username you created in the last step.
    * On line 4, change the value `postgres_password` to match the password you created in the last step.

This will take care of all of the setup and editing. We can now build and deploy the project.

# 3. Building the Project
To build the project, open a terminal and navigate to the project directory. Run the command: 

```
$ gradle build
```

And you will see much information being produced and it should end with something looking like this:

```
BUILD SUCCESSFUL in 3s
3 actionable tasks: 2 executed, 1 up-to-date
```

If you get any errors here, go back and ensure the files were changed correctly in the previous step. Also ensure that your Gradle and PostgreSQL installations are correct and up to date.

# 4. Deploying the Project
Now to deploy the project, we simply need to run the `.jar` file that was produced in the previous step. To do this, run the command:

```
java -jar build/libs/newMonopolySpring.jar
```

After some more information is displayed, the last few lines should look like this:

```
2018-03-09 17:41:16.106  INFO 15312 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
2018-03-09 17:41:16.464  INFO 15312 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-03-09 17:41:16.465  INFO 15312 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Bean with name 'dataSource' has been autodetected for JMX exposure
2018-03-09 17:41:16.472  INFO 15312 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Located MBean 'dataSource': registering with JMX server as MBean [com.zaxxer.hikari:name=dataSource,type=HikariDataSource]
2018-03-09 17:41:16.546  INFO 15312 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2018-03-09 17:41:16.548  INFO 15312 --- [           main] com.newmonopoly.Application              : Started Application in 6.087 seconds (JVM running for 6.705)
```

Notice that the second line from the bottom gives the port number, and the bottom line gives the time it took to start the application.
The project is now deployed and can be accessed from http://localhost:8080/.
