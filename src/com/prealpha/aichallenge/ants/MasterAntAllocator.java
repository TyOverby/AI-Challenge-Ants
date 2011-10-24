package com.prealpha.aichallenge.ants;

import java.util.HashMap;
import java.util.Map;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class MasterAntAllocator {
	public static Map<Point, Ant> ants = new HashMap<Point, Ant>();
	static int totalAntCount = 0;

	public static void addAnt(Point position, GameMap gameMap){
		totalAntCount++;
		if (!ants.containsKey(position)) {
			Ant ant;

			if(totalAntCount<3){
				ant = new Hunter(gameMap,position);
			}
			else{
				if(totalAntCount%2==0){
					ant = new Hunter(gameMap,position);
				}
				else{
					ant = new Scout(gameMap,position);
				}
			}
			ants.put(position, ant);
		}
	}
	
	public static void removeAnt(int row, int col, int owner) {
		if (owner == 0) {
			Point point = new Point(row, col);
			if (ants.containsKey(point)) {
				ants.get(point).die();
				ants.remove(point);
			}
		}
	}
}
