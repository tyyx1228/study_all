#!/bin/bash

## 本脚本初衷是用于定时执行以检查各个服务进程是否还在运行；
## 同时进程挂掉后，可自动从起以保障各服务进程意外退出或被误杀。

# VERSION-1: 支持各程序启动脚本和程序包都在同一个服务器上或同一个集群服务器中
# 脚本用于检测各任务进程是否还存在，若不存在则将其启动。
# 支持的任务类型有：
#   1.运行在linux上的进程
#   2.运行在yarn上的进程
#   3.运行在stort上的进程
#   4.运行在spark（standalone）上的进程


## 运行环境
#   1. 服务器之间已经配置好免密登陆


## 注意事项
#   1. 脚本中使用的keyword为程序运行时的全名，请一定确保keyword只对应一个唯一进程。


SCRIPTS_HOME=.

. $SCRIPTS_HOME/linux-process-tool.sh
. $SCRIPTS_HOME/yarn-process-tool.sh
. $SCRIPTS_HOME/constant.sh


LOG_PATH=$SCRIPTS_HOME/check.out

for line in `cat $CONFIG_PATH`
do
    CHECK_TIME=`date "+%Y-%m-%d %H:%M:%S"`
    # 解析配置
    process_type=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 1`
    keyword=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 2`
    exec_host=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 3`
    start_script_path=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 4`
    params=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 5`
    input_params=${params//,/ }

    echo $process_type $keyword $exec_host $start_script_path $params
    if [ "linux" == $process_type ]
    then
        res_num=`linuxGetProcessNumByKeywrod $keyword`
        if [ $res_num -ge 1 ]
        then
            echo $CHECK_TIME' [stdout] '$keyword': 当前实例正在运行，运行实例数量为：'$res_num #>>$LOG_PATH 2>&1
        else
            echo $CHECK_TIME' [stdout] '$keyword': 当前LINUX进程中找不到运行实例，正在启动 ... ' #>> $LOG_PATH
            #todo 可增加下载启支脚本支持，这样做可以将所有启动脚本都在一个服务器上管理-------先下载后执行最后
            ssh $exec_host ". /etc/profile; sh $start_script_path $input_params"
        fi
    elif [ "yarn" == $process_type ]
    then
        res_num=`yarnGetProcessNumByKeywrod $keyword`
        if [ $res_num -ge 1 ]
        then
            echo $CHECK_TIME' [stdout] '$keyword': 当前实例正在yarn中运行，运行实例数量为：'$res_num #>> $LOG_PATH
        else
            echo $CHECK_TIME' [stdout] '$keyword': 当前yarn任务队列中找不到运行实例，正在启动 ... ' #>> $LOG_PATH
            ssh $exec_host  ". /etc/profile; sh $start_script_path $input_params"
        fi
    elif [ "spark" == $process_type ]
    then
        # todo
        echo spark...
    elif [ "storm" == $process_type ]
    then
        # todo
        echo storm start script TO_DO
    else
        echo missing or end ...
    fi
done
