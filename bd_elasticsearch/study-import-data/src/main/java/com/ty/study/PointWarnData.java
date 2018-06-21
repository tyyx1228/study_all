package com.ty.study;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 报警事件
 *
 * @author relax tongyu
 * @create 2018-06-13 11:40
 **/
@Setter
@Getter
@ToString
public class PointWarnData implements Serializable{

    private String pointId;

    private String kkscode;

    private String orgId;

    private Date warnStartTime;

    private String warnLevel;

    private String warnValue;

    private String warnDuration;

    private String status;

    //
    private String cost;

    private String unitStatus;

    private Long rid;

    private Date warnEndTime;

    private String serviceType;

    //考核
    private String assessment;





}
