package com.prealpha.aichallenge.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.prealpha.aichallenge.protocol.Point;

public class PathTest {
	@Test
	public void testPathDistance(){
		Point start = new Point(0,0);
		Point end = new Point(0,5);
		
		Path path = new Path(start);
		assert(path.getTravledDistance()==0);
		assert(path.getEstimatedRemainingDistance(end)==5);
		assert(path.getTotalDist(end)==5);
	}
	@Test
	public void testPathDistanceTwo(){
		Point start = new Point(0,0);
		Point next = new Point(0,-1);
		Point end = new Point(0,5);
		
		List<Point> pathset = new ArrayList<Point>();
		pathset.add(start);
		pathset.add(next);
		
		Path path = new Path(pathset);
		
		assert(path.getTravledDistance()==1);
		assert(path.getEstimatedRemainingDistance(end)==6);
		assert(path.getTotalDist(end)==7);
	}
	
	@Test
	public void testGetHead(){
		Point start = new Point(0,0);
		
		Path path = new Path(start);
		
		assert(path.getHead().equals(start));
	}
	@Test 
	public void testGetHeadTwo(){
		Point start = new Point(0,0);
		Point next = new Point(0,1);
		
		Path path = new Path(start,next);
		
		assert(path.getHead().equals(next));
	}
	
	
	@Test
	public void testPathBranch(){
		Point start = new Point(0,0);
		Path curPath = new Path(start);
		
		Set<Path> newPaths = new HashSet<Path>();
		newPaths.add(new Path(start,new Point(0,1)));
		newPaths.add(new Path(start,new Point(0,-1)));
		newPaths.add(new Path(start,new Point(1,0)));
		newPaths.add(new Path(start,new Point(-1,0)));
		
		assert(curPath.getNewPathsFromHead().equals(newPaths));
	}
}
