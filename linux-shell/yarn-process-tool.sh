#!/bin/bash
SCRIPTS_HOME=.

. $SCRIPTS_HOME/constant.sh


yarnGetProcessNumByKeywrod(){
    $HADOOP_HOME/bin/yarn application -list | grep $1 | wc -l
}
