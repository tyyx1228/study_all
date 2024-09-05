select
	t.W_ID										w_record_id,	--记录id
	t.P_I_ID 									w_tp_id,		--测点id
	upper(t.kks_code)					w_tp_kkscode,	--报警测点kkscode
	TO_NUMBER(t.W_VALUE) 			w_value,		--报警值
	t.W_DATE									w_start_time,	--报警开始时间
	t.ENDTIME 								w_stop_time,	--报警结束时间
	TO_NUMBER(t.W_LEVEL)			w_level,		--报警级别
	TO_NUMBER(t.alarm_type)		w_service_type,	--0:测点报警， 1:超量程上限，2:超量程下限， 3:变化速率异常
	TO_NUMBER(t.bjtimes) 			w_duration,		--报警时长  数字 秒为单位
	t.W_TIME									w_duration_desc, --报警时长描述，格式为 d年d月d日d时d分d秒
	TO_NUMBER(t.W_STATUS)			w_status,		--报警状态(1：已消除:0：未消除)
	t.class_value							w_class_val,	--班值
	TO_NUMBER(t.w_type)				msg_issend,		--短信是否已发送，1为发送，null或者0为未发送
	t.rid											rid,			--上游事件数据的rid，用于当前表的数据采集
	t.kaohetag								is_appraise,	--考核标记，'Y'表示已考核
	t.create_date 						create_date,	--当前记录落地时间
	t.last_update_date				update_date,  --当前记录修改时间
	--测点基本信息
	a.t_p_name																                                      tp_desc,		--测点描述
	case when a.spec_id in ('-', '—', '') then null else a.spec_id end              professional,	--所属专业
	case when a.t_p_unit in ('-', '—', '') then null else a.t_p_unit end	          val_unit,		--单位  描述数字的涵意
	case when a.w_value_high in('-', '—', '') then null else a.w_value_high end			w_value_high,	--高报警值
	case when a.w_value_low in('-', '—', '') then null else a.w_value_low end		    w_value_low,		--低报警值
	a.g_id																				                                  unit_id,			--机组id
	cast(t.unit_status as int) 															                        unit_status,	--机组状态
	case when a.bz_warn	in('-', '—', '')then null else a.bz_warn end					      w_bz_factor,	--班组开始报警的因素
	case when a.bz_endvalue	in('-', '—', '')then null else a.bz_endvalue end			  w_bz_end_factor, --班组结束报警的因素
	case when a.dc_warn	in('-', '—', '')then null else a.dc_warn end					      w_dc_factor,	--电厂或公司开始报警的因素
	case when a.dc_endvalue	in('-', '—', '')then null else 	a.dc_endvalue end			  w_dc_end_factor,--电厂或公司结束报警的因素
	case when a.bm_warn	in('-', '—', '')then null else 	a.bm_warn end					      w_bm_factor,	--部门开始报警的因素
	case when a.bm_endvalue in('-', '—', '')then null else 	a.bm_endvalue end			  w_bm_end_factor, --部门结束报警的因素
	case when a.jt_warn in('-', '—', '')then null else a.jt_warn end					      w_jt_factor,	 --集团开始报警的因素
	case when a.jt_endvalue in('-', '—', '')then null else 	a.jt_endvalue end			   w_jt_end_factor, --集团结束报警的因素
	--电厂
	t.org_id 																  ph_id,			--电厂id，ph=Power House
	o.org_name																ph_name,		--电厂名称
	o.attribute5															ph_grid,		--所属电网
	o.org_codes																ph_simple_name,	--电厂简称
	--受控
	TO_NUMBER(s.controlled_state)						  ctrl_state,		--受控状态(1:非受控；0:受控)
	s.alarm_reason														alarm_reason,	--报警原因
	s.processing_mode													process_mode,	--处理方式
	s.processing_method												process_method, --处理方法
	s.processing_state												process_state,	--处理状态( 处理中 、处理完成 )插入时默认为空
	s.plan_eliminate_date											plan_eliminate_date, --计划消除时间
	s.submitter																submitter,		--提交人
	s.submit_date															submit_date,	--提交时间
	s.note																	  note,		--备注
	s.batch																	  batch,			--批次编码
	s.ismain																  ismian,			--是否是同一批次的主记录（Y：是主记录）
	s.sjxcrq																  sjxcrq			--实际消除日期
from (
	--只同步7月1号及以后的报警事件
	select *
	from tes_ba_t_p_w_list
	where last_update_date>=:sql_last_value
) t
left join tes_ba_points_index a
on t.p_i_id=a.p_i_id
left join xip_pub_orgs o
on t.org_id=o.org_id
left join baojing_info_sc s
on t.w_id=s.id
order by update_date asc