@echo off
Title Compiler
javac -Xlint:unchecked *.java
move /Y *.class ..\bin
move /Y "sign\*.class" "..\bin\sign"
move /Y "util\*class" "..\bin\util"
pause