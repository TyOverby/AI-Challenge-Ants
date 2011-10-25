package com.prealpha.aichallenge.ants;

import java.util.Comparator;
import java.util.PriorityQueue;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Ilk;
import com.prealpha.aichallenge.protocol.Point;

public class ScoutCounselor {
	private static GameMap gameMap;
	//private static Map<Ant,Point> jobMap = new HashMap<Ant,Point>();

	public static void update(GameMap gameMap){
		ScoutCounselor.gameMap = gameMap;
	}

	public static Point getJob(final Point position,Ant ant){
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

		// Loop through all the points on the map to look for areas that are unexplored.
		// If said area is unexplored, it adds that as a point that is a possible target
		for (int i = 0; i < gameMap.getRows(); i++) {
			for (int j = 0; j < gameMap.getCols(); j++) {
				Point point = new Point(i, j);
				if (gameMap.getIlk(point).isPassable()) {
					int unknownCount = 0;
					for (Point adjacent : gameMap.getAdjacent(point)) {
						if (gameMap.getIlk(adjacent) == Ilk.UNKNOWN) {
							unknownCount++;
						}
					}
					if (unknownCount > 0) {
						targets.add(point);
					}
				}
			}
		}
		
		return targets.peek();
	}

	public static void addAnt(Ant ant){

	}
	public static void removeAnt(Ant ant){
		//jobMap.remove(ant);
	}
}
