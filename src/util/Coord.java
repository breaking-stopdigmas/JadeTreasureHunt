package util;

import java.util.Scanner;

public class Coord{
	public int x;
	public int y;
	
	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Coord(){
		this(0, 0);
	}
	public double distanceTo(Coord other){
		int dx = x - other.x;
		int dy = y - other.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public void move(String direction) {
		switch (direction) {
		case "up":
			y -= 1;
			break;
		case "down":
			y += 1;
			break;
		case "left":
			x -= 1;
			break;
		case "right":
			x += 1;
		default:
			break;
		}
	}
	
	public String determineDirectionTo() {
		@SuppressWarnings("resource")
		Scanner read = new Scanner(System.in);
		String movement = read.next();
		return movement;
	}
	
	public boolean equals(Coord other){
		return (x == other.x) && (y == other.y);
	}
	
	@Override
	public Coord clone(){
		return new Coord(x, y);
	}
	
	@Override
	public String toString(){
		return "[" + x + "; "+ y +"]";
	}
	
}