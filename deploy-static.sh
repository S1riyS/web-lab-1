#!/bin/bash

echo 'Uploading static...'
scp -r static/* itmo:~/web/lab1/static
