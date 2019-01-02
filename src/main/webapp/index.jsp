<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>echarts</title>
    <meta charset="UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/echarts.common.min.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
  </head>
  
  <body>
  <div id="box" style="width:3600px;height:800px;"></div>
  
  <script type="text/javascript">
  
  var mychart=echarts.init(document.getElementById('box'));
  
  mychart.setOption({
     title:{
       text:'新发地大白菜销售价格走势图'
     },
     xAxis:{
       data:[]
     },
     yAxis:{},
     series:[{
        name:'最低价格',
        type:'line',
        data:[]
     },{
        name:'平均价格',
        type:'line',
        data:[]
     },{
        name:'最高价格',
        type:'line',
        data:[]
     }]     
  
  });
  
  //显示加载动画
  mychart.showLoading();
  //定义商品最低数组
  var lowprices=[];
  //定义商品最高数组
  var hprices=[];
  //定义商品平均数组
  var avgprices=[];
  //定义日期时间数组
  var createdates=[];
  //发出ajax请求
  $.ajax({
    type:'post',
    url:'getscbydate',
    data:{begindate:'2018-12-01',enddate:'2018-12-31'},
    dataType:'json',
    success:function(json){
    
      if(json){
         //遍历返回的json
         for(var i=(json.length-1);i>0;i--){
         
            lowprices.push(json[i].lowprice);
            avgprices.push(json[i].avgprice);
            hprices.push(json[i].hprice);
            //获取到毫秒值
           var time=new Date(json[i].createdate);
           //获取年份
           var year=time.getFullYear();
           //获取月份
           var month=time.getMonth()+1;
           //获取日期
           var date=time.getDate();
           
           var dateStr=year+'-'+month+'-'+date;
           
           createdates.push(dateStr);
            
         }
         //隐藏加载动画
         mychart.hideLoading();
         //设置图表数据
         mychart.setOption({
            xAxis:{
              data:createdates
            },
            series:[{
            name:'最低价格',
            type:'line',
            data:lowprices
            },{
            name:'平均价格',
            type:'line',
            data:avgprices
            },{
            name:'最高价格',
            type:'line',
            data:hprices
            }]
         
         });
      }
    }
  
  });
  </script>
    
  </body>
</html>
