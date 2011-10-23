/*
 * RoleTest.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Ilk;
import com.prealpha.aichallenge.protocol.Point;

public final class RoleTest {
	private static final int ROWS = 20;

	private static final int COLS = 20;

	private Role role;

	private GameMap map;

	private GameMap obstacleMap;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		// since I don't want to make this constructor public
		Constructor<?> constructor = GameMap.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		map = (GameMap) constructor.newInstance(ROWS, COLS);
		obstacleMap = (GameMap) constructor.newInstance(ROWS, COLS);
		for (Method method : GameMap.class.getDeclaredMethods()) {
			if (method.getName().equals("update")) {
				method.setAccessible(true);
				method.invoke(obstacleMap, new Point(5, 4), Ilk.WATER);
				method.invoke(obstacleMap, new Point(4, 5), Ilk.WATER);
			}
		}
	}

	@Test
	public void testFindPath() {
		role = new Role(map, new Point(0, 0), new Point(5, 5)) {
		};
		Path path = role.findPath();
		assertEquals(10, path.getGeneration());
	}

	@Test
	public void testFindPathObstacles() {
		role = new Role(obstacleMap, new Point(0, 0), new Point(5, 5)) {
		};
		Path path = role.findPath();
		assertEquals(12, path.getGeneration());
	}
}
