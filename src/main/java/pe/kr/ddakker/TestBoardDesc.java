package pe.kr.ddakker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class TestBoardDesc {
	public TestBoardDesc(){}
	
	public TestBoardDesc(Long id, String info) {
		this.id = id;
		this.info = info;
	}
	
	public TestBoardDesc(String info) {
		this.info = info;
	}
	
	public TestBoardDesc(String info, TestBoard board) {
		this.info = info;
		this.board = board;
	}
	
	public TestBoardDesc(Long id, String info, TestBoard board) {
		this.id = id;
		this.info = info;
		this.board = board;
	}
	
	@Id
	@GenericGenerator(name = "foreign_one_to_one_generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "board"))
	@GeneratedValue(generator = "foreign_one_to_one_generator")
	@Column(name = "parent_id")
	private Long id;
	 
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@PrimaryKeyJoinColumn(name = "parent_id", referencedColumnName = "id")
	private TestBoard board;
	
	/*@Id
	@Column (name="id")
	private Long id;
	
	@OneToOne(optional=false)
	@JoinColumn (name="id")
	private TestBoard board;*/
	
	private String info;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TestBoard getBoard() {
		return board;
	}

	public void setBoard(TestBoard board) {
		this.board = board;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
