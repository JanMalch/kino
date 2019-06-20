|Build|Code Coverage|Code Quality|Dependencies|Bugs|
|--|--|--|--|--|
|[![Build Status](https://travis-ci.org/JanMalch/kino.svg?branch=master)](https://travis-ci.org/JanMalch/kino)|[![codecov](https://codecov.io/gh/JanMalch/kino/branch/master/graph/badge.svg)](https://codecov.io/gh/JanMalch/kino)|[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=JanMalch_kino&metric=alert_status)](https://sonarcloud.io/dashboard?id=JanMalch_kino)|[![Known Vulnerabilities](https://snyk.io/test/github/JanMalch/kino/badge.svg)](https://snyk.io/test/github/JanMalch/kino)|[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/JanMalch/kino.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/JanMalch/kino/context:java)

# Kino

A RESTful Cinema API built with JEE

## Running the server
### Build
Benötigt:
-	Maven 3.6.0 	(https://maven.apache.org/download.cgi)
-	Node 12.0	(https://nodejs.org/en/)
-	Java 11	(https://jdk.java.net/11/)
./kino>_ %MAVEN_ROOT% clean install 
### Deployment
Benötigt:
-	Java 			(https://jdk.java.net/11/)
-	Wildfly 16.0.0 Final 	(https://wildfly.org/downloads/)
-	MySql Datenbank	(https://www.apachefriends.org/de/index.html)
-	Executable Kino.war	(aus Build)
### Datenbank Setup
-	Nutzung der SQL Skripte im Ordner `sql-scripts`
-	Datenbankname: kino
-	Datenbank-Credential:
    - User: `jpa`
    - Passwort: `jpa`
  
## Dummy Login
-	**Admin-Account:**
    - User: `admin@account.de`
    - Passwort: `admin`
-	**Moderator-Account:**
    -	User: `admin@account.de`
    -	Passwort: `moderator`
-	**Customer-Account:**
    -	User: `customer@account.de`
    -	Passwort: `customer`
-	**Customer -Account:**
    -	User: `customer1@account.de`
    -	Passwort: `customer1`
