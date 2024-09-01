#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: script.sh [-v | --version]"
    exit 1
fi

if [ "$1" == "-v" ] || [ "$1" == "--version" ]; then
    echo "0.0.1"
else
    echo "Invalid argument: $1"
    echo "Usage: script.sh [-v | --version]"
    exit 1
fi
