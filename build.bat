@ECHO off

IF "%~1"=="" GOTO TEST
IF "%~1"=="-m" GOTO MAIN
IF "%~1"=="-t" GOTO TEXT
IF "%~1"=="-s" GOTO SITE
IF "%~1"=="-d" GOTO DOC
IF "%~1"=="-ds" GOTO DOCSITE

:TEST
	ECHO Building test...
	mvn clean
	mvn test
	GOTO END

:MAIN
	ECHO Building main...
	mvn clean install
	GOTO END

:TEXT
	ECHO Building text...
	CD src/text
	DEL com/newmonopoly/*.class
	javac -cp gson-2.8.2.jar com/newmonopoly/*.java
	java -cp .;gson-2.8.2.jar com/newmonopoly/Game
	GOTO END

:SITE
	ECHO Building javadoc site...
	mvn site
	GOTO END

:DOC
	ECHO Building docs directory...
	mvn javadoc:javadoc
	GOTO END

:DOCSITE
	ECHO BUilding javadoc site and docs directory...
	mvn clean
	mvn site
	mvn javadoc:javadoc
	GOTO END

:END