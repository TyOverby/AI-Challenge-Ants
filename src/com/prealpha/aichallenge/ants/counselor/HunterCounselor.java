package com.prealpha.aichallenge.ants.counselor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.prealpha.aichallenge.ants.Ant;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class HunterCounselor {
	private static GameMap gameMap;
	private static Map<Ant,Point> jobMap = new HashMap<Ant,Point>();

	public static void update(GameMap gameMap){
		HunterCounselor.gameMap = gameMap;
	}

	public static Point getJob(final Point position,Ant ant){
		PriorityQueue<Point> targets = new PriorityQueue<Point>(1,
				new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				int d1 = gameMap.getManhattanDistance(position, p1);
				int d2 = gameMap.getManhattanDistance(position, p2);
				return d1 - d2;
			}
		});

		// Loop through all the food tiles
		// If an ant doesn't already have it, then add it to the list of possible targets
		for (Point p: gameMap.getFoodTiles()) {
			if(!jobMap.values().contains(p)){
				targets.add(p);
			}
		}
		
		Point target = targets.peek();
		jobMap.put(ant, target);
		return target;
	}

	public static void addAnt(Ant ant){
		
	}
	public static void releaseAnt(Ant ant){
		jobMap.remove(ant);
	}
}
