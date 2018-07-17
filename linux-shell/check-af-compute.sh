#!/bin/bash

HADOOP_HOME=/usr/local/bigdata/hadoop-2.6.4
PROCESS_KEYWORD=com.inbasis.realtime.streaming.AFRealtimeCalulatorPlus

START_SCRIPT_PATH=/root/shell/af-start-rtc.sh
LOG_PATH=/home/logs/af-realtime/check-af-compute-task.out

CHECK_TIME=`date "+%Y-%m-%d %H:%M:%S"`

process_exist(){
    $HADOOP_HOME/bin/yarn application -list | grep $1 | wc -l
}


result=`process_exist $PROCESS_KEYWORD`
if [ $result -ge 1 ]
then
    echo $CHECK_TIME': '$PROCESS_KEYWORD': 当前实例正在运行，运行实例数量为：'$result >> $LOG_PATH
else
    echo $CHECK_TIME': '$PROCESS_KEYWORD': 当前yarn任务队列中找不到运行实例，正在启动 ... ' >> $LOG_PATH
    sh $START_SCRIPT_PATH
fi


