package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

//테스트케이스 단축키 
public class MemberService {
	//회원 서비스 클래스
	//회원 서비스 : 회원 리포지토리 + 도메인을 이용해 비지니스 로직을 만드는 단계
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	
	/*
	 * 회원가입
	 */
	public Long join (Member member) {
		//같은 이름이 있는 중복 회원x
		//ctrl + shift + L 메소드만 입력시 result 입력됨
		//Optional를 바로 반환하는건 권장하지 않음
		//Optional<Member> result = memberRepository.findByName(member.getName());

		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		//권장하는 방법
		//ifPresent 값이 있다면, 예외발생시킴
		//Alt + Shift + m = extract Method
		memberRepository.findByName(member.getName())
					.ifPresent( m -> {
							throw new IllegalStateException("이미 존재하는 회원입니다.");
					});
	}
	
	/*
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	//서비스는 비지니스의존적 용어 선택하여 네이밍
	//리포지토리는 기계적, 개발스럽게 사용
	
	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
