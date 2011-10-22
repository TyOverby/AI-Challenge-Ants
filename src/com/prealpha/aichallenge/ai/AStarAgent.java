package com.prealpha.aichallenge.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class AStarAgent {
	private final Point start, end;

	private final List<Path> paths = new ArrayList<Path>();

	private final Set<Point> exploredPoints = new HashSet<Point>();

	public AStarAgent(GameMap map,Point start, Point end) {
		this.start = start;
		this.end = end;

		paths.add(new Path(map,this.start));
		exploredPoints.add(this.start);
	}	

	public Path findCurrentMin(){

		// Find the smallest current path
		Path curMin = paths.get(0);
		for(Path p:paths){
			// see if it is the smallest
			if(p.getTotalDist(end)<curMin.getTotalDist(end)){
				curMin = p;
			}
		}

		return curMin;		
	}

	public void advance(){
		Path minPath = findCurrentMin();

		// Add all of its children 
		paths.addAll(minPath.getNewPathsFromHead());
		paths.remove(minPath);
	}

	public Path getSmallestPath() {
		Path minPath = findCurrentMin();

		// Add all of its children 
		paths.addAll(minPath.getNewPathsFromHead());
		paths.remove(minPath);

		if(minPath.getHead().equals(end)){
			return minPath;
		}
		else{
			return getSmallestPath();
		}
	}
}
