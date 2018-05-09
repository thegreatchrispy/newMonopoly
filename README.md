
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
    * [Maven](#maven-setup)
    * [MySQL](#mysql-setup)
2. [Edit Files](#2-edit-files)
3. [Building the Project](#3-building-the-project)
4. [Deploying the Project](#4-deploying-the-project)
5. [Current Project Files](#5-current-project-files)
6. [Needs Completion](#6-needs-completion)

# 1. Setup
In order to build and deploy this project, you need to have the latest version of Maven, and MySQL.

## Maven Setup
Maven is a build system that will allow you to build and deploy the project.<br/>
There is a thorough tutorial for setting up Maven which can be located [here](https://www.tutorialspoint.com/maven/maven_environment_setup.htm). This tutorial will walk you through the process of installing and changing the appropriate environment variables to start using Maven.

## MySQL Setup
MySQL is the relational database that is supported by this project.<br/>
The MySQL website has a tutorial for installation and usage which can be found [here](https://dev.mysql.com/doc/mysql-getting-started/en/). 

**\*\*IMPORTANT: Remember your username and password for the MySQL database on your system. You will need it later in order to build the project correctly!**

After the two installations, you have the tools needed to build and deploy the project. Now we will edit some configuration files.

# 2. Edit Files
Currently, the only file that needs editing is the `application.properties` located at `src/main/resources/application.properties`. Open this file in a text editor, and change the following 3 lines:
  * On line 4, ensure that the database name matches the name you create for your database. The only thing that should change here is the database name after the final slash. This name comes from the SQL command `CREATE DATABASE database_name`. **Make sure to run this command before running the project.**
  * On line 5, change the value `mysql_username` to match the username you use for your MySQL database system.
  * On line 6, change the value `mysql_password` to match the password you use for your MySQL database system.

This will take care of all of the setup and editing. We can now build and deploy the project.

# 3. Building the Project
To build the project, open a terminal and navigate to the project directory. Run the following command for your operating system:

OS | Output
----- | -----
Windows | ```build -m```
Linux | ```./build.sh -m```
Mac | ```./build.sh -m```

And you will see much information being produced and it should end with something looking like this:

```
[INFO]
[INFO] --- maven-jar-plugin:2.6:jar (default-jar) @ NewMonopoly ---
[INFO] Building jar: C:\Users\cwall\Desktop\newMonopoly\target\NewMonopoly-0.0.1-SNAPSHOT.jar
[INFO]
[INFO] --- spring-boot-maven-plugin:1.4.2.RELEASE:repackage (default) @ NewMonopoly ---
[INFO]
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ NewMonopoly ---
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 27.200 s
[INFO] Finished at: 2018-05-03T01:14:54-05:00
[INFO] ------------------------------------------------------------------------
```

If you get any errors here, go back and ensure the files were changed correctly in the previous step. Also ensure that your Maven and MySQL installations are correct and up to date.

# 4. Deploying the Project
Now to deploy the project, we simply need to run the `.jar` file that was produced in the previous step. To do this, run the command:

```
java -jar target/newMonopoly.jar
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

# 5. Current Project Files
Here is the current file structure of the project:
```
docs
src
  |main
    |java
      |com
        |newmonopoly
          |configuration
          |controller
          |model
          |repository
          |service
          |Application.java
    |resources
      |static
        |css
        |images
        |jquery
        |js
      |templates
        |*.html
      |application.properties
      |data.sql
  test
    |main
      |java
        |com
          |newmonopoly
            |*Test.java
  text
    |com
      |newmonopoly
        |*.java
    |bugs.txt
    |build.bat
    |build.sh
    |*.json
    |gson-2.8.2.jar
build.bat
build.sh
deploy.bat
pom.xml
README.md
*.json
```

**Update: 04/01/18**</br>We have completed enough Java files to be able to play the text based version of the Monopoly game in the terminal. We also included all of the JUnit test case files that we will need for the project. After building, the results from these tests can be viewed in HTML format by going to ```build\reports\tests\test``` and opening ```index.html``` in a web browser.

**Update: 04/20/18**</br>We have switched the project over to building using Maven instead of Gradle. We have also switched to using MySQL instead of PostgreSQL. These decisions were so that we could finish implementing the Account Creation functionality.

**Update: 05/03/18**</br>In the last update, we have completely implemented the login system. We also added almost all of the functionality of playing the game with multiple users playing on a single screen.

# 6. Needs Completion
The following is the list of things that still need to be completed:
- [x] Create account system through webpage
- [x] Log in system through webpage
- [x] Finish the mortgage(), trade() and nextTurn() methods in Board.java
- [x] Write out all of the test cases
- [x] Connect the GUI webpage interface with the Java backend game files
- [ ] Implement all gameplay functions through the GUI
- [x] Implement seasons based on turns
- [x] Implement seasonal pricing for properties
- [x] Finish documentation
- [ ] Add the support for multiple players on webpage
