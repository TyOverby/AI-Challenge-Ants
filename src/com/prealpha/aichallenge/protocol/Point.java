package com.prealpha.aichallenge.protocol;

/**
 * Represents a tile of the game map.
 */
public final class Point {
	private final int row;

	private final int col;

	/**
	 * Creates new {@link Tile} object.
	 * 
	 * @param row
	 *            row index
	 * @param col
	 *            column index
	 */
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Returns row index.
	 * 
	 * @return row index
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns column index.
	 * 
	 * @return column index
	 */
	public int getCol() {
		return col;
	}

	@Override
	public int hashCode() {
		return row * Game.MAX_MAP_SIZE + col;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Point) {
			Point tile = (Point) o;
			result = row == tile.row && col == tile.col;
		}
		return result;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
}
