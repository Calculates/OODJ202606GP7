@echo off
REM Hospital Management System Run Script for Windows

echo.
echo ===================================
echo Hospital Management System - Run
echo ===================================
echo.

REM Check if bin directory exists
if not exist bin (
    echo ERROR: bin directory not found. Please run build.bat first!
    pause
    exit /b 1
)

REM Run the application
echo Starting Hospital Management System...
echo.
java -cp bin com.apu.hms.HospitalManagementSystem

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Application failed to start!
    echo Make sure you have Java installed and build.bat was executed successfully.
    pause
    exit /b 1
)
