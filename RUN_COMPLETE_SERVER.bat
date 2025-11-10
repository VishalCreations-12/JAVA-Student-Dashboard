@echo off
cd /d "%~dp0"
echo ========================================
echo Student Admission Portal - Complete Server
echo ========================================
echo.

echo Stopping any existing servers on port 8080...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080 ^| findstr LISTENING') do (
    echo Killing process %%a
    taskkill /F /PID %%a >nul 2>&1
)
timeout /t 2 /nobreak >nul

echo.
echo Building project...
call mvn clean package -q

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Build failed! Please check errors above.
    pause
    exit /b 1
)

echo.
echo Starting server...
echo.
echo ========================================
echo Server will start in a few seconds...
echo ========================================
echo.
echo Once started, open your browser to:
echo http://localhost:8080/student-admission-portal/welcome.xhtml
echo.
echo Press Ctrl+C to stop the server
echo.

call mvn exec:java -Dexec.mainClass="com.studentportal.ServerRunner" -Dexec.classpathScope=runtime

pause

