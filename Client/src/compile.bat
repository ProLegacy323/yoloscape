@echo off
Title Compiler
javac -Xlint:unchecked *.java
move /Y *.class ..\bin
pause