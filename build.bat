@ECHO off

IF "%~1"=="" GOTO TEST
IF "%~1"=="-m" GOTO MAIN
IF "%~1"=="-t" GOTO TEXT

:TEST
	ECHO Building test...
	mvn test
	GOTO END

:MAIN
	ECHO Building main...
	mvn clean install
	GOTO END

:TEXT
	ECHO Building text...
	cd src/text
	javac -cp gson-2.8.2.jar com/newmonopoly/*.java
	java -cp .;gson-2.8.2.jar com/newmonopoly/Game
	GOTO END

:END