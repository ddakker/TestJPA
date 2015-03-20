package pe.kr.ddakker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	@Transactional()
	@Rollback(false)
	public void test() throws Exception {
		//TestBoard board = new TestBoard(1l, "ddakker");
		TestBoard board = new TestBoard("ddakker");
		board.setDesc(new TestBoardDesc("info"));
		entityManager.persist(board);
		entityManager.flush();
		
		/*TestBoard resultBoard = (TestBoard) entityManager
				.createQuery("select name from TestBoard b");
		System.out.println(resultBoard);*/
	}
	
	@Test
	@Transactional()
	@Rollback(false)
	public void test2() throws Exception {
		TestBoardDesc desc = new TestBoardDesc(1l, "info", new TestBoard("zzz"));
		entityManager.persist(desc);
		entityManager.flush();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TestBoard> resultBoard = cb.createQuery(TestBoard.class);
		System.out.println("resultBoard: " + resultBoard);
		
		TestBoardDesc joinResultBoardDesc = entityManager.createQuery("select bd from TestBoard b join b.desc bd where bd.id=:id", TestBoardDesc.class)
				 .setParameter("id", desc.getBoard().getId())
				 .getSingleResult();
		
		System.out.println("joinResultBoardDesc: " + joinResultBoardDesc);
		System.out.println("joinResultBoardDesc.getBoard(): " + joinResultBoardDesc.getBoard());
		System.out.println(joinResultBoardDesc.getInfo() + ", " + joinResultBoardDesc.getBoard().getName());
		
		
	}
	
	@Test
	@Transactional(noRollbackFor = Exception.class)
	@Rollback(false)
	public void test3() throws Exception {
		//TestBoard board = new TestBoard(1l, "ddakker");
		TestBoard board = new TestBoard("ddakker");
		
		TestBoardDesc desc = new TestBoardDesc("info");
		desc.setBoard(board);
		
		entityManager.persist(desc);
		entityManager.flush();
		
		/*TestBoard resultBoard = (TestBoard) entityManager
				.createQuery("select name from TestBoard b");
		System.out.println(resultBoard);*/
	}
	
	@Test
	@Transactional
	public void add() throws Exception {
		//TestBoard board = new TestBoard(new Long(1), "ddakker", new TestBoardDesc(new Long(1), "정보"));
		//TestBoard board = new TestBoard(new Long(1), "ddakker", new TestBoardDesc("정보"));
		
		
		//entityManager.persist(board);
		//entityManager.flush();
		//assertNotNull(board);
	}

	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Order order = new Order();
		order.getItems().add(new Item());
		entityManager.persist(order);
		entityManager.flush();
		assertNotNull(order.getId());
	}

	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		Order order = new Order();
		order.getItems().add(new Item());
		entityManager.persist(order);
		entityManager.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		entityManager.clear();
		Order other = (Order) entityManager.find(Order.class, order.getId());
		assertEquals(1, other.getItems().size());
		assertEquals(other, other.getItems().iterator().next().getOrder());
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testSaveAndFind() throws Exception {
		Order order = new Order();
		Item item = new Item();
		item.setProduct("foo");
		order.getItems().add(item);
		entityManager.persist(order);
		entityManager.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		entityManager.clear();
		Order other = (Order) entityManager
				.createQuery(
						"select o from Order o join o.items i where i.product=:product")
				.setParameter("product", "foo").getSingleResult();
		//System.out.println("other: " + other);
		//System.out.println("other.getItems(): " + other.getItems());
		//System.out.println("other.getItems().size(): " + other.getItems().size());
		assertEquals(1, other.getItems().size());
		assertEquals(other, other.getItems().iterator().next().getOrder());
	}

}
