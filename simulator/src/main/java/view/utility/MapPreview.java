package view.utility;

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
	private String[] teamColors = {"FFFFFF","FF0040","0000FF","00FFFF","FFFF00","00FF00","000000", "585858", "A9F5F2","F5A9E1","088A29","088A68", "088A68", "086A87", "08298A", "29088A", "6A0888", "8A0868",
								   "3A2F0B", "243B0B", "0B3B24", "0B2F3A", "0B0B3B", "2F0B3A", "3B0B17", "610B21"};
	
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
		
		//TODO: check if y is even
	
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
				char c;
				
				if(glyph == '.' || glyph == '#' || glyph == '$' || glyph == '&' || isDigit(glyph) || isTeamLetter(glyph))
					c = glyph;
				else
					throw new IllegalArgumentException();
				
				fields[x][y] = c;
				incrementXY(width, height);
			}
		}
		
		reader.close();
		return fields;
    }
	
	
	private boolean isTeamLetter(char c){
		if(c >= 'a' && c < 'z')
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
