@echo off
setlocal

:: Verifica si se ha proporcionado un argumento
if "%~1"=="" (
    echo Usage: script.bat [-v | --version] [additional arguments...]
    exit /b 1
)

:: Verifica la versión
if "%~1"=="-v" (
    echo 0.0.2
    exit /b 0
) else if "%~1"=="--version" (
    echo 0.0.2
    exit /b 0
) else (
    :: Verifica si el archivo JAR existe
    if not exist "C:\Orange Cat\Orange-cat-tools\lib\Ocat.jar" (
        echo Error: Ocat.jar not found at C:\Orange Cat\Orange-cat-tools\lib\Ocat.jar.
        exit /b 1
    )

    :: Ejecuta el archivo JAR con argumentos adicionales
    java -jar "C:\Orange Cat\Orange-cat-tools\lib\Ocat.jar" %*
)
endlocal
