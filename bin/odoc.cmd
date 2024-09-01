@echo off
setlocal 

if "%~1"=="" (
    echo Usage: script.bat [-v | --version] [other arguments...]
    exit /b 1
)

if "%~1"=="-v" (
    echo 0.1.1
) else if "%~1"=="--version" (
    echo 0.1.1
) else (
    if not exist "C:\Orange Cat\Orange-cat-tools\lib\OdocToHtml.jar" (
        echo Error: OdocToHtml.jar not found.
        exit /b 1
    )
    java -jar "C:\Orange Cat\Orange-cat-tools\lib\OdocToHtml.jar" %*
)

endlocal
