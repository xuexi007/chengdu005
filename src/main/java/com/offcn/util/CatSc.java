package com.offcn.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.dao.ScDao;
import com.offcn.po.Sc;

public class CatSc {

	public static void main(String[] args) {
		//初始化spring环境
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		ScDao dao = context.getBean(ScDao.class);
		
		for(int i=1;i<38;i++){
		String url="http://www.xinfadi.com.cn/marketanalysis/0/list/"+i+".shtml?prodname=%E5%A4%A7%E7%99%BD%E8%8F%9C&begintime=2017-01-01&endtime=2018-12-28";
		String html = catUrl(url);
		//System.out.println(html);
		List<Sc> list = parsHtml(html);
		for(Sc sc:list){
			dao.save(sc);
		}
		}
	}
	
	/**
	 * 使用httpclient抓取指定网页的内容
	 */
	public static String catUrl(String url){
		String html=null;
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpGet get = new HttpGet(url);
		
		try {
			CloseableHttpResponse response = client.execute(get);
			int code=response.getStatusLine().getStatusCode();
			if(code==200){
				HttpEntity en = response.getEntity();
				html=EntityUtils.toString(en, "utf-8");
			}else{
				
				System.out.println("状态码错误:"+code);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return html;
	}
	
	/**
	 * 解析html获取里面蔬菜报价
	 */
	public static List<Sc> parsHtml(String html){
		List<Sc> list=new ArrayList<Sc>();
		
		Document doc = Jsoup.parse(html);
		//使用选择器语法选择到 class 为：hq_table
		Element table = doc.select(".hq_table").first();
		Elements trs = table.select("tr");
		
		//移除第一个tr
		trs.remove(0);
		
		//循环遍历全部的tr
		
		for (Element tr : trs) {
			String rowStr=tr.text();
			//System.out.println(rowStr);
			String[] ss = rowStr.split(" ");
			Sc sc = new Sc();
			sc.setName(ss[0]);
			sc.setLowprice(Float.valueOf(ss[1]));
			sc.setAvgprice(Float.valueOf(ss[2]));
			sc.setHprice(Float.valueOf(ss[3]));
			sc.setGuige(ss[4]);
			sc.setUnit(ss[5]);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				sc.setCreatedate(df.parse(ss[6]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(sc);
			list.add(sc);
		}
		return list;
	}

}
