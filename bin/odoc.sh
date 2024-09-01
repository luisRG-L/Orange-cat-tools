#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: script.sh [-v | --version] [other arguments...]"
    exit 1
fi

if [ "$1" == "-v" ] || [ "$1" == "--version" ]; then
    echo "0.1.1"
else
    if [ ! -f "/C/Orange\ Cat/Orange-cat-tools/lib/OdocToHtml.jar" ]; then
        echo "Error: OdocToHtml.jar not found."
        exit 1
    fi
    java -jar "/C/Orange Cat/Orange-cat-tools/lib/OdocToHtml.jar" "$@"
fi
``
