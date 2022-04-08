package hello.hellospring.repository;

import java.util.*;
import hello.hellospring.domain.Member;

public class MemoryMemberRepository implements MemberRepository {
	//key는 회원의 id, 값은 Member
	//동시성 문제 -> 공유되는 변수일 concurrent hashmap 사용함
	private static Map<Long, Member> store = new HashMap();
	//이또한 동시성 문제를 고려해 AtomicLong 사용
	private static long sequence = 0L;
	
	@Override
	public Member save(Member member) {
		//멤버 세이브시
		//sequence값 하나 올려줌
		member.setId(++sequence);
		//store에 넣기전에 member의 id를 세팅해줌
		store.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		//store.get(id) 결과가 없을경우 null올 수 있는데,
		//Optional.ofNullable로 감싸서 null이여도 감싸 반환
		//클라이언트에서 조작할 수 있는것이 있음
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Optional<Member> findByName(String name) {
		//store java8 lambda
		//filter에서 member가 parameter로 넘어온 name과 같은경우 필터링됨
		//그중 findAny로 하나라도 찾으면 return함 없을경우 optional로 감싼 null을 반환
		return store.values().stream()
				.filter(member ->member.getName().equals(name))
				.findAny();
	}

	@Override
	public List<Member> findAll() {
		//반환은 List 많이 씀
		//store.values() = member들을 반환
		return new ArrayList<>(store.values());
	}
	//어떻게 검증을 할까?
	//test케이스 작성 -> MemberRepositoryTest ㄱㄱ

	public void clearStore() {
		store.clear();
	}

}
