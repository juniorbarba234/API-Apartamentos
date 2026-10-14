@echo off
cd /d "%~dp0"
if not defined JAVA_HOME if exist "%ProgramFiles%\Java\latest\bin\java.exe" set "JAVA_HOME=%ProgramFiles%\Java\latest"
echo Iniciando API Apartamentos...
echo Deja esta ventana abierta mientras uses Postman.
call mvnw.cmd spring-boot:run
pause
