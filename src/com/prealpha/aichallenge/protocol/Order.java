package com.prealpha.aichallenge.protocol;

/**
 * Represents an order to be issued.
 */
public final class Order {
	private final Point point;

	private final Aim direction;

	/**
	 * Creates new {@link Order} object.
	 * 
	 * @param point
	 *            map tile with my ant
	 * @param direction
	 *            direction in which to move my ant
	 */
	public Order(Point point, Aim direction) {
		this.point = point;
		this.direction = direction;
	}

	public Point getPoint() {
		return point;
	}

	public Point getTarget(GameMap map) {
		return map.getPoint(point, direction);
	}

	@Override
	public String toString() {
		return String.format("o %d %d %s", point.getRow(), point.getCol(),
				direction);
	}
}
