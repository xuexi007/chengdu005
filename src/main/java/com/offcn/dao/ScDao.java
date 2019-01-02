package com.offcn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.offcn.po.Sc;

public interface ScDao {

	public void save(Sc sc);
	
	public List<Sc> getScByDate(@Param("begindate")String begindate,@Param("enddate")String enddate);
	
	public Sc getScByNameAndCreateDate(Sc sc);
}
