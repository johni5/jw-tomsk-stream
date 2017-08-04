@echo off

set JAVA_HOME=C:/Program Files/Java/jdk1.8.0_111

rem cmd /C C:\tools\red5-server\red5-shutdown.bat
cmd /C mvn clean package -P red5
rmdir /S /Q C:\tools\red5-server\webapps\jws
copy target\jws-0.1-alpha.war C:\tools\red5-server\webapps
cd C:\tools\red5-server
start cmd /C red5.bat
