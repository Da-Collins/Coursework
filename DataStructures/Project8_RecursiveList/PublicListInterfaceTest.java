package structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PublicListInterfaceTest {

	private ListInterface<String> list;

	@Before
	public void setup(){
          list = new RecursiveList<>();
	}

	@Test (timeout = 500)
	public void testInsertFirstIsEmptySizeAndGetFirst1() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		assertEquals("Newly constructed list should be size 0.", 0, list.size());
		assertEquals("Insert First should return instance of self", list, list.insertFirst("hello"));
		assertFalse("List should now have elements.", list.isEmpty());
		assertEquals("List should now have 1 element.", 1, list.size());
		assertEquals("First element should .equals \"hello\".", "hello", list.getFirst());
		list.insertFirst("world");
		assertEquals(2, list.size());
		list.insertFirst("foo");
		assertEquals(3, list.size());
		assertEquals("First element should .equals \"foo\".", "foo", list.getFirst());
	}
	
	@Test
	public void testRemoveFirst() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		assertTrue(list.size() == 1);
		list.removeFirst();
		assertTrue(list.size() == 0);
		list.insertFirst("1");
		list.insertFirst("2");
		list.insertFirst("3");
		list.removeFirst();
		assertTrue(list.size() == 2);
		assertEquals(list.getFirst(), "2");
	}
	
	@Test
	public void testRemoveLast() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		assertTrue(list.size() == 1);
		list.removeLast();
		assertTrue(list.size() == 0);
		list.insertFirst("1");
		list.insertFirst("2");
		list.insertFirst("3");
		list.removeLast();
		assertTrue(list.size() == 2);
		assertEquals(list.getLast(), "2");
	}
	
	@Test
	public void testInsertFirst() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		assertTrue(list.size() == 1);
		assertEquals(list.getFirst(), "1");
		list.insertFirst("2");
		assertTrue(list.size() == 2);
		assertEquals(list.getFirst(), "2");
		list.insertFirst("3");
		assertTrue(list.size() == 3);
		assertEquals(list.getFirst(), "3");
	}
	
	@Test
	public void testInsertLast() {
		list = new RecursiveList<String>();
		list.insertLast("1");
		assertTrue(list.size() == 1);
		assertEquals(list.getLast(), "1");
		list.insertLast("2");
		assertTrue(list.size() == 2);
		assertEquals(list.getLast(), "2");
		list.insertLast("3");
		assertTrue(list.size() == 3);
		assertEquals(list.getLast(), "3");
	}
	
	@Test
	public void testGetFirst() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		assertEquals(list.getFirst(), "1");
		list.insertFirst("2");
		assertEquals(list.getFirst(), "2");
	}
	
	@Test
	public void testGetLast() {
		list = new RecursiveList<String>();
		list.insertLast("1");
		assertEquals(list.getLast(), "1");
		list.insertLast("2");
		assertEquals(list.getLast(), "2");
	}
	
	@Test
	public void testGetAtLast() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		list.insertFirst("2");
		list.insertFirst("3");
		list.insertFirst("4");
		assertEquals(list.get(3), "1");
	}
	
	@Test
	public void testGetAtFirst() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		list.insertFirst("2");
		list.insertFirst("3");
		list.insertFirst("4");
		assertEquals(list.get(0), "4");
	}
	
	@Test
	public void testGetAt() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		assertEquals(list.get(0), "1");
		list.insertFirst("2");
		list.insertFirst("3");
		list.insertFirst("4");
		assertEquals(list.get(3), "1");
	}
	
	@Test
	public void testInsertAtLast() {
		list = new RecursiveList<String>();
		list.insertAt(0, "1");
		list.insertAt(0, "2");
		list.insertAt(2, "3");
		assertEquals(list.getLast(), "3");
	}
	
	@Test
	public void testInsertAtFirst() {
		list = new RecursiveList<String>();
		list.insertAt(0, "1");
		list.insertAt(0, "2");
		list.insertAt(0, "3");
		assertEquals(list.getFirst(), "3");
	}
	
	@Test
	public void testInsertAt() {
		list = new RecursiveList<String>();
		list.insertAt(0, "1");
		list.insertAt(0, "2");
		list.insertAt(0, "3");
		assertEquals(list.size(), 3);
		list.insertAt(2, "4");
		list.insertAt(1, "5");
		assertEquals(list.get(2), "2");
		assertEquals(list.get(1), "5");
	}
	
	@Test
	public void testRemoveAtLast() {
		list = new RecursiveList<String>();
		list.insertAt(0, "1");
		list.removeAt(0);
		assertEquals(list.size(), 0);
		list.insertAt(0, "2");
		list.insertAt(0, "3");
		list.removeAt(1);
		assertEquals(list.getLast(), "3");
	}
	
	@Test
	public void testRemoveAtFirst() {
		list = new RecursiveList<String>();
		list.insertAt(0, "1");
		list.removeAt(0);
		assertEquals(list.size(), 0);
		list.insertAt(0, "2");
		list.insertAt(0, "3");
		list.removeAt(0);
		assertEquals(list.getFirst(), "2");
		assertEquals(list.size(), 1);
	}
	
	@Test
	public void testRemoveAt() {
		list = new RecursiveList<String>();
		list.insertFirst("4");
		list.insertFirst("3");
		list.insertFirst("2");
		list.insertFirst("1");
		assertEquals(list.size(), 4);
		list.removeAt(1);
		assertEquals(list.get(1), "3");
		assertEquals(list.size(), 3);
	}
	
	@Test
	public void testIndexOfFirst() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		assertEquals(0, list.indexOf("1"));
		list.insertFirst("1");
		assertEquals(0, list.indexOf("1"));
		list.insertFirst("2");
		assertEquals(0, list.indexOf("2"));
	}
	
	@Test 
	public void testIndexOfLast() {
		list = new RecursiveList<String>();
		list.insertFirst("1");
		list.insertLast("2");
		assertEquals(1, list.indexOf("2"));
	}
}
