package com.ty.study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ty.study.dao.LocationInfoDao;
import com.ty.study.domain.LocationInfo;

@Service
public class LocationInfoServiceImpl implements LocationInfoService {


	@Autowired
    private LocationInfoDao locationInfoDao;
	
	public List<LocationInfo> findAll() {
		return locationInfoDao.findAll();
	}

}
