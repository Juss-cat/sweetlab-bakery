@echo off
REM Script para ejecutar Sweet Lab Bakery
REM =====================================

echo.
echo ╔════════════════════════════════════════╗
echo ║   🍰 SWEET LAB BAKERY - Sistema       ║
echo ║   Iniciando la aplicación...          ║
echo ╚════════════════════════════════════════╝
echo.

REM Cambiar al directorio del proyecto
cd /d "%~dp0"

REM Ejecutar Maven con JavaFX
call "C:\Users\pc\maven\bin\mvn.cmd" javafx:run

REM Si hay error, pausa para ver el mensaje
if errorlevel 1 (
    echo.
    echo ❌ Error al ejecutar la aplicación
    pause
    exit /b 1
)

echo.
echo ✅ Aplicación cerrada correctamente
pause
