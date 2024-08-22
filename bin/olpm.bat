@echo off
setlocal

:: Verifica si se ha proporcionado un argumento
if "%~1"=="" (
    echo Usage: script.bat [-v | --version]
    exit /b 1
)

:: Procesa el argumento
if "%~1"=="-v" (
    echo 0.0.1
) else if "%~1"=="--version" (
    echo 0.0.1
) else (
    echo Invalid argument: %~1
    echo Usage: script.bat [-v | --version]
    exit /b 1
)

endlocal
