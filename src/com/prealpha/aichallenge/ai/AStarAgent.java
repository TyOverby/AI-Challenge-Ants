package com.prealpha.aichallenge.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.prealpha.aichallenge.protocol.Point;

public class AStarAgent {
	private final Point start,end;
	
	private final List<Path> paths = new ArrayList<Path>();
	private final Set<Point> exploredPoints = new HashSet<Point>();
	
	public AStarAgent(Point start, Point end){
		this.start = start;
		this.end = end;
		
		paths.add(new Path(this.start));
		exploredPoints.add(this.start);
	}
	
	public Path findCurrentMin(){
		
		// Find the smallest current path
		Path curMin = paths.get(0);
		for(Path p:paths){
			// Check to see if we won yet
			if(p.getHead().equals(end)){
				return p;
			}
			// Otherwise, see if it is the smallest
			else if(p.getTotalDist(end)<curMin.getTotalDist(end)){
				curMin = p;
			}
		}
		
		return curMin;		
	}
}
