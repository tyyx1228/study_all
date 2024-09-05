#!/bin/bash

echo 启动rtc... $1 $2

jps -l | cat | while read line
do
    echo $line
done