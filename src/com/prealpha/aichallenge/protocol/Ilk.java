package com.prealpha.aichallenge.protocol;

/**
 * Represents type of tile on the game map.
 */
public enum Ilk {
	/** We've never had visibility on this area. */
	UNKNOWN(false),

	/** Water tile. */
	WATER(false),

	/** Food tile. */
	FOOD(true),

	/** Land tile. */
	LAND(true),

	/** Dead ant tile. */
	DEAD(true),

	/** My ant tile. */
	MY_ANT(true),

	/** Enemy ant tile. */
	ENEMY_ANT(true);

	private final boolean passable;

	private Ilk(boolean passable) {
		this.passable = passable;
	}

	/**
	 * Checks if this type of tile is passable, meaning that it could be
	 * possible to move there. Note that this does not refer to whether we can
	 * move <i>this</i> turn.
	 * 
	 * @return {@code true} if this tile is potentially passable, otherwise
	 *         {@code false}
	 */
	public boolean isPassable() {
		return passable;
	}
}
