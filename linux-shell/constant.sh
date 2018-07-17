#!/bin/bash

# 该参数接受以下可选值
# base_download             基于从ftp远程下载脚本执行
# base_local                 基于远程服务器自带的脚本执行
EXEC_MODE=base_download

#每个配置项内部分隔符
CONFIG_LINE_SPLIT_BY=:
CONFIG_PATH=$SCRIPTS_HOME/process-key-scripts.txt


HADOOP_HOME=/usr/local/bigdata/hadoop-2.6.5
SPARK_HOME=
HIVE_HOME=


SCRIPTS_PROJECT_NAME=shell_project
FTP_SERVER=http://10.10.1.60/$SCRIPTS_PROJECT_NAME