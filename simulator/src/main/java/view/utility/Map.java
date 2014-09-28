package view.utility;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Map {

	private Camera cam;
	private char[][] map;
	private int width;
	private int height;
	
	private String[] teamColors = {"3C704C", "BD5E5C", "01635E", "611846", "F17140", "3DE0F2", "A65639", "1A232A", "708027", "F85F94",
								   "E6922C", "2C151B", "AD1730", "8C1C1C", "ED7802", "3D3E40", "2C151B", "FECB00"};
	
	
	public Map(char[][] map, Camera cam){
		if(map == null || cam == null) throw new NullPointerException();
		
		this.cam = cam;
		this.map = map;
		width = map.length;
		height = map[0].length;
	}
	
	
	public char[][] getMap(){
		return map;
	}
	
	public void draw(GraphicsContext gc){
		width  = Math.abs(cam.b - cam.a);
		height = Math.abs(cam.d - cam.c);
    	double scaleW = gc.getCanvas().getWidth()/width;
    	double scaleH = gc.getCanvas().getHeight()/height;
    	
    	gc.setFill(Color.web("76A6A6"));
    	gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    	
    	for(int y = 0; y < height; y++){
        	for(int x = 0; x < width; x++){

        		char field = map[x+cam.a][y+cam.c];
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
	
	private boolean isTeamLetter(char c){
		if(c >= 'a' && c < 'z')
			return true;
		
		return false;
	}
}
