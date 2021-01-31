#!/bin/bash

set -e


URL="https://downloads.apache.org//jmeter/binaries/$JMETER_FILE.tgz"
if [ ! -d  ~/.jmeter ]
then
   mkdir -pv ~/.jmeter
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
    ls -la ~/.jmeter
    mv  -v ~/.jmeter/$JMETER_FILE ~/.jmeter/jmeter
    rm -rf ~/.jmeter/jmeter/docs
    rm -rf ~/.jmeter/jmeter/printable_docs
    rm -rf ~/.jmeter/jmeter/licenses
    rm -rf ~/.jmeter/$JMETER_FILE.tgz
    ls -la ~/.jmeter/jmeter
    echo "update" >  ~/.jmeter/$JMETER_FILE.txt
fi