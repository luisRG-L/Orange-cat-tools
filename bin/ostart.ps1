$ENV_DIR = ""
$PROJECT_NAME = ""

$index = 0
while ($index -lt $args.Count) {
    switch ($args[$index]) {
        "-e" {
            $ENV_DIR = $args[$index + 1]
            $index += 2
        }
        "-p" {
            $PROJECT_NAME = $args[$index + 1]
            $index += 2
        }
        "-s" {
            $ENV_DIR = $args[$index + 1]
            $index += 2
        }
        "-v" {
            Write-Host "0.1.1"
            $index++
        }
        "--version" {
            Write-Host "0.1.1"
            $index++
        }
        default {
            Write-Host "Invalid option: $($args[$index])"
            Write-Host "Usage: ostart [-e <env_directory>] [-p <project_name>] [-s <env_directory>]"
            exit 1
        }
    }
}

if ($PROJECT_NAME) {
    if (-not (Test-Path $PROJECT_NAME)) {
        Write-Host "Creating project structure: $PROJECT_NAME"
        New-Item -ItemType Directory -Path "$PROJECT_NAME\src"
        New-Item -ItemType File -Path "$PROJECT_NAME\src\main.ocat"
        New-Item -ItemType Directory -Path "$PROJECT_NAME\lib"
        New-Item -ItemType Directory -Path "$PROJECT_NAME\config"
        New-Item -ItemType File -Path "$PROJECT_NAME\config\libs.olib"
        New-Item -ItemType Directory -Path "$PROJECT_NAME\docs"
        New-Item -ItemType File -Path "$PROJECT_NAME\docs\README.odoc"
        New-Item -ItemType File -Path "$PROJECT_NAME\docs\style.oss"
    } else {
        Write-Host "Project already exists: $PROJECT_NAME"
    }
}

if ($ENV_DIR) {
    if (-not (Test-Path $ENV_DIR)) {
        Write-Host "Creating virtual environment: $ENV_DIR"
        New-Item -ItemType Directory -Path "$ENV_DIR\projects"
        New-Item -ItemType Directory -Path "$ENV_DIR\bin"
        New-Item -ItemType Directory -Path "$ENV_DIR\locals\libs"
        New-Item -ItemType Directory -Path "$ENV_DIR\locals\include"
        New-Item -ItemType File -Path "$ENV_DIR\locals\include\includes.oml"
    } else {
        Write-Host "Directory already exists: $ENV_DIR"
    }
}
