package com.prealpha.aichallenge.ai;

import org.junit.Test;

import com.prealpha.aichallenge.protocol.Point;

public class AStarTest {
	/**
	 * Tests the smallest path with only a starting point
	 */
	@Test
	public void TestAdvanceOne(){
		Point start = new Point(0,0);
		Point end   = new Point(5,0);
		
		AStarAgent route = new AStarAgent(start, end);
		
		Path curMin = route.findCurrentMin();
		
		assert(curMin.equals(new Path(start)));
	}
}
