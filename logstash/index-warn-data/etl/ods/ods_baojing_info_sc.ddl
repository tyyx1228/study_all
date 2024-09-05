drop table if exists ods.baojing_info_sc;
--创建测点报警受控信息表
create table if not exists ods.baojing_info_sc(
	ID	        VARCHAR(36),
	KKS_CODE	VARCHAR(36),
	KKS_NAME	VARCHAR(50),
	ORG_ID	    VARCHAR(36),
	G_ID	VARCHAR(10),
	STARTTIME	TIMESTAMP,
	ENDTIME	TIMESTAMP,
	W_LEVEL	VARCHAR(10),
	W_VALUE	VARCHAR(50),
	W_TIME	VARCHAR(60),
	BJTIMES	VARCHAR(50),
	W_STATUS	VARCHAR(36),
	UNIT_STATUS	VARCHAR(36),
	T_P_UNIT	VARCHAR(20),
	SPEC_ID	VARCHAR(20),
	RID	bigint,
	START_TIME_NO	VARCHAR(50),
	END_TIME_NO	VARCHAR(50),
	CLASS_VALUE	int,
	CONTROLLED_STATE	VARCHAR(10),
	PROCESSING_MODE	VARCHAR(20),
	PROCESSING_METHOD	VARCHAR(1000),
	ALARM_REASON	VARCHAR(1000),
	PLAN_ELIMINATE_DATE	TIMESTAMP,
	SUBMITTER	VARCHAR(40),
	SUBMIT_DATE	TIMESTAMP,
	CREATE_DATE	TIMESTAMP,
	ORG_NAME	VARCHAR(20),
	NOTE	VARCHAR(2000),
	PROCESSING_STATE	VARCHAR(30),
	BATCH	VARCHAR(36),
	ISMAIN	VARCHAR(10),
	SJXCRQ	TIMESTAMP,
	ISKAOHE	VARCHAR(10)
)comment '测点报警受控信息'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;