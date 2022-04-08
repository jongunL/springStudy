package hello.hellospring.repository;

import java.util.List;

//static import 하면 Assertions생략 가능
//import org.assertj.core.api.Assertions.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;

class MemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	//테이스 케이스 작성시 주의점 Junit이
	//findAll()
	//findByName()
	//save()
	//순으로 실행됨, 테스트 케이스는 순서 보장이 안된다.
	//순서 상관없이 메서드 따로 동작하도록 설계해야함
	//테스트 하나 끝나고나면 데이터를 깔끔하게 클리어해줘야하는데,
	//afterEach 를 이용해 작성한다.
	//afterEach는 에코벡 메서드로써 테스트케이스 하나 실행될때마다 해당 메서드로 오게됨
	//따라서 이 메서드는 세이브가 끝난 이후 리파지토리를 비우는 용도로 사용할것임
	//테스트 케이스는 의존관계없이 작성해야함
	//TDD -> 테스트케이스를 먼저 만든 후 구현하는 방식 // 테스트 주도 개발 알아보기
	//이 케이스와는 상관 없음
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}
	
	@Test
	public void save() {
		Member member = new Member();
		member.setName("spring");
		
		repository.save(member);
		
		//Optional은 get으로 꺼낼 수 있음
		//저장한 member와 db에서 꺼낸것과 똑같으면 참
		Member result = repository.findById(member.getId()).get();
		
		//이렇게 출력해봐도됨
//		System.out.println("result = " + (result == member));
		//콘솔 글자로 계속 볼 수 없기에 Assertions를 이용해 확인가능
		//같다면 녹색
		//org.junit.jupiter.api.Test
		//parameter expected(같기를 기대하는 값) actual(비교하는 값) 
		//Assertions.assertEquals(member, result);
		//다르다면 빨간색이 뜸
		//Assertions.assertEquals(member, null);
		
		//org.assertj.core.api.Assertions
		//member가 result와 똑같냐?
		Assertions.assertThat(member).isEqualTo(result);
	}
	@Test
	public void findByName() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		//alt + shift + r = 같은 이름 변수이름 모두 변경하기
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		Member result = repository.findByName("spring1").get();	
		//member1 : 정상 동작 확인
		//member2 : 오류 발생 확인
		Assertions.assertThat(result).isEqualTo(member1);
	}
	
	@Test
	public void findAll() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		List<Member> result = repository.findAll();
		
		//2개 있으니 오류 안남, 3넣으면 남
		Assertions.assertThat(result.size()).isEqualTo(2);
	}
}
