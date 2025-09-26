@echo off
setlocal enabledelayedexpansion

:: Configuration
set OUT=out
set MAIN=Main
set SOURCES=*.java core\*.java utils\*.java
set JAR_NAME=UniversityApp.jar
set MANIFEST=manifest.txt

if "%1"=="" (
    echo Usage: build.bat [compile^|run^|clean^|jar^|all]
    exit /b 1
)

:: CLEAN
if "%1"=="clean" (
    if exist "%OUT%" rmdir /s /q "%OUT%"
    if exist "%JAR_NAME%" del "%JAR_NAME%"
    if exist "%MANIFEST%" del "%MANIFEST%"
    echo Cleaned build files.
    exit /b 0
)

:: COMPILE
if "%1"=="compile" (
    if not exist "%OUT%" mkdir "%OUT%"
    javac -d "%OUT%" %SOURCES%
    if errorlevel 1 (
        echo Compilation failed.
        exit /b 1
    )
    echo Compilation successful.
    exit /b 0
)

:: RUN
if "%1"=="run" (
    if not exist "%OUT%" (
        echo No compiled classes found. Run "build.bat compile" first.
        exit /b 1
    )
    java -cp "%OUT%" %MAIN%
    exit /b 0
)

:: JAR
if "%1"=="jar" (
    if not exist "%OUT%" (
        echo No compiled classes found. Run "build.bat compile" first.
        exit /b 1
    )
    echo Main-Class: %MAIN% > "%MANIFEST%"
    jar cfm "%JAR_NAME%" "%MANIFEST%" -C "%OUT%" .
    del "%MANIFEST%"
    echo Created %JAR_NAME%
    exit /b 0
)

:: ALL (clean + compile + run)
if "%1"=="all" (
    call %0 clean
    call %0 compile
    call %0 run
    exit /b 0
)

echo Invalid command: %1
echo Usage: build.bat [compile^|run^|clean^|jar^|all]
exit /b 1
