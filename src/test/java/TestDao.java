import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.dao.ScDao;
import com.offcn.dao.StuDao;
import com.offcn.po.Sc;
import com.offcn.po.Stu;

public class TestDao {

	public static void main(String[] args) throws ParseException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-dao.xml");

		ScDao dao = context.getBean(ScDao.class);
		Sc sc = new Sc();
		sc.setName("大白菜");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		sc.setCreatedate(df.parse("2018-12-27"));
		//dao.save(sc);
		
		Sc sc2 = dao.getScByNameAndCreateDate(sc);
		System.out.println(sc2);
		/*List<Sc> list = dao.getScByDate("2018-12-28", "2018-12-28");
		for (Sc sc2 : list) {
			System.out.println(sc2);
		}*/
	}

}
