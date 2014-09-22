package Events;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import generator.Generator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GenerateEvent implements EventHandler<ActionEvent> {
	
	private SliderListener mapSize, islandCount, islandSize;
	
	
	public GenerateEvent(SliderListener mapSize, SliderListener islandCount, SliderListener islandSize) {
		this.mapSize = mapSize;
		this.islandCount = islandCount;
		this.islandSize = islandSize;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		
		Integer height = mapSize.get();
		Integer width  = mapSize.get();
		int isCount = islandCount.get();
		int isSize  = islandSize.get();
		
		Generator gen = new Generator(height, width, isCount, isSize, 0, 0);
		char[][] fields = gen.generate();
		
		try {
			PrintWriter out = new PrintWriter("map.txt");
			out.write(height.toString() + '\n');
			out.write(width.toString() + '\n');
			
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
					String s = Character.toString(fields[y][x]);
					out.write(s + ' ');
				}	
				out.write('\n');
				
				if(y%2 == 0)
					out.write(' ');
			}
			out.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
