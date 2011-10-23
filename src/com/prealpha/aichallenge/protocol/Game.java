package com.prealpha.aichallenge.protocol;

/**
 * Holds all game data and current game state.
 */
public final class Game {
	/**
	 * Maximum map size.
	 */
	public static final int MAX_MAP_SIZE = 256;

	private final GameMap map;

	private final int loadTime;

	private final int turnTime;

	private final int turns;

	private final int viewRadius2;

	private final int attackRadius2;

	private final int spawnRadius2;

	private long turnStartTime;

	/**
	 * Creates new {@link Game} object.
	 * 
	 * @param map
	 *            the game map
	 * @param loadTime
	 *            timeout for initializing and setting up the bot on turn 0
	 * @param turnTime
	 *            timeout for a single game turn, starting with turn 1
	 * @param turns
	 *            maximum number of turns the game will be played
	 * @param viewRadius2
	 *            squared view radius of each ant
	 * @param attackRadius2
	 *            squared attack radius of each ant
	 * @param spawnRadius2
	 *            squared spawn radius of each ant
	 */
	Game(GameMap map, int loadTime, int turnTime, int turns, int viewRadius2,
			int attackRadius2, int spawnRadius2) {
		this.map = map;
		this.loadTime = loadTime;
		this.turnTime = turnTime;
		this.turns = turns;
		this.viewRadius2 = viewRadius2;
		this.attackRadius2 = attackRadius2;
		this.spawnRadius2 = spawnRadius2;
	}

	/**
	 * Returns the game map.
	 * 
	 * @return the game map
	 */
	public GameMap getMap() {
		return map;
	}

	/**
	 * Returns timeout for initializing and setting up the bot on turn 0.
	 * 
	 * @return timeout for initializing and setting up the bot on turn 0
	 */
	public int getLoadTime() {
		return loadTime;
	}

	/**
	 * Returns timeout for a single game turn, starting with turn 1.
	 * 
	 * @return timeout for a single game turn, starting with turn 1
	 */
	public int getTurnTime() {
		return turnTime;
	}

	/**
	 * Returns maximum number of turns the game will be played.
	 * 
	 * @return maximum number of turns the game will be played
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * Returns squared view radius of each ant.
	 * 
	 * @return squared view radius of each ant
	 */
	public int getViewRadius2() {
		return viewRadius2;
	}

	/**
	 * Returns squared attack radius of each ant.
	 * 
	 * @return squared attack radius of each ant
	 */
	public int getAttackRadius2() {
		return attackRadius2;
	}

	/**
	 * Returns squared spawn radius of each ant.
	 * 
	 * @return squared spawn radius of each ant
	 */
	public int getSpawnRadius2() {
		return spawnRadius2;
	}

	/**
	 * Sets turn start time.
	 * 
	 * @param turnStartTime
	 *            turn start time
	 */
	void setTurnStartTime(long turnStartTime) {
		this.turnStartTime = turnStartTime;
	}

	/**
	 * Returns how much time the bot has still has to take its turn before
	 * timing out.
	 * 
	 * @return how much time the bot has still has to take its turn before
	 *         timing out
	 */
	public int getTimeRemaining() {
		return turnTime - (int) (System.currentTimeMillis() - turnStartTime);
	}
}
