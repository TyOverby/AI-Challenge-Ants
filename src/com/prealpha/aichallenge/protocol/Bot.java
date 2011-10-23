package com.prealpha.aichallenge.protocol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Provides basic game state handling.
 */
public abstract class Bot extends AbstractSystemInputParser {
	private final Map<Point, Order> origins;

	private final Map<Point, Order> targets;

	private Game game;

	private GameMap map;

	protected Bot() {
		origins = new HashMap<Point, Order>();
		targets = new HashMap<Point, Order>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	final void setup(int loadTime, int turnTime, int rows, int cols, int turns,
			int viewRadius2, int attackRadius2, int spawnRadius2) {
		map = new GameMap(rows, cols, viewRadius2);
		game = new Game(map, loadTime, turnTime, turns, attackRadius2,
				spawnRadius2);
	}

	/**
	 * Returns game state information.
	 * 
	 * @return game state information
	 */
	protected final Game getGame() {
		if (game == null) {
			throw new IllegalStateException();
		}
		return game;
	}

	/**
	 * Returns the game map.
	 * 
	 * @return the game map
	 */
	protected final GameMap getMap() {
		if (game == null) {
			throw new IllegalStateException();
		}
		return map;
	}

	@Override
	protected void beforeUpdate() {
		origins.clear();
		targets.clear();
		game.setTurnStartTime(System.currentTimeMillis());
		map.clearMyAnts();
		map.clearEnemyAnts();
		map.clearMyHills();
		map.clearEnemyHills();
		map.clearFoodTiles();
	}

	@Override
	protected void addWater(int row, int col) {
		map.update(new Point(row, col), Ilk.WATER);
	}

	@Override
	protected void addAnt(int row, int col, int owner) {
		Point point = new Point(row, col);
		map.update(point, owner > 0 ? Ilk.ENEMY_ANT : Ilk.MY_ANT);
	}

	@Override
	protected void addFood(int row, int col) {
		map.update(new Point(row, col), Ilk.FOOD);
	}

	@Override
	protected void removeAnt(int row, int col, int owner) {
		map.update(new Point(row, col), Ilk.DEAD);
	}

	@Override
	protected void addHill(int row, int col, int owner) {
		map.updateHills(new Point(row, col), owner);
	}

	@Override
	protected void afterUpdate() {
		map.updateVisible();
	}

	@Override
	protected final void finishTurn() {
		for (Point ant : map.getMyAnts()) {
			if (!origins.containsKey(ant) && targets.containsKey(ant)) {
				targets.remove(ant);
			}
		}
		for (Order order : targets.values()) {
			System.out.println(order);
		}
		System.out.flush();
		super.finishTurn();
	}

	protected final void issueOrder(Order order) {
		Point origin = order.getOrigin();
		Point target = order.getTarget(map);
		if (!targets.containsKey(target)) {
			if (origins.containsKey(origin)) {
				Order other = origins.get(origin);
				Point otherTarget = other.getTarget(map);
				origins.remove(origin);
				targets.remove(otherTarget);
			}
			origins.put(origin, order);
			targets.put(target, order);
		}
	}

	protected final Set<Order> getOrders() {
		return new HashSet<Order>(targets.values());
	}
}
