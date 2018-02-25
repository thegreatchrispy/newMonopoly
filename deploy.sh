#!/bin/bash

TOMCAT_DIR=/Library/Tomcat
NEW_WAR_BASE_NAME=newMonopoly
PROJECT_DIR=/Users/isaacperalez/Documents/School/Project/newMonopoly

cd $TOMCAT_DIR
sh $TOMCAT_DIR/bin/shutdown.sh
wait

rm -f -r $TOMCAT_DIR/logs/
#rm -f $TOMCAT_DIR/webapps/$WAR_BASE_NAME
#rm -f $TOMCAT_DIR/webapps/$WAR_BASE_NAME.war
rm -f $TOMCAT_DIR/webapps/$NEW_WAR_BASE_NAME
rm -f $TOMCAT_DIR/webapps/$NEW_WAR_BASE_NAME.war

mkdir $TOMCAT_DIR/logs

cp $PROJECT_DIR/war/$NEW_WAR_BASE_NAME.war $TOMCAT_DIR/webapps/
# cd $TOMCAT_DIR/webapps
# mv $WAR_BASE_NAME.war $NEW_WAR_BASE_NAME.war
wait

sh $TOMCAT_DIR/bin/startup.sh
sh $TOMCAT_DIR/bin/shutdown.sh
#rm newMonopoly/WEB-INF/web.xml
cd $PROJECT_DIR
