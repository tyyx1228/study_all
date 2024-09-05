#!/bin/bash
SQOOP_HOME=/usr/local/bigdata/sqoop-1.4.7.bin__hadoop-2.6.0
HADOOP_HOME=/usr/local/bigdata/hadoop-2.6.4

TABLE_NAME=TES_BA_POINTS_INDEX
SPLIT_COLUMN=P_I_ID
targetDir=/sqoop-import/oracle/$TABLE_NAME

$HADOOP_HOME/bin/hadoop fs -rm -r -f $targetDir



$SQOOP_HOME/bin/sqoop import \
-Doracle.sessionTimeZone=Asia/Shanghai \
-Dorg.apache.sqoop.splitter.allow_text_splitter=true \
--connect jdbc:oracle:thin:@172.168.100.111:1521:orcl \
--username jsjd \
--password jnjtjsjd \
--table $TABLE_NAME \
--split-by $SPLIT_COLUMN \
--columns P_I_ID,UP_P_I_ID,APP_ID,KKS_CODE,T_P_CODE,T_P_NAME,SPEC_ID,T_P_COMP,DCS_NO,P_DATA_LEVEL,ORG_ID,T_P_UNIT,T_P_ORIGIN,AID_KKS_CODE,AID_PARAMS,RANGE_UP,RANGE_DOWN,W_VALUE_LOW,W_VALUE_HIGH,W_LEVEL,CALC_METHOD,W_ASSESS,W_DISCERN,IS_POINTS,CREATE_BY,CREATE_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE,LAST_UPDATE_LOGIN,PI_CODE,PRECENT,ATTRIBUTE5,ATTRIBUTE6,ATTRIBUTE7,ATTRIBUTE8,ATTRIBUTE9,ATTRIBUTE10,ATTRIBUTE11,ATTRIBUTE12,ATTRIBUTE13,ATTRIBUTE14,ATTRIBUTE15,ATTRIBUTE16,ATTRIBUTE17,ATTRIBUTE18,ATTRIBUTE19,SYSTEM_TYPE,G_ID,PIKEY,BZ_WARN,DC_WARN,BM_WARN,JT_WARN,BJ_SYSTEM,BZ_ENDVALUE,DC_ENDVALUE,BM_ENDVALUE,JT_ENDVALUE,RATE_RANGE,RATE_CIRCLE \
--target-dir $targetDir \
--num-mappers 5 \
--null-string '\\N' \
--null-non-string '\\N' \
--fields-terminated-by '\t' 



#--query 'select a.* from (select t.*, rownum num FROM TES_BA_POINTS_INDEX_NEW t) a where $CONDITIONS' \
#--table $TABLE_NAME \
#--split-by $SPLIT_COLUMN \
#-Dorg.apache.sqoop.splitter.allow_text_splitter=true \




