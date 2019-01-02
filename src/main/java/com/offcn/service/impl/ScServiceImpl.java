package com.offcn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offcn.dao.ScDao;
import com.offcn.po.Sc;
import com.offcn.service.ScService;
@Service
public class ScServiceImpl implements ScService {

	@Autowired
	ScDao     dao;
	
	@Override
	public List<Sc> getScByDate(String begindate, String enddate) {
		
		return dao.getScByDate(begindate, enddate);
	}

}
