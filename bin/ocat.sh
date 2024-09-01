#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: script.sh [-v | --version] [additional arguments...]"
    exit 1
fi

if [ "$1" == "-v" ]; then
    echo "0.1.1"
    exit 0
elif [ "$1" == "--version" ]; then
    echo "0.0.2"
    exit 0
else
    if [ ! -f "/C/Orange\ Cat/Orange-cat-tools/lib/Ocat.jar" ]; then
        echo "Error: Ocat.jar not found at /C/Orange Cat/Orange-cat-tools/lib/Ocat.jar."
        exit 1
    fi
    java -jar "/C/Orange Cat/Orange-cat-tools/lib/Ocat.jar" "$@"
fi
