/*
 * AvoidingPathFinderTest.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public final class AvoidingPathFinderTest {
	private static final int ROWS = 20;

	private static final int COLS = 20;

	private Game game;

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Constructor<?> mapConstructor = GameMap.class.getDeclaredConstructors()[0];
		mapConstructor.setAccessible(true);
		GameMap map = (GameMap) mapConstructor.newInstance(ROWS, COLS);

		Constructor<?> gameConstructor = Game.class.getDeclaredConstructors()[0];
		gameConstructor.setAccessible(true);
		game = (Game) gameConstructor.newInstance(map, 3000, 1000, 500, 55, 5,
				1);
	}

	@Test
	public void testFindPath() {
		PathFinder pathFinder = new AvoidingPathFinder(game, new Point(4, 5));
		List<Point> path = pathFinder
				.findPath(new Point(0, 0), new Point(5, 5));
		assertEquals(14, path.size());
	}

	@Test
	public void testExplore() {
		AvoidingPathFinder pathFinder = new AvoidingPathFinder(game, new Point(
				0, 0));
		List<Point> path = pathFinder.explore(new Point(0, 0));
		assertTrue(Math.sqrt(game.getViewRadius2()) < path.size());
	}
}
