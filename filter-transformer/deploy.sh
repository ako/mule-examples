#!/bin/bash
export MULE_HOME=/home/akoelewijn/programs/mule-standalone-3.2.0
export APP_HOME=$MULE_HOME/apps/filter-transformer
mkdir -p $APP_HOME
mkdir -p $APP_HOME/classes
cp mule-config.xml $APP_HOME/mule-config.xml
cp log4j.properties $APP_HOME/classes/log4j.properties
