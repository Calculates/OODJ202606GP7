@echo off
REM Hospital Management System Build Script for Windows

echo.
echo ===================================
echo Hospital Management System - Build
echo ===================================
echo.

REM Create bin directory
if not exist bin mkdir bin
echo [1/3] Created bin directory

REM Compile all Java files
echo [2/3] Compiling Java files...
javac -d bin -sourcepath src src/com/apu/hms/HospitalManagementSystem.java src/com/apu/hms/models/*.java src/com/apu/hms/views/*.java src/com/apu/hms/utils/*.java

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo [3/3] Compilation successful!
echo.
echo ===================================
echo Build Complete!
echo ===================================
echo.
echo To run the application, execute:
echo java -cp bin com.apu.hms.HospitalManagementSystem
echo.
pause
