package com.prealpha.aichallenge.protocol;

/**
 * Provides basic game state handling.
 */
public abstract class Bot extends AbstractSystemInputParser {
	private Game game;

	protected Bot() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void setup(int loadTime, int turnTime, int rows, int cols,
			int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
		game = new Game(loadTime, turnTime, rows, cols, turns,
				viewRadius2, attackRadius2, spawnRadius2);
	}

	/**
	 * Returns game state information.
	 * 
	 * @return game state information
	 */
	protected final Game getGameMap() {
		return game;
	}

	@Override
	protected void beforeUpdate() {
		game.setTurnStartTime(System.currentTimeMillis());
		game.clearMyAnts();
		game.clearEnemyAnts();
		game.clearMyHills();
		game.clearEnemyHills();
		game.getFoodTiles().clear();
		game.getOrders().clear();
	}

	@Override
	protected void addWater(int row, int col) {
		game.update(Ilk.WATER, new Point(row, col));
	}

	@Override
	protected void addAnt(int row, int col, int owner) {
		game.update(owner > 0 ? Ilk.ENEMY_ANT : Ilk.MY_ANT, new Point(row,
				col));
	}

	@Override
	protected void addFood(int row, int col) {
		game.update(Ilk.FOOD, new Point(row, col));
	}

	@Override
	protected void removeAnt(int row, int col, int owner) {
		game.update(Ilk.DEAD, new Point(row, col));
	}

	@Override
	protected void addHill(int row, int col, int owner) {
		game.updateHills(owner, new Point(row, col));
	}

	@Override
	protected void afterUpdate() {
	}
}
