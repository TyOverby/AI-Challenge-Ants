package com.prealpha.aichallenge.ai;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class AStarTest {
	private static final int ROWS = 20;
	private static final int COLS = 20;
	private GameMap map;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		// since I don't want to make this constructor public
		Constructor<?> constructor = GameMap.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		map = (GameMap) constructor.newInstance(ROWS, COLS);
	}
	
	
	/**
	 * Tests the smallest path with only a starting point
	 */
	@Test
	public void TestCurrentMinOne(){
		Point start = new Point(0,0);
		Point end   = new Point(5,0);
		
		AStarAgent agent = new AStarAgent(map,start, end);
		
		Path curMin = agent.findCurrentMin();
		
		assertEquals(new Path(map,start),curMin);
	}
	
	/**
	 * Tests the smallest path with only a starting point
	 */
	@Test
	public void TestAdvanceOne(){
		Point start = new Point(0,0);
		Point end   = new Point(5,0);
		
		AStarAgent agent = new AStarAgent(map,start, end);
		
		
		agent.advance();
		Path curMin = agent.findCurrentMin();
		
		Path shouldPath = new Path(map,start);
		shouldPath.addPoint(new Point(1,0));
		
		assertEquals(shouldPath,curMin);
		
		agent.advance();
		curMin = agent.findCurrentMin();
		shouldPath.addPoint(new Point(2,0));
		
		assertEquals(shouldPath,curMin);
	}
	
	@Test
	public void testEntire(){
		Point start = new Point(0,0);
		Point end   = new Point(5,5);
		
		AStarAgent agent = new AStarAgent(map,start, end);
		
		System.out.println(agent.getSmallestPath().toString());
	}
}
