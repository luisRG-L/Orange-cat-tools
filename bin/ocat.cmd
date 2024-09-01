@echo off
setlocal

if "%~1"=="" (
    echo Usage: ocat [-v | --version | file path] [additional arguments...]
    exit /b 1
)

if "%~1"=="-v" (
    echo 0.1.1
    exit /b 0
) else if "%~1"=="--version" (
    echo 0.0.2
    exit /b 0
) else (
    if not exist "C:\Orange Cat\Orange-cat-tools\lib\Ocat.jar" (
        echo Error: Ocat.jar not found at C:\Orange Cat\Orange-cat-tools\lib\Ocat.jar.
        exit /b 1
    )

    java -jar "C:\Orange Cat\Orange-cat-tools\lib\Ocat.jar" %*
)
endlocal
