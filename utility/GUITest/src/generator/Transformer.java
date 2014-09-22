package generator;

import java.awt.Color;


public class Transformer {

	private final char emptyIsland = '#';
	private final char water = '.';
	
	private int height;
	private int width;
	
	private Color[][] image;
	private char[][] fields;
	
	
	public Transformer(Color[][] img){
		
		image = img;
		height = image.length;
		width = image[0].length;
		fields = new char[height][width];
	}
	
	
	public char[][] transform(){
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				
				int r = image[y][x].getRed();
				int g = image[y][x].getGreen();
				int b = image[y][x].getBlue();
				int black = 120;
				
				if((r < b) && (g < b))
					fields[y][x] = water;
				else if((r < black) && (g < black) && (b < black))
					fields[y][x] = water;
				else if(b < 50)
					fields[y][x] = water;
				else
					fields[y][x] = emptyIsland;
			}
		}	
		return fields;
	}
}
