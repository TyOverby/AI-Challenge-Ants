package com.prealpha.aichallenge.ants.counselor;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.prealpha.aichallenge.ants.Soldier;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class SoldierCounselor {
	private static GameMap gameMap;
	private static Set<Point> basePoints = new HashSet<Point>();
	private static Set<Soldier> soldiers = new HashSet<Soldier>();

	public static void update(GameMap gameMap){
		SoldierCounselor.gameMap = gameMap;
	}

	public static void addHill(Point location){
		basePoints.add(location);
	}
	public static void removeHill(Point location){
		// If it was a base, tell all the other soldiers
		if(basePoints.remove(location)){
			for(Soldier soldier:soldiers){
				Point newTarget = getTarget();
				if(newTarget==null){
					soldier.setTarget(ScoutCounselor.getJob(soldier));
				}
				else{
					soldier.setTarget(newTarget);
				}
			}
		}
		else{
			// Don't do anything, it was just scouting...
		}
	}
	
	public static void addAnt(Soldier soldier){
		soldiers.add(soldier);
	}
	public static void removeAnt(Soldier soldier){
		soldiers.remove(soldier);
	}

	public static Point getTarget(){
		// We need a point to base the distance off of
		final Point position;
		// If we have hills left
		if(gameMap.getMyHills().size()<0){
			// Target the hill closest to ours
			position = (Point) gameMap.getMyHills().toArray()[0];
		}
		else{
			// Target a hill closest to 0,0
			position = new Point(0,0);
		}

		// This Priority queue prioritizes points that are closer to the ant
		PriorityQueue<Point> targets = new PriorityQueue<Point>(1,
				new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				int d1 = gameMap.getManhattanDistance(position, p1);
				int d2 = gameMap.getManhattanDistance(position, p2);
				return d1 - d2;
			}
		});

		for(Point hill:basePoints){
			targets.add(hill);
		}
		
		return targets.peek();
	}
}
