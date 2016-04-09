#!/bin/bash 
mvn clean compile
mvn package
java -jar ./target/employees-app-1.0-SNAPSHOT-jar-with-dependencies.jar
