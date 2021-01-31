#!/bin/bash

set -e


URL="https://downloads.apache.org//jmeter/binaries/$JMETER_FILE.tgz"
if [ ! -d  ~/.jmeter/jmeter ]
then
   mkdir -p ~/.jmeter/jmeter
   echo "Creating folder .jmeter"
else
   echo "Found folder .jmeter"
fi

if [ -f  ~/.jmeter/$JMETER_FILE.txt ]
then
    echo "Using from cache"
else
    curl $URL --output  ~/.jmeter/$JMETER_FILE.tgz
    tar -xzf ~/.jmeter/$JMETER_FILE.tgz   -C ~/.jmeter/
    mv  ~/.jmeter/$JMETER_FILE ~/.jmeter/jmeter
    ls -la $JMETER_HOME
    echo "update" >  ~/.jmeter/$JMETER_FILE.txt
fi