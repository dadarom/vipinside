#!/bin/sh

cd ..

# use maven-assembly-plugin
#mvn clean assembly:assembly -Pdev

# use maven-shade-plugin
mvn clean install -Pdev