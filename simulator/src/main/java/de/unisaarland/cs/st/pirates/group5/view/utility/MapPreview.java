package de.unisaarland.cs.st.pirates.group5.view.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MapPreview {

	private int x;
	private int y;
	private char[][] map;

	public static String[] teamColors = {"007171","57493D","1A3140","59734E","837C48","BE6D5F","FFFB00", "716C67", "659FA6","8B0D00","D1DC48","A6F2CB", "EE6456", "BF96BC", "AEB660", "FF8C53", "BBFF91", "D9304F",
								   "FC9D9B", "590B00", "5A3C31", "000000", "E6B91F", "1CAF9A", "73436F", "FFFFFF"};

	
	public MapPreview(File mapFile) throws IOException{
		this.map = buildMap(mapFile);
	}
	
	public MapPreview(char[][] map){
		this.map = map;
	}
	
	public MapPreview(){}
	
	
	public void setMap(char[][] map){
		this.map = map;
	}
	
	public char[][] getMap(){
		return map;
	}
	
	public void draw(GraphicsContext gc){
		if(map == null) throw new NullPointerException();
		
		int width = map.length;
    	int height = map[0].length;
    	double scaleW = gc.getCanvas().getWidth()/width;
    	double scaleH = gc.getCanvas().getHeight()/height;
    	
    	gc.setFill(Color.web("76A6A6"));
    	gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	for(int y = 0; y < height; y++){
        	for(int x = 0; x < width; x++){

        		char field = map[x][y];
        		Color color;
        		
        		if(isTeamLetter(field))
        			color = Color.web(teamColors[field - 'a']);
        		else if(field == '.')
        			color = Color.web("76A6A6");
        		else
        			color = Color.web("FCDA69");
        		
        		double step = (y % 2 != 0) ? scaleW/2 : 0;
        		
        		gc.setFill(color);
        		gc.fillRect(scaleW*x + step, scaleH*y, scaleW, scaleH);
        	}		
    	}
	}	
	

	@SuppressWarnings("resource")
	private char[][] buildMap(File mapFile) throws IOException{
		if(mapFile == null)
			mapFile = new File("map.txt");
		
        FileInputStream stream = new FileInputStream(mapFile);
        
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		int width, height;
		char[][] fields;
		
		try{
			width = Integer.parseInt(reader.readLine());
			height = Integer.parseInt(reader.readLine());
			fields = new char[width][height];
		}
		catch(NumberFormatException e){
			reader.close();
			throw new IllegalArgumentException("Submitted coordinates are not valid numbers");
		}
		
		if(height % 2 != 0)
			throw new IllegalArgumentException("Height must be a even number");
	
		String line;
		while((line = reader.readLine()) != null){
			
			line.trim();
			line = line.replaceAll(" ", "");
			
			for(int i = 0; i < line.length(); i++){
				if(y >= height){
					reader.close();
					throw new IllegalArgumentException("Map is bigger than was specified in the first two lines.");
				}
				
				char glyph = line.charAt(i);
				if(glyph == '.' || glyph == '#' || glyph == '$' || glyph == '&' || isDigit(glyph) || isTeamLetter(glyph))
					fields[x][y] = glyph;
				else
					throw new IllegalArgumentException();

				incrementXY(width, height);
			}
		}
		
		reader.close();
		return fields;
    }
	
	
	private boolean isTeamLetter(char c){
		if(c >= 'a' && c <= 'z')
			return true;
		
		return false;
	}

	private boolean isDigit(char c){
		if(c >= '1' && c <= '9')
			return true;
		
		return false;
	}
    
	private void incrementXY(int width, int height) {
		x++;
		if(x >= width)
			y++;
		x = x % width;
	}
}
