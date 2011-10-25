package com.prealpha.aichallenge.ants;

import java.util.HashMap;
import java.util.Map;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class MasterAntSpawner {
	public static Map<Point, Ant> ants = new HashMap<Point, Ant>();
	
	static int totalAntCount = 0;
	static int hunterCount = 0;
	static int scoutCount = 0;
	static int soldierCount = 0;

	public static void addAnt(Point position, GameMap gameMap){
		totalAntCount++;
		if (!ants.containsKey(position)) {
			Ant ant;
			
		
			if(totalAntCount<5){
				ant = new Hunter(gameMap,position);
				hunterCount++;
			}
			else{
				if(totalAntCount%2==0){
					ant = new Hunter(gameMap,position);
					hunterCount++;
				}
				else{
					if(totalAntCount>30){
						ant = new Soldier(gameMap,position);
						soldierCount--;
					}
					else{
						ant = new Scout(gameMap,position);
						scoutCount--;
					}
				}
			}
			ants.put(position, ant);
		}
	}
	
	public static void decommissionAnt(Ant ant){
		if(ant instanceof Hunter){
			HunterCounselor.releaseAnt(ant);
			hunterCount --;
			return;
		}
		if(ant instanceof Scout){
			scoutCount --;
			return;
		}
		if(ant instanceof Soldier){
			soldierCount --;
			return;
		}
	}

	public static void removeAnt(int row, int col) {
		Point point = new Point(row, col);
		if (ants.containsKey(point)) {
			Ant ant = ants.get(point);
			ant.die();
			ants.remove(point);
		}
	}
}
