package com.prealpha.aichallenge.protocol;

/**
 * Provides basic game state handling.
 */
public abstract class Bot extends AbstractSystemInputParser {
	private GameMap ants;

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
	public GameMap getAnts() {
		return ants;
	}

	/**
	 * Sets game state information.
	 * 
	 * @param ants
	 *            game state information to be set
	 */
	protected void setAnts(GameMap ants) {
		this.ants = ants;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeUpdate() {
		ants.setTurnStartTime(System.currentTimeMillis());
		ants.clearMyAnts();
		ants.clearEnemyAnts();
		ants.clearMyHills();
		ants.clearEnemyHills();
		ants.getFoodTiles().clear();
		ants.getOrders().clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWater(int row, int col) {
		ants.update(Ilk.WATER, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnt(int row, int col, int owner) {
		ants.update(owner > 0 ? Ilk.ENEMY_ANT : Ilk.MY_ANT, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addFood(int row, int col) {
		ants.update(Ilk.FOOD, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAnt(int row, int col, int owner) {
		ants.update(Ilk.DEAD, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addHill(int row, int col, int owner) {
		ants.updateHills(owner, new Point(row, col));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterUpdate() {
	}
}
