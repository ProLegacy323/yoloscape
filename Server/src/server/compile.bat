@echo off
title Server Compiler
javac -Xlint:unchecked *.java
FOR /R "../server/" %%G in (.) DO (
	move /Y *.class "../../bin/%%G"
)
pause
