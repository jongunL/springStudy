package hello.hellospring.repository;

import java.util.List;
import java.util.Optional;
import hello.hellospring.domain.Member;

public interface MemberRepository {
	//저장소에 회원 저장
	Member save(Member member);
	//Optional java8에 들어간 기능
	//findById, findByName가 null이 들어올 수 있는데,
	//null을 그대로 반환하는 방법 대신에 Optional로 감싸서 반환하는 방식을 선호함
	Optional<Member> findById(Long id);			//저장소에  id찾아옴
	Optional<Member> findByName(String name);	//저장소에서 name찾아옴
	List<Member> findAll();	//모든 회원 정보 리스트 출력
}
