
--电厂信息表
drop table if exists ods.xip_pub_orgs;
create table if not exists ods.xip_pub_orgs(
	ORG_ID	VARCHAR(36)	comment	'组织ID',
	ORG_CODE	VARCHAR(100)	comment	'组织编码',
	ORG_NAME	VARCHAR(200)	comment	'组织名称',
	ORG_SHORT_NAME	VARCHAR(200)	comment	'组织简称',
	UP_ORG_ID	VARCHAR(36)	comment	'上级组织',
	ORG_TYPE	VARCHAR(20)	comment	'类型：O，实体组织；V，虚拟组织',
	ENABLE_FLAG	VARCHAR(1)	comment	'启用标记：Y，启用；N，禁用',
	ORG_NUM	VARCHAR(36)	comment	'期数（一期、二期、三期）',
	UNIT_TYPE	VARCHAR(36)	comment	'机组类型(空冷、水冷)',
	UNIT_POWER	VARCHAR(36)	comment	'机组额定功率',
	ATTRIBUTE4	bigint	comment	'组织排序号',
	ORG_CLASS	VARCHAR(36)	comment	'电厂种类（1、火电   2、风电   3、水电   4、光伏）',
	ATTRIBUTE6	VARCHAR(250)	comment	'1-燃煤 2-燃气 3-风力 4-水力 5-光伏',
	ATTRIBUTE7	VARCHAR(250)	comment	'弹性扩展字段7',
	ATTRIBUTE8	VARCHAR(250)	comment	'弹性扩展字段8',
	ATTRIBUTE9	VARCHAR(250)	comment	'弹性扩展字段9',
	ATTRIBUTE10	VARCHAR(250)	comment	'弹性扩展字段10',
	CREATION_DATE	TIMESTAMP	comment	'创建日期',
	CREATED_BY	VARCHAR(36)	comment	'创建人',
	LAST_UPDATE_DATE	TIMESTAMP	comment	'最后更新日期',
	LAST_UPDATED_BY	VARCHAR(36)	comment	'最后更新人',
	LAST_UPDATE_LOGIN	VARCHAR(36)	comment	'最后更新登陆ID',
	ATTRIBUTE1	VARCHAR(250)	comment	'排序',
	ATTRIBUTE2	VARCHAR(250)	comment	'组织管理用UP_ID(可为NULL,别动)',
	ATTRIBUTE3	VARCHAR(250)	comment	'null',
	ATTRIBUTE5	VARCHAR(250)	comment	'null',
	ORG_CODES	VARCHAR(20)	comment	'电厂简称',
	STATUS	VARCHAR(10)	comment	'status：1表示该电厂系统已上线',
	ORDER_ORG	VARCHAR(10)	comment	'排序字段'
)comment '电厂信息'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;