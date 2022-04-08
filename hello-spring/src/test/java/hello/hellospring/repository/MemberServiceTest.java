package hello.hellospring.repository;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;

public class MemberServiceTest {

	//static은 클래스레벨이라 큰 상관은 없으나, new로 새로운 객체를 생성하면
	//다른 객체이기 때문에 내용물이 달라지거나 그럴 확률이 존재하기 때문에 권장하지는 않는다.
	//static이 없다면 완전히 다른 객체이기때문에, 다른 리포지토리를 이용하는 격이다.
	// 같은 인스턴스를 사용하고 싶다면?
//	MemberService memberService = new MemberService();
//	MemoryMemberRepository memberRepository = new MemoryMemberRepository();
	
	//생성자를 이용하는 방법이다.
	//@BeforeEach 를 이용해 테스트 실행 전마다, 
	MemoryMemberRepository memberRepository;
	MemberService memberService;

	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
	}
	
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}
	//테스트코드는 한글로 적어도 문제가 없음
	//저장이 되는것도 중요하나
	//테스트는 예외 플로우가 더 중요하다.
	@Test
	void 회원가입() {
		//given when then 문법
		
		//given : 어떤 상황이 주어짐
		Member member =new Member();
		member.setName("hello");
		
		//when : 실행했을때
		Long saveId = memberService.join(member);
		
		//then : 결과가 나와야함
		Member findMember = memberService.findOne(saveId).get();
		Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	@Test
	public void 중복_회원_예외() {
		//given
		Member member1 = new Member();
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");
		
		//when
		memberService.join(member1);
		
		//이것만으로 try-catch 애매하기 떄문에 람다식 이용해서 사용할것임
//		try {
//			memberService.join(member2);
//			fail();	//예외 발생해야 하는데 정상실행된경우
//		} catch (IllegalStateException e) {
//			Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//		}
		
		//import static org.junit.jupiter.api.Assertions.assertThrows;
		//static import시 클래스명 작성 안해도됨
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		//then
	}
	
	@Test
	void findMembers() {
		
	}
	@Test
	void findOne() {
		
	}
}
