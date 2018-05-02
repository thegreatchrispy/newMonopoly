#!/bin/bash

#### Functions

usage() {
	echo "Usage: $0 [OPTIONS]"
}

#### Main
if [ $# -eq 0 ]; then
	echo "[TEXT VERSION BUILD]"
	cd src/text/
	javac -cp META-INF/lib/gson-2.8.2.jar com/newmonopoly/*.java -d META-INF/classes/
	jar cvfm NewMonopoly.jar META-INF/MANIFEST.MF META-INF/classes/* META-INF/lib/gson-2.8.2.jar
	java -cp NewMonopoly.jar com.newmonopoly.Game
	exit 0
fi

while [ "$1" != "" ]; do
	case $1 in
		-h | --help)
			echo "DESCRIPTION:"
			echo "    build script for NewMonopoly project"
			echo " "
			usage
			echo " "
			echo "OPTIONS:"
			echo "    -c, --clean"
			echo "          removes all files generated from building the project"
			echo "    -t, --test"
			echo "          builds only the test cases and files related to test cases"
			echo "    -m, --main"
			echo "          builds the entire project, including test cases"
			echo "    -s, --site"
			echo "          generates the documentation for the entire project, including dependencies"
			echo "    -d, --doc, --docs"
			echo "          generates the javadoc documentation only"
			echo "    -l, --launch"
			echo "          builds and deploys the project"
			echo " "
			exit 0
			;;
		-c | --clean)
			echo "[CLEAN]"
			mvn clean
			;;
		-t | --test)
			echo "[TEST BUILD]"
			mvn test
			;;
		-m | --main)
			echo "[MAIN BUILD]"
			mvn clean install
			;;
		-s | --site)
			echo "[SITE BUILD]"
			rm docs/*
			mvn site
			mv target/site/* docs/
			;;
		-d | --doc | --docs)
			echo "[DOC BUILD]"
			rm docs/*
			mvn javadoc:javadoc
			mv target/apidocs/* docs/
			;;
		-l | --launch)
			echo "[BUILD AND LAUNCH]"
			mvn clean install
			java -jar target/NewMonopoly-0.0.1-SNAPSHOT.jar
			;;
		*)
			usage
			echo "run 'build -h' for more info"
			exit 1
	esac
	shift
done

exit 0