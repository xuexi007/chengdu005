package com.offcn.service;

import java.util.List;


import com.offcn.po.Sc;

public interface ScService {

	@GET
	
	public List<Sc> getScByDate(String begindate,String enddate);
}
