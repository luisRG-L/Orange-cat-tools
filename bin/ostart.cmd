@echo off
setlocal

:: Inicializa las variables
set "ENV_DIR="
set "PROJECT_NAME="

:: Procesa los argumentos
:parse_args
if "%~1"=="" goto end_args
if "%~1"=="-e" (
    set "ENV_DIR=%~2"
    shift
    shift
) else if "%~1"=="-p" (
    set "PROJECT_NAME=%~2"
    shift
    shift
) else if "%~1"=="-s" (
    set "ENV_DIR=%~2"
    shift
    shift
) else if "%~1"=="-v" (
    echo 0.1.1
    shift
) else if "%~1"=="--version" (
    echo 0.1.1
    shift
) else (
    echo Invalid option: %~1
    echo Usage: ostart [-e <env_directory>] [-p <project_name>] [-s <env_directory>]
    exit /b 1
)
goto parse_args

:end_args

:: Verifica si se ha especificado un nombre de proyecto
if not "%PROJECT_NAME%"=="" (
    :: Crea la estructura del proyecto
    if not exist "%PROJECT_NAME%" (
        echo Creating project structure: %PROJECT_NAME%
        mkdir "%PROJECT_NAME%"
        mkdir "%PROJECT_NAME%\src"
        echo. > "%PROJECT_NAME%\src\main.ocat"
        mkdir "%PROJECT_NAME%\lib"
        mkdir "%PROJECT_NAME%\config"
        echo. > "%PROJECT_NAME%\config\libs.olib"
        mkdir "%PROJECT_NAME%\docs"
        echo. > "%PROJECT_NAME%\docs\README.odoc"
        echo. > "%PROJECT_NAME%\docs\style.oss"
    ) else (
        echo Project already exists: %PROJECT_NAME%
    )
)

:: Verifica si se ha especificado un directorio de entorno y créalo si no existe
if not "%ENV_DIR%"=="" (
    if not exist "%ENV_DIR%" (
        echo Creating virtual environment: %ENV_DIR%
        mkdir "%ENV_DIR%"
        cd "%ENV_DIR%"
        mkdir "projects"
        mkdir "bin"
        mkdir "locals"
        cd "locals"
        mkdir "libs"
        mkdir "include"
        cd "include"
        echo. > "includes.oml"
        cd ..\..
    ) else (
        echo Directory already exists: %ENV_DIR%
    )
)

endlocal
