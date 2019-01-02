import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.offcn.po.Stu;
import com.offcn.service.StuService;

public class TestService {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml","spring-service.xml");

		StuService service = context.getBean(StuService.class);
		
		List<Stu> list = service.getAll();
		for (Stu stu : list) {
			System.out.println(stu);
		}
	}

}
