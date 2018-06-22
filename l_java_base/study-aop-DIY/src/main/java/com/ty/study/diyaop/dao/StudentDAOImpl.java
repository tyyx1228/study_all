package com.ty.study.diyaop.dao;

import com.ty.study.diyaop.proxy.ProxyFactoryBean;

/**
 *
 *@author tongyu
 *@time 2015年2月7日 上午10:58:59
 */

public class StudentDAOImpl extends ProxyFactoryBean implements StudentDAO {

	@Override
	public boolean insert() {
		System.out.println("学生已添加成功");
		return false;
	}

	@Override
	public boolean update() {
		System.out.println("学生已更新成功");
		return false;
	}

	@Override
	public boolean delete() {
		System.out.println("学生已删除成功");
		return false;
	}

	@Override
	public boolean query() {
		System.out.println("查询了学生");
		return false;
	}

}
