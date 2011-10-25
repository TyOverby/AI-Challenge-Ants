package com.prealpha.aichallenge.ants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.PathFinder;
import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

public abstract class BaseAnt extends PathFinder implements Ant {
	protected static final Set<Point> ACTIVE_TARGETS = new HashSet<Point>();

	protected final GameMap map;

	protected Point position;

	protected List<Point> path;

	protected Order order;

	public BaseAnt(GameMap map, Point position) {
		super(map);
		this.map = map;
		this.position = position;
	}

	@Override
	public Order getOrder() {
		order = doGetOrder();
		// If the order is unavailable, recalculate and try again.
		if (order == null) {
			recalculate();
			order = doGetOrder();
		}
		return order;
	}

	/**
	 * 
	 * @return The Order
	 */
	private Order doGetOrder() {
		if (path != null) {
			// The 'index' is the next position
			int index = path.indexOf(position) + 1;
			// Check to make sure we haven't reached our goal
			if (index < path.size()) {
				Point target = path.get(index);
				if (map.getIlk(target).isPassable()) {
					Set<Aim> directions = map.getDirections(position, target);
					if (directions.size() == 1) {
						Aim direction = directions.iterator().next();
						return new Order(position, direction);
					}
				}
			}
			else{
				this.onGoalReached(this.position);
			}
		}
		return null;
	}

	private void recalculate() {
		if (path != null) {
			ACTIVE_TARGETS.remove(path.get(path.size() - 1));
		}
		Point target = getTarget();
		if (target != null) {
			path = findPath(position, target);
			if (path != null && !path.isEmpty()) {
				ACTIVE_TARGETS.add(path.get(path.size() - 1));
			}
		}
	}

	/**
	 * Picks the next point to move to 
	 * @return The destination point
	 */
	protected abstract Point getTarget();

	protected abstract void onGoalReached(Point position);
	/**
	 * If the order is confirmed, then move this ants position to the new position
	 */
	@Override
	public void orderConfirmed() {
		position = order.getTarget(map);
		order = null;
	}

	@Override
	public void die() {
		if (path != null && !path.isEmpty()) {
			ACTIVE_TARGETS.remove(path.get(path.size() - 1));
		}
		MasterAntSpawner.decommissionAnt(this);
	}
}
