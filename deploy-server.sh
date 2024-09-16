#!/bin/bash

echo 'Building and uploading server jar...'
./gradlew build && scp app/build/libs/app.jar itmo:~/web/lab1/httpd-root/fcgi-bin