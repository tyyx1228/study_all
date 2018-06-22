package com.ty.study.service;


import java.util.List;

import com.ty.study.domain.Menu;

public interface MenuService {

	List<Menu> findPrimaryMenu();

	List<Menu> findSecondaryMenu(Long parentId);

	
}
