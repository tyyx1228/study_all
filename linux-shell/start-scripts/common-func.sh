#!/usr/bin/env bash

#批量启动集群中的程序
#$1: 节点名称，格式： "salve1 slave2 slave3"
#$2: 节点运行的表示唯一进程的名称
#$3: 用于执行程序的命令
start_slaves_process(){
    SLAVES_NAME="$1"
    SLAVES_PROCESS="$2"
    START_COMMAND="$3"
    VALID_COMMAND="ps -ef | grep $SLAVES_PROCESS | grep -v grep"

    for SLAVE in $SLAVES_NAME
    do
        VALID=`ssh $SLAVE $VALID_COMMAND`
        if [ "$VALID" == "" ]
        then
            ssh $SLAVE "$START_COMMAND"
            RES=`ssh $SLAVE $VALID_COMMAND`
            if [ "$RES" != "" ]
            then
                echo "$SLAVE start $SLAVES_PROCESS success"
            else
                echo "$SLAVE start $SLAVES_PROCESS failed"
            fi
        else
            echo "$SLAVE process $SLAVES_PROCESS already started"
        fi
    done
}

#批量杀死集群中的程序
#$1: 节点名称，格式： "salve1 slave2 slave3"
#$2: 节点运行的表示唯一进程的名称
kill_slaves_process(){
    SLAVES_NAME="$1"
    SLAVES_PROCESS="$2"
    VALID_COMMAND="ps -ef | grep $SLAVES_PROCESS | grep -v grep"
    for SLAVE in $SLAVES_NAME
    do
        VALID=`ssh $SLAVE "$VALID_COMMAND"`
        if [ "$VALID" != "" ]
        then
            ssh $SLAVE "$VALID_COMMAND | cut -c 9-15 | xargs kill -s 9"
            RES=`ssh $SLAVE "$VALID_COMMAND"`
            if [ "$RES" == "" ]
            then
                echo "Slave: $SLAVE stop $SLAVES_PROCESS success"
            else
                echo "Slave: $SLAVE stop $SLAVES_PROCESS failed"
            fi
        else
            echo "Slave: $SLAVE process $SLAVES_PROCESS unrecognized"
        fi
    done

}