#!/bin/bash
SQOOP_HOME=/usr/local/bigdata/sqoop-1.4.7.bin__hadoop-2.6.0
HADOOP_HOME=/usr/local/bigdata/hadoop-2.6.4


TABLE_NAME=BAOJING_INFO_SC
targetDir=/sqoop-import/oracle/$TABLE_NAME

$HADOOP_HOME/bin/hadoop fs -rm -r -f $targetDir

#SPLIT_COLUMN=ID

$SQOOP_HOME/bin/sqoop import \
-Doracle.sessionTimeZone=Asia/Shanghai \
-Dorg.apache.sqoop.splitter.allow_text_splitter=true \
--connect jdbc:oracle:thin:@172.168.100.111:1521:orcl \
--username jsjd \
--password jnjtjsjd \
--table $TABLE_NAME \
--target-dir $targetDir \
--num-mappers 1 \
--null-string '\\N' \
--null-non-string '\\N' \
--fields-terminated-by '\t' 
#--split-by $SPLIT_COLUMN \




