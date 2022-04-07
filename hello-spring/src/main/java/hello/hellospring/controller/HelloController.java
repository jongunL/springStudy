package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//스프링 컨트롤러 어노테이션을 적어줘야함
@Controller
public class HelloController {

	@GetMapping("hello") //웹 어플리케이션에서 /hello 라고들어오면 이 메서드를 호출한다.
	public String hello(Model model) {
		//모델 mvc 모델뷰컨트롤에서 모델임
		model.addAttribute("data", "hello!!");
		//컨트롤러에서 리턴값으로 문자를 반환하면 뷰 리졸버가 화면을 찾아서 처리한다 -> 템플릿 hello.html로 이동
		return "hello";
	}
	
	@GetMapping("hello-mvc")
	public String helloMvc(@RequestParam(value = "name") String name, Model model) {
		//Model에 담으면 View에서 랜더링 할때 사용
		//key = "name"
		model.addAttribute("name", name);
		//return 하면 hello-template으로 간다.
		return "hello-template";
		//@RequestParam("name")
		//@RequestParam(value = "name", required = true)
		//required 옵션 -> default가 true이기때문에 무조건 넘겨줘야 해서 발생하는 오류
		//flase로 하면 안넘겨도됨
		//localhost:8080/hello-mvc 로 이동하면 에러발생
		//2022-04-07 22:40:01.905  WARN 25668 --- [nio-8080-exec-4] .w.s.m.s.DefaultHandlerExceptionResolver
		//: Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required request parameter 'name' for method parameter type String is not present]
		
		//'name' for method parameter에 인자값을 달라는 건데
		//웹사이트의 주소 뒤에 ?name=spring! 를 붙이면 된다.
		//http://localhost:8080/hello-mvc?name=spring!
		
		//동작원리
		//http get방식 ?name = spring 
		//name에 spring parameter 전달
		//이후 name key값을 찾아 name에 spring을 전달
		//model에 담기고 -> return을 통해 "hello-template"에 넘어가 치환 후 동작
		//model.addAttribute("name", name);
	}
	@GetMapping("hello-string")
	@ResponseBody
	//@ResponseBody = http에서 head부와 body부가 있는데 (http 통신프로토콜)
	//body에 데이터를 직접 넣어주겠다는 뜻이다.
	public String helloString(@RequestParam("name") String name) {
		//API방식
		//http://localhost:8080/hello-string?name=spring!!!
		//만약 spring이라고 name을 넣으면 hello spring으로 바뀜
		//이 문자가 요청한 클라이언트에 그대로 내려감
		//템플릿 엔진과의 차이는 View 이런것 없이 그대로 내려감
		//html소스없이 무식하게 name만 그대로 올라감
		return "hello" + name;
	}
	//탬플릿 앤진은 view라는 탬플릿에서 조작하는 방식
	//API는 데이터를 그대로 내려주는 방식
	
	//그럼 왜쓸까?
	//데이터를 내놓으라고 할때
	//http://localhost:8080/hello-api?name=spring!!! 실행시
	//{"name":"spring!!!"} 처럼 출력이 되는데
	//json 이라는 방식이다.
	//json = key : value로 이루어진 구조
	//XML 방식은 <html></html> 여닫는식으로 실행되기 때문에 무겁고 번거로움
	//json 방식은 name = value 처럼 바로 나오기떄문에 간단하다.
	//요즘에는 json 방식에 통일됨
	//Spring도 객체를 반환하고 @ResponseBody라고 해두면 json으로 반환하는것이 기본임
	//원하면 XML로 할수는 있음
	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloApi(@RequestParam("name") String name) {
		Hello hello = new Hello();
		hello.setName(name);
		return hello;//객체반환
		//@ResponseBody라고 오면
		//hello 객체가를 넘기고 몇가지 조건을 보고
		//httpMessageConverter가 동작함 -> 기존에는 뷰리졸버가 동작(탬플릿 사용시)
		//단순 문자면 String컨버터, 객체면 Json컨버터가 동작하고 해당 결과를 웹 브라우저에 뿌림
		//Spring에서 객체가 오면 기본방식으로 json방식으로 
	}
	
	static class Hello {
		private String name;
		
		//get, set 자바 bin 표준 방식
		//property 접근방식 이라고도함
		//클래스의 필드를 접근하는 방식임
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}

}
//정적 컨텐츠 : 파일을 그대로 내려줌
//MVC와 탬플릿 엔진 : 템플릿 엔진을 모델뷰컨트롤 방식으로 쪼개서 뷰를 탬플릿 엔진으로 html날것 그대로가 아닌 프로그래밍 하여 보다 가공된 것으로 랜더링해서 랜더링된 html을 사용자에게 전달해준다.
//API : 객체를 반환하는것 -> json 스타일로 / view 이런것 없이 그냥 바로 http리스폰스에 전달해줌
