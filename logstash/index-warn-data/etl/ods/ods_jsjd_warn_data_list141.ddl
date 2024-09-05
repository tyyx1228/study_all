drop table if exists ods.ods_jsjd_warn_data_list_141;
create table if not exists ods.ods_jsjd_warn_data_list_141(
	W_ID				VARCHAR(36) 	comment		'条目ID',
	P_I_ID				VARCHAR(36)		comment		'测点ID',
	KKS_CODE			VARCHAR(36)		comment		'KKScode',
	ORG_ID				VARCHAR(36)		comment		'电厂id',
	W_DATE				TIMESTAMP		comment		'开始时间',
	W_LEVEL				VARCHAR(10)		comment		'报警级别(1 班组 2 部门 3 电厂 4 集团)',
	W_VALUE				VARCHAR(50)		comment		'报警值',
	CREATE_BY			VARCHAR(36)		comment		'创建人',
	CREATE_DATE			TIMESTAMP		comment		'创建时间',
	LAST_UPDATED_BY		VARCHAR(36)		comment		'最后更新人',
	LAST_UPDATE_DATE	TIMESTAMP		comment		'最后更新时间',
	LAST_UPDATE_LOGIN	VARCHAR(36),
	W_TIME				VARCHAR(50)		comment		'报警时长',
	W_STATUS			VARCHAR(36)		comment		'报警状态(已消除，未消除)',
	W_COST				VARCHAR(10),
	UNIT_STATUS			VARCHAR(36)		comment		'机组状态(0停机，1未停机)',
	RID					BIGINT			comment		'行id',
	ENDTIME				TIMESTAMP		comment		'结束时间',
	START_TIME_NO		VARCHAR(60)		comment		'开始时间数',
	END_TIME_NO			VARCHAR(60)		comment		'结束时间数',
	W_TYPE				VARCHAR(10)		comment		'1为发送，null或者0为未发送',
	ATTRIBUTE12			VARCHAR(50)		comment		'备注',
	ALARM_TYPE			VARCHAR(50)		comment		'测点报警 0  超量程上限1 超量程下限2 变化速率异常3',
	ATTRIBUTE14			VARCHAR(50)		comment		'备用14',
	ATTRIBUTE15			VARCHAR(50)		comment		'备用15',
	ATTRIBUTE16			VARCHAR(50)		comment		'备用16',
	ATTRIBUTE17			VARCHAR(50)		comment		'备用17',
	ATTRIBUTE18			VARCHAR(50)		comment		'备用18',
	ATTRIBUTE19			VARCHAR(50)		comment		'备用19',
	ATTRIBUTE20			VARCHAR(50)		comment		'备用20',
	KAOHETAG			VARCHAR(1)		comment		'考核标记',
	BJTIMES				VARCHAR(50)		comment		'报警秒数',
	CLASS_VALUE			int				comment		'班值'
)comment '报警数据表'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;