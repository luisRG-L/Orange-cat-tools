#!/bin/bash

ENV_DIR=""
PROJECT_NAME=""

while [ "$1" != "" ]; do
    case $1 in
        -e ) shift
             ENV_DIR=$1
             ;;
        -p ) shift
             PROJECT_NAME=$1
             ;;
        -s ) shift
             ENV_DIR=$1
             ;;
        -v | --version )
             echo "0.1.1"
             exit 0
             ;;
        * )
             echo "Invalid option: $1"
             echo "Usage: ostart [-e <env_directory>] [-p <project_name>] [-s <env_directory>]"
             exit 1
    esac
    shift
done

if [ "$PROJECT_NAME" != "" ]; then
    if [ ! -d "$PROJECT_NAME" ]; then
        echo "Creating project structure: $PROJECT_NAME"
        mkdir -p "$PROJECT_NAME/src"
        touch "$PROJECT_NAME/src/main.ocat"
        mkdir -p "$PROJECT_NAME/lib"
        mkdir -p "$PROJECT_NAME/config"
        touch "$PROJECT_NAME/config/libs.olib"
        mkdir -p "$PROJECT_NAME/docs"
        touch "$PROJECT_NAME/docs/README.odoc"
        touch "$PROJECT_NAME/docs/style.oss"
    else
        echo "Project already exists: $PROJECT_NAME"
    fi
fi

if [ "$ENV_DIR" != "" ]; then
    if [ ! -d "$ENV_DIR" ]; then
        echo "Creating virtual environment: $ENV_DIR"
        mkdir -p "$ENV_DIR/projects"
        mkdir -p "$ENV_DIR/bin"
        mkdir -p "$ENV_DIR/locals/libs"
        mkdir -p "$ENV_DIR/locals/include"
        touch "$ENV_DIR/locals/include/includes.oml"
    else
        echo "Directory already exists: $ENV_DIR"
    fi
fi
