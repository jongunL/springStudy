package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		//메인 메서드 실행
		//HelloSpringApplication.class 실행 -> 톰켓 웹서버 내장되어있음
		SpringApplication.run(HelloSpringApplication.class, args);
		
		/*
		Tomcat started on port(s): 8080 (http) with context path '' 이게 중요한거임
		localhost:8080 -> URL에 입력해서 접속해보기
		 */
	}

}
