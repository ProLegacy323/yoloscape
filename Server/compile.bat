@echo off
title Project Insanity - Compiler
javac -Xlint:unchecked -d bin -cp ./deps/*; -sourcepath src src/server/ServerStart.java
pause
