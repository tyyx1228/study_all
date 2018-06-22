package com.ty.study.domain;

import java.util.List;

public class PageData<T> {
	
	private List<T> data;
	
	private long recordsTotal;
	
	public PageData(){}
	
	public PageData(List<T> data){
		this.recordsTotal = data.size();
		this.data = data;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

}
