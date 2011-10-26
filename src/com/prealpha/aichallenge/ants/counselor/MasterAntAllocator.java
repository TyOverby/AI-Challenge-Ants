package com.prealpha.aichallenge.ants.counselor;

import java.util.HashMap;
import java.util.Map;

import com.prealpha.aichallenge.ants.Ant;
import com.prealpha.aichallenge.ants.DumbAnt;
import com.prealpha.aichallenge.ants.Hunter;
import com.prealpha.aichallenge.ants.Scout;
import com.prealpha.aichallenge.ants.Soldier;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class MasterAntAllocator {
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
						SoldierCounselor.addAnt((Soldier) ant);
						soldierCount++;
					}
					else{
						ant = new Scout(gameMap,position);
						scoutCount++;
					}
				}
			}
			if(hunterCount<10){
				ant = new Hunter(gameMap,position);
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
	
	public static Ant swap(Ant ant, Class<Ant> newClass,GameMap gameMap){
		removeAnt(ant);
		if(newClass.equals(Hunter.class)){
			return new Hunter(gameMap,ant.getLocation());
		}
		else if(newClass.equals(Soldier.class)){
			return new Soldier(gameMap,ant.getLocation());
		}	
		else if(newClass.equals(Scout.class)){
			return new Scout(gameMap,ant.getLocation());
		}	
		else if(newClass.equals(DumbAnt.class)){
			return new Scout(gameMap,ant.getLocation());
		}
		else{
			throw new IllegalArgumentException();
		}
	}

	public static void removeAnt(Ant ant){
		removeAnt(ant.getLocation().getRow(), ant.getLocation().getCol());
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
