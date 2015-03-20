package pe.kr.ddakker;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="testboard")
public class TestBoard {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	 
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="board", optional=true)
	@PrimaryKeyJoinColumn
	private TestBoardDesc desc;
	/*@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, optional=true)
	@PrimaryKeyJoinColumn
	@JoinColumn (name="id", nullable=false)
	private TestBoardDesc desc;*/
	
	
	public TestBoard(){}
	
	public TestBoard(String name) {
		this.name = name;
	}
	
	public TestBoard(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public TestBoard(Long id, String name, TestBoardDesc desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TestBoardDesc getDesc() {
		return desc;
	}

	public void setDesc(TestBoardDesc desc) {
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
