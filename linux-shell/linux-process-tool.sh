#!/bin/bash

. /etc/profile

linuxGetProcessNumByKeywrod(){
    process_keyword=$1
    ps -ef | grep $process_keyword | grep -v grep | wc -l
}


linuxGetProcessIdByKeyword(){
    process_keyword=$1
    ps -ef | grep $ | grep -v grep | cut -c 9-15
}

linuxGetProcessIdByPort(){
    port=$1
    ps -aux | grep $port | grep -v grep | cut -c 9-15
}


