package com.prealpha.aichallenge.protocol;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a tile of the game map.
 */
public class Point {
	public static Point bounds;
	public static void setBounds(Point p){
		bounds = p;
	}
	
    private final int row;
    private final int col;
    
    /**
     * Creates new {@link Tile} object.
     * 
     * @param row row index
     * @param col column index
     */
    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public Point getModPoint(Aim aim){
    	int newRow = (this.row+aim.getRowDelta());
    	int newCol = (this.col+aim.getColDelta());
    	
    	return new Point(newRow,newCol);
    }
    
    public Set<Point> getNeighbors(){
    	Set<Point> toReturn = new HashSet<Point>(4);
    	for(Aim aim:Aim.values()){
    		toReturn.add(this.getModPoint(aim));
    	}    	
    	
    	return toReturn;
    }
    
    /**
     * Returns row index.
     * 
     * @return row index
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Returns column index.
     * 
     * @return column index
     */
    public int getCol() {
        return col;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return row * GameMap.MAX_MAP_SIZE + col;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Point) {
        	Point tile = (Point)o;
            result = row == tile.row && col == tile.col;
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return row + " " + col;
    }
}