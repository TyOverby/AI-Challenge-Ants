package com.prealpha.aichallenge.protocol;

/**
 * Provides basic game state handling.
 */
public abstract class Bot extends AbstractSystemInputParser {
	private Game game;

	private Map map;

	protected Bot() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void setup(int loadTime, int turnTime, int rows, int cols,
			int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
		map = new Map(rows, cols);
		game = new Game(map, loadTime, turnTime, turns, viewRadius2,
				attackRadius2, spawnRadius2);
	}

	/**
	 * Returns game state information.
	 * 
	 * @return game state information
	 */
	protected final Game getGame() {
		return game;
	}

	/**
	 * Returns the game map.
	 * 
	 * @return the game map
	 */
	protected final Map getMap() {
		return map;
	}

	@Override
	protected void beforeUpdate() {
		game.setTurnStartTime(System.currentTimeMillis());
		game.clearOrders();
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
		map.update(new Point(row, col), owner > 0 ? Ilk.ENEMY_ANT : Ilk.MY_ANT);
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
	}
}
