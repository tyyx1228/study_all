package com.ty.study.dao;

import java.util.List;

import com.ty.study.domain.LocationInfo;

public interface LocationInfoDao {
	
	
	List<LocationInfo> findAll();
}
