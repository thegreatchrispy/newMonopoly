@echo off
SET TOMCAT_DIR=C:\path\to\tomcat\directory
SET NEW_WAR_BASE_NAME=newMonopoly
SET PROJECT_DIR=C:\path\to\project\directory

CD %TOMCAT_DIR%
CALL %TOMCAT_DIR%\bin\shutdown.bat
PAUSE

DEL /F /Q %TOMCAT_DIR%\logs\
DEL /F /Q %TOMCAT_DIR%\webapps\%WAR_BASE_NAME%
DEL /F /Q %TOMCAT_DIR%\webapps\%WAR_BASE_NAME%.war
DEL /F /Q %TOMCAT_DIR%\webapps\%NEW_WAR_BASE_NAME%
DEL /F /Q %TOMCAT_DIR%\webapps\%NEW_WAR_BASE_NAME%.war

COPY %PROJECT_DIR%\war\%NEW_WAR_BASE_NAME%.war %TOMCAT_DIR%\webapps\
CD %TOMCAT_DIR%\webapps
REN %WAR_BASE_NAME%.war %NEW_WAR_BASE_NAME%.war
PAUSE

CALL %TOMCAT_DIR%\bin\startup.bat
CALL %TOMCAT_DIR%\bin\shutdown.bat
DEL newMonopoly\WEB-INF\web.xml
CD %PROJECT_DIR%
