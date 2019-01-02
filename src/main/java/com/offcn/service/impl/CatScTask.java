package com.offcn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.offcn.dao.ScDao;
import com.offcn.po.Sc;
import com.offcn.util.CatSc2;

@Service
public class CatScTask {

	@Autowired
	ScDao dao;
	
	@Scheduled(cron="0 27 8/1 * * ?")
	public void beginTask(){
		System.out.println("启动定时抓取数据程序");
		for(int i=1;i<38;i++){
			String url="http://www.xinfadi.com.cn/marketanalysis/0/list/"+i+".shtml?prodname=%E5%A4%A7%E7%99%BD%E8%8F%9C&begintime=2017-01-01&endtime=2018-12-28";
			String html = CatSc2.catUrl(url);
			//System.out.println(html);
			List<Sc> list = CatSc2.parsHtml(html);
			for(Sc sc:list){
				Sc sc2 = dao.getScByNameAndCreateDate(sc);
				if(sc2==null){
				dao.save(sc);
				}
			}
			}
	}
}
