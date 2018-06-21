
#创建名称空间
create_namespace 'NS_SY'

#创建测点表
create 'NS_SY:H_POINT', 'POINT_INFO', SPLITS => ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']
alter 'NS_SY:H_POINT', {NAME => 'POINT_INFO', VERSIONS => 1, BLOOMFILTER => 'ROWCOL', TTL => 15552000, BLOOMFILTER=>'ROWCOL', COMPRESSION => 'GZ'}
alter 'NS_SY:H_POINT', 'coprocessor'=>'hdfs://inbasis01:9000/hbase-coprocessor/h_point_coprocessor-1.0-SNAPSHOT.jar|com.inbasis.hbase.coprocessor.HPointNameIndexCoprocessor|1001|'
#alter 'NS_SY:H_POINT', 'coprocessor'=>'hdfs://10.10.1.60:9000/hbase-coprocessor/h_point_coprocessor-1.0-SNAPSHOT.jar|com.ty.study.coprocessor.HPointNameIndexCoprocessor|1001|'


#删除协处理器，$1为第一个协处理器
alter 'NS_SY:H_POINT', METHOD => 'table_att_unset', NAME => 'coprocessor$1'


#删除表
disable 'NS_SY:H_POINT'
drop 'NS_SY:H_POINT'

disable 'NS_SY:H_POINT_NAME_INDEX'
drop 'NS_SY:H_POINT_NAME_INDEX'


#创建测点名称索引表
create 'NS_SY:H_POINT_NAME_INDEX', 'POINT_INFO', SPLITS => ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31']
alter 'NS_SY:H_POINT_NAME_INDEX', {NAME => 'POINT_INFO', VERSIONS => 1, BLOOMFILTER => 'ROWCOL', TTL => 15552000, BLOOMFILTER=>'ROWCOL', COMPRESSION => 'GZ'}



测试数据
put 'NS_SY:H_POINT', '01201803021205001234567890', 'POINT_INFO:NAME', 'ABCD'
put 'NS_SY:H_POINT', '00798194748344745660304670', 'POINT_INFO:NAME', 'NMDH:S70AN001CT'
put 'NS_SY:H_POINT', '00798194748344745660304669', 'POINT_INFO:NAME', 'NMDH:S70AN001CT002'



scan 'NS_SY:H_POINT', {LIMIT => 10}
scan 'NS_SY:H_POINT_NAME_INDEX', {LIMIT => 10}


#truncate命令将清除所有region和所有数据，命令执行完成后region个数为1
truncate 'NS_SY:H_POINT'
truncate 'NS_SY:H_POINT_NAME_INDEX'


#truncate命令将清除所有数据，region数量保留
truncate_preserve 'NS_SY:H_POINT'
truncate_preserve 'NS_SY:H_POINT_NAME_INDEX'



#创建报警事件表
create 'NS_SY:H_EVENT', {NAME => 'WARN_EVENT', VERSIONS => 1, BLOOMFILTER => 'ROWCOL', BLOOMFILTER=>'ROWCOL', COMPRESSION => 'GZ'}
#增加列族，用于存放序列
alter 'NS_SY:H_EVENT', {NAME => 'SEQ', VERSIONS => 1, BLOOMFILTER => 'ROWCOL', BLOOMFILTER=>'ROWCOL', COMPRESSION => 'GZ'}

#序列rowkey设计为固定字符串：19位数字+固定字符，以下划线分隔
0000000000000000000_SEQ

#报警事件数据rowkey设计为：19位字符序列+自定义字段，以下划线分隔
0000000000000000012_fieldVal1_fieldVal2 ...

#创建序列
incr 'NS_SY:H_EVENT', '0000000000000000000_SEQ', 'SEQ:NUM', 1

