/*
 * PathFinderTest.java
 * Copyright (C) 2011 Meyer Kizner, Ty Overby
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public final class WrapAroundTest {
	
	private static final int ROWS = 20;

	private static final int COLS = 20;

	private static final int VIEW_RADIUS_2 = 55;

	private GameMap map;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		// since I don't want to make this constructor public
		Constructor<?> constructor = GameMap.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		map = (GameMap) constructor.newInstance(ROWS, COLS, VIEW_RADIUS_2);
	}

	@Test
	public void testDist(){
		Point start = new Point(0,0);
		Point end = new Point(0,19);
		
		int dist = map.getManhattanDistance(start, end);
		assertEquals(2, dist);
	}
}
