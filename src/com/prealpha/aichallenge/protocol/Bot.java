package com.prealpha.aichallenge.protocol;

/**
 * Provides basic game state handling.
 */
public abstract class Bot extends AbstractSystemInputParser {

	private GameMap gameMap;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup(int loadTime, int turnTime, int rows, int cols,
			int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
		setAnts(new GameMap(loadTime, turnTime, rows, cols, turns, viewRadius2,
				attackRadius2, spawnRadius2));
	}

	/**
	 * Returns game state information.
	 * 
	 * @return game state information
	 */
	public GameMap getGameMap() {
		return gameMap;
	}

	/**
	 * Sets game state information.
	 * 
	 * @param ants
	 *            game state information to be set
	 */
	protected void setAnts(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeUpdate() {
		gameMap.setTurnStartTime(System.currentTimeMillis());
		gameMap.clearMyAnts();
		gameMap.clearEnemyAnts();
		gameMap.clearMyHills();
		gameMap.clearEnemyHills();
		gameMap.getFoodTiles().clear();
		gameMap.getOrders().clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWater(int row, int col) {
		gameMap.update(Ilk.WATER, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnt(int row, int col, int owner) {
		gameMap.update(owner > 0 ? Ilk.ENEMY_ANT : Ilk.MY_ANT, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addFood(int row, int col) {
		gameMap.update(Ilk.FOOD, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAnt(int row, int col, int owner) {
		gameMap.update(Ilk.DEAD, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addHill(int row, int col, int owner) {
		gameMap.updateHills(owner, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterUpdate() {
	}
}
