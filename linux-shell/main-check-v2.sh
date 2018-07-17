#!/bin/bash

## 本脚本初衷是用于定时执行以检查各个服务进程是否还在运行；
## 同时进程挂掉后，可自动重启以保障各服务进程意外退出或被误杀。
## 须使用linux crontab配合


## VERSION-2: 支持各程序启动脚本和程序包都在同一个服务器上或同一个集群服务器中
#            支持远程下载脚本，然后远程启动，之后在远程删掉启动脚本。本方式不会在远程残留脚本文件
# 脚本用于检测各任务进程是否还存在，若不存在则将其启动。
# 支持的任务类型有：
#   1.运行在linux上的进程
#   2.运行在yarn上的进程
#   3.运行在stort上的进程
#   4.运行在spark（standalone）上的进程


## 运行环境
#   1. 服务器之间已经配置好免密登陆
#   2. 需要配置局域网内的ftp服务器，支持下载文件。如：linux上的httpd服务管理文件


## 注意事项
#   1. 脚本中使用的keyword为程序运行时的全名，请一定确保keyword只对应一个唯一进程。
#   2. 各启动脚本可放置于当前目录下的start-scripts中


SCRIPTS_HOME=.

. $SCRIPTS_HOME/linux-process-tool.sh
. $SCRIPTS_HOME/yarn-process-tool.sh
. $SCRIPTS_HOME/constant.sh


LOG_PATH=$SCRIPTS_HOME/check.out



# 远程下载、执行和执行完后删除脚本
# $1: 远程服务器IP或HASTNAME
# $2: 脚本文件名（带路径，base_local模式下为绝对路径，根路径为'/'；base_download下为相对路径，相对）
# $3: 执行脚本时的输入参数列表，各参数以逗号分隔
exec_remote(){
    #将参数中的逗号分隔符替换为空格
    input_params=${3//,/ }
    if [ "base_local" == $EXEC_MODE ]
    then
        ssh $1  ". /etc/profile; sh $2 $input_params"
    elif [ "base_download" == $EXEC_MODE ]
    then
        script_name=''
        parent_dir=''
        if [ $2 == ${2%/*} ]
        then
            parent_dir=$SCRIPTS_PROJECT_NAME
            script_name=$2
        else
            parent_dir=$SCRIPTS_PROJECT_NAME/${2%/*}
            script_name=${2##*/}
        fi

        #-r -np -nH ; find start-scripts -name "index.html*" | xargs rm -rf
        echo $script_name =-=-
        echo $parent_dir -=-=
        ssh $1 ". /etc/profile; \
            cd ~ ; \
            wget $FTP_SERVER -r -np -nH ; \
            find $SCRIPTS_PROJECT_NAME -name 'index.html*' | xargs rm -rf; \
            cd ~/$parent_dir; \
            sh $script_name $input_params; \
            cd ~ ; \
            rm -rf $SCRIPTS_PROJECT_NAME"
    fi
}


for line in `cat $CONFIG_PATH`
do
    CHECK_TIME=`date "+%Y-%m-%d %H:%M:%S"`
    # 解析配置
    process_type=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 1`
    keyword=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 2`
    exec_host=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 3`
    start_script_path=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 4`
    params=`echo $line | cut -d "$CONFIG_LINE_SPLIT_BY" -f 5`

    echo $CHECK_TIME' [stdout] checking config: '$process_type $keyword $exec_host $start_script_path $params
    if [ "linux" == $process_type ]
    then
        res_num=`linuxGetProcessNumByKeywrod $keyword`
        if [ $res_num -ge 1 ]
        then
            echo $CHECK_TIME' [stdout] '$keyword': 当前实例正在运行，运行实例数量为：'$res_num #>>$LOG_PATH 2>&1
        else
            echo $CHECK_TIME' [stdout] '$keyword': 当前LINUX进程中找不到运行实例，正在启动 ... ' #>> $LOG_PATH
            #todo 可增加下载启支脚本支持，这样做可以将所有启动脚本都在一个服务器上管理-------先下载后执行最后
#            ssh $exec_host ". /etc/profile; sh $start_script_path $input_params"
            exec_remote $exec_host $start_script_path $params
        fi
    elif [ "yarn" == $process_type ]
    then
        res_num=`yarnGetProcessNumByKeywrod $keyword`
        if [ $res_num -ge 1 ]
        then
            echo $CHECK_TIME' [stdout] '$keyword': 当前实例正在yarn中运行，运行实例数量为：'$res_num #>> $LOG_PATH
        else
            echo $CHECK_TIME' [stdout] '$keyword': 当前yarn任务队列中找不到运行实例，正在启动 ... ' #>> $LOG_PATH
#            ssh $exec_host  ". /etc/profile; sh $start_script_path $input_params"
            exec_remote $exec_host $start_script_path $params
        fi
    elif [ "spark" == $process_type ]
    then
        # todo
        echo spark...
       # exec_remote $exec_host $start_script_path $params
    elif [ "storm" == $process_type ]
    then
        # todo
        echo storm start script TO_DO
    else
        echo missing or end ...
    fi
done
