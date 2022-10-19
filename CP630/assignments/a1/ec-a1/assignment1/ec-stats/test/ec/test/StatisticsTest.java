package ec.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ec.ECStatistics;

public class StatisticsTest {
	

	@Before
	public void beforeEachTest() {
		System.out.println("Testing has Started");
	}

	@After
	public void afterEachTest() {
		System.out.println("Testing Complete");
	}
	

	@Test
	public void testCount() {
		ECStatistics statisitcs  = new ECStatistics();
		for (int i = 1; i <= 5; i++) {
			statisitcs.addData(i);
		}		
		assertEquals(5.0, statisitcs.getCount(), 0.0);
	}

	@Test
	public void testMin() {
		ECStatistics statisitcs  = new ECStatistics();
		for (int i = 1; i <= 5; i++) {
			statisitcs.addData(i);
		}		
		assertEquals(1.0, statisitcs.getMin(), 0.0);
	}

	@Test
	public void testMax() {
		ECStatistics statisitcs  = new ECStatistics();
		for (int i = 1; i <= 5; i++) {
			statisitcs.addData(i);
		}		
		assertEquals(5.0, statisitcs.getMax(), 0.0);
	}

	@Test
	public void testMean() {
		ECStatistics statisitcs  = new ECStatistics();
		for (int i = 1; i <= 5; i++) {
			statisitcs.addData(i);
		}		
		assertEquals(3.0, statisitcs.getMean(), 0.0);
	}

	@Test
	public void testSTD() {
		ECStatistics statisitcs  = new ECStatistics();
		for (int i = 1; i <= 5; i++) {
			statisitcs.addData(i);
		}		
		assertEquals(1.70293863659264, statisitcs.getSTD(), 0.005);
	}
}
