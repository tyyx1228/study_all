package com.ty.study.dao;

import java.util.List;

import com.ty.study.domain.Menu;


public interface MenuDao {

	List<Menu> findPrimaryMenu();

	List<Menu> findByParentId(Long parentId);
}
