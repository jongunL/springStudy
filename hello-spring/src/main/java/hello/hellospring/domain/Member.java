package hello.hellospring.domain;

public class Member {
	//long이 아닌 Long을 이용한 이유는 null값이 들어갈 수 있기때문에 채택된것이다.
	private Long id;		//시스템이 저장하는 id
	private String name;	//이름
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
