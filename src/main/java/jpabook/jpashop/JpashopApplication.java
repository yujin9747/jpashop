package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
//		lombok 잘 되는지 확인
//		Lombok lombok = new Lombok();
//		lombok.setData("test");
//		System.out.println(lombok.getData());
		SpringApplication.run(JpashopApplication.class, args);
	}

}
