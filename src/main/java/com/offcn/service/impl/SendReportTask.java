package com.offcn.service.impl;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.offcn.dao.ScDao;
import com.offcn.po.Sc;

@Service
public class SendReportTask {

	@Autowired
	ScDao dao;
	@Autowired
	JavaMailSender mailSend;
	
	@Scheduled(cron="0/15 * 8/1 * * ?")
	public void MakeReportSendmail(){
		System.out.println("启动定时任务生成图表");
		makeReport();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendMail();
		
	}
	
	/**
	 * 生成JfreeChart图表
	 */
	public  void makeReport(){
		//获取数据中，制定日期的数据
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		List<Sc> list = dao.getScByDate(df.format(new Date(System.currentTimeMillis()-(8*24*3600*1000))), df.format(new Date()));
	
	//创建JfreeChart图表
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Sc sc:list){
		dataset.setValue(sc.getLowprice(), "最低价格", df.format(sc.getCreatedate()));
		dataset.setValue(sc.getHprice(), "最高价格", df.format(sc.getCreatedate()));
		dataset.setValue(sc.getAvgprice(), "平均价格", df.format(sc.getCreatedate()));
		}
		
		//设置图表样式
		StandardChartTheme st = new StandardChartTheme("CN");
		st.setExtraLargeFont(new Font("黑体",Font.BOLD,20));
		st.setRegularFont(new Font("宋体",Font.ITALIC,15));
		st.setLargeFont(new Font("仿宋",Font.BOLD,12));
		
		ChartFactory.setChartTheme(st);
		
		JFreeChart chart = ChartFactory.createLineChart("大白菜价格走势图", "日期", "最低价格", dataset);
		
		CategoryPlot plot = chart.getCategoryPlot();
		LineAndShapeRenderer render = new LineAndShapeRenderer();
		render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		render.setBaseItemLabelsVisible(true);
		
		plot.setRenderer(render);
		
		try {
			ChartUtilities.saveChartAsJPEG(new File("C:\\Users\\Administrator\\Workspaces\\MyEclipse 2017 CI\\.metadata\\.me_tcat85\\webapps\\xinfadi\\chart\\line1.jpg"), chart, 1800, 500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 发送邮件
	 */
	
	public void sendMail(){
		MimeMessage mmMsg = mailSend.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mmMsg, true);
			helper.setFrom("hk109@126.com");
			helper.setTo("hk109@126.com");
			helper.setSubject("大白菜价格走势图");
			helper.setText("<h1>大白菜价格走势图</h1><br><img src='cid:www'>", true);
		
			helper.addInline("www", new File("C:\\Users\\Administrator\\Workspaces\\MyEclipse 2017 CI\\.metadata\\.me_tcat85\\webapps\\xinfadi\\chart\\line1.jpg"));
		
			mailSend.send(mmMsg);
			System.out.println("邮件发送成功");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
