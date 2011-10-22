package com.prealpha.aichallenge.protocol;

/**
 * Provides basic game state handling.
 */
public abstract class Bot extends AbstractSystemInputParser {
	private GameMap gameMap;

	protected Bot() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void setup(int loadTime, int turnTime, int rows, int cols,
			int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
		gameMap = new GameMap(loadTime, turnTime, rows, cols, turns,
				viewRadius2, attackRadius2, spawnRadius2);
	}

	/**
	 * Returns game state information.
	 * 
	 * @return game state information
	 */
	protected final GameMap getGameMap() {
		return gameMap;
	}

	@Override
	protected void beforeUpdate() {
		gameMap.setTurnStartTime(System.currentTimeMillis());
		gameMap.clearMyAnts();
		gameMap.clearEnemyAnts();
		gameMap.clearMyHills();
		gameMap.clearEnemyHills();
		gameMap.getFoodTiles().clear();
		gameMap.getOrders().clear();
	}

	@Override
	protected void addWater(int row, int col) {
		gameMap.update(Ilk.WATER, new Point(row, col));
	}

	@Override
	protected void addAnt(int row, int col, int owner) {
		gameMap.update(owner > 0 ? Ilk.ENEMY_ANT : Ilk.MY_ANT, new Point(row,
				col));
	}

	@Override
	protected void addFood(int row, int col) {
		gameMap.update(Ilk.FOOD, new Point(row, col));
	}

	@Override
	protected void removeAnt(int row, int col, int owner) {
		gameMap.update(Ilk.DEAD, new Point(row, col));
	}

	@Override
	protected void addHill(int row, int col, int owner) {
		gameMap.updateHills(owner, new Point(row, col));
	}

	@Override
	protected void afterUpdate() {
	}
}
