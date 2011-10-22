package com.prealpha.aichallenge.protocol;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a location of the game map.
 */
public class Point {
	public static Point dimensions;
	public static void setDims(Point dim){
		Point.dimensions = dim;
	}
	
    private final int y;
    
    private final int x;
    
    /**
     * Creates new {@link Point} object.
     * 
     * @param row row index
     * @param col column index
     */
    public Point(int row, int col) {
        this.y = row;
        this.x = col;
    }
    
    /**
     * Returns row index.
     * 
     * @return row index
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Returns column index.
     * 
     * @return column index
     */
    public int getX() {
        return this.x;
    }
    
    public Point getPosFromAim(Aim aim){
    	int newX = this.x+aim.getXDelta();
    	int newY = this.y+aim.getYDelta();
    	return new Point(newX,newY);
    }
    
    public Set<Point> getPossiblePoints(){
    	Set<Point> toReturn = new HashSet<Point>();
    	
    	for(Aim a:Aim.values()){
    		toReturn.add(getPosFromAim(a));
    	}
    	return toReturn;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return y * GameMap.MAX_MAP_SIZE + x;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Point) {
            Point tile = (Point)o;
            result = y == tile.y && x == tile.x;
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "("+ y + "," + x+")";
    }

	public static float distance(Point point, Point destination) {
		int deltaX = point.x-destination.x;
		int deltaY = point.y-destination.y;
		
		return (float) Math.sqrt(Math.abs(deltaX*deltaX+deltaY*deltaY));
	}
}