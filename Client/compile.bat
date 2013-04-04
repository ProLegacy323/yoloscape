@echo off
title Client - Compiler
javac -d bin -sourcepath src
move /Y src\*.class bin\
pause
