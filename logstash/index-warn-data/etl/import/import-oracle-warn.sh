#!/bin/bash
SQOOP_HOME=/usr/local/bigdata/sqoop-1.4.7.bin__hadoop-2.6.0
HADOOP_HOME=/usr/local/bigdata/hadoop-2.6.4

TABLE_NAME=TES_BA_T_P_W_LIST
targetDir=/sqoop-import/oracle/$TABLE_NAME

$HADOOP_HOME/bin/hadoop fs -rm -r -f $targetDir

$SQOOP_HOME/bin/sqoop import \
-Doracle.sessionTimeZone=Asia/Shanghai \
--connect jdbc:oracle:thin:@172.168.100.111:1521:orcl \
--username jsjd \
--password jnjtjsjd \
--table TES_BA_T_P_W_LIST \
--target-dir $targetDir \
--num-mappers 20 \
--null-string '\\N' \
--null-non-string '\\N' \
--fields-terminated-by '\t'




