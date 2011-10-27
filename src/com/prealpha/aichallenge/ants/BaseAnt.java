package com.prealpha.aichallenge.ants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.prealpha.aichallenge.PathFinder;
import com.prealpha.aichallenge.ants.counselor.MasterAntAllocator;
import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

public abstract class BaseAnt extends PathFinder implements Ant {
	protected static final Set<Point> ACTIVE_TARGETS = new HashSet<Point>();

	protected Stack<Point> targets = new Stack<Point>();
	
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
		// If the current path that we are on isn't going to the first target in our list
		if(!path.get(path.size()-1).equals(targets.peek())){
			// Then recalculate with the new target
			this.path=findPath(position, targets.peek());
		}
		
		
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
		Point target = getTarget();
		if (target != null) {
			path = findPath(position, target);
		}
	}
	
	@Override 
	public void setTarget(Point target){
		this.targets.push(target);
	}
	@Override
	public void addTarget(Point target){
		this.targets.add(target);
	}

	@Override
	public Point getLocation(){
		return this.position;
	}
	
	/**
	 * Picks the next point to move to 
	 * @return The destination point
	 */
	protected abstract Point getTarget();

	protected void onGoalReached(Point position){
		this.targets.pop();
	}
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
		MasterAntAllocator.decommissionAnt(this);
	}
}
