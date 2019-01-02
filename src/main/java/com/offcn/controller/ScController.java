package com.offcn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offcn.po.Sc;
import com.offcn.service.ScService;
/**
 * 新增注释0001
 * @author Administrator
 *
 */
@Controller
public class ScController {

	@Autowired
	ScService service;
	
	//@RequestMapping("/getscbydate")
	@RequestMapping(value="/get/id/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List<Sc> getScByDate(@PathVariable("id") String begindate,String enddate){
		return service.getScByDate(begindate, enddate);
	}
}
