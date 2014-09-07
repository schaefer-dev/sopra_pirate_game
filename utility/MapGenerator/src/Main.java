import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class Main {

	private static Integer height;
	private static Integer width;
	private static Integer teamsPerMap;
	private static Integer islandSize;
	
	public static void main(String[] args) throws FileNotFoundException{

		if(args[0].equals("generate")){
			
			//Standard configuration: 'generate 200 200 40 5 5 8'
			height = Integer.parseInt(args[1]);
			width  = Integer.parseInt(args[2]);
			Integer islandsCount = Integer.parseInt(args[3]);
			islandSize = Integer.parseInt(args[4]);
			teamsPerMap  = Integer.parseInt(args[5]);
			Integer boatsPerTeam = Integer.parseInt(args[6]);
			
			if(!checkInput()) return;
			
			Generator gen = new Generator(height, width, islandsCount, islandSize, teamsPerMap, boatsPerTeam);
			write(gen.generate());	
		}
		else if(args[0].equals("transform")){
			
			//Standard configuration: 'transform europe.jpg 5 8'
			String mapFile = args[1];
			teamsPerMap  = Integer.parseInt(args[2]);
			Integer boatsPerTeam = Integer.parseInt(args[3]);
			
			Color[][] image = read(mapFile);
			if(image == null) return;
			
			Transformer trans = new Transformer(image);
			char[][] fields = trans.transform();
			height = fields.length;
			width = fields[0].length;
			Generator gen = new Generator(fields, teamsPerMap, boatsPerTeam);

			write(gen.placeObjects());
		}
		else
			System.out.println("args[0] must be either 'generate' to generate a random map or 'transform' to create a map from an image file(args[1])");
	}
	
	
	private static void write(char[][] fields) throws FileNotFoundException{
		
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
	
	public static Color[][] read(String fileName){
		
		BufferedImage image;
		File file = new File(fileName);
		
		try{
			image = ImageIO.read(file);
		}
		catch(IOException e){
			System.out.println("No Image found");
			return null;
		}
		
		if(image.getHeight() < 2 || image.getHeight() > 1999){
			System.out.println("Height must be in range {2...2000}");
			return null;
		}
		if(image.getWidth() < 2 || image.getWidth() > 1999){
			System.out.println("Width must be in range {2...2000}");
			return null;
		}
		
		Color[][] img = new Color[image.getHeight()][image.getWidth()];
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++)
				img[y][x] = new Color(image.getRGB(x, y));
		}	
		return img;
	}
	
	private static boolean checkInput(){
		if(height < 2 || height > 1999){
			System.out.println("Height must be in range {2...2000}");
		return false;
		}
		if(width < 2 || width > 1999){
			System.out.println("Width must be in range {2...2000}");
			return false;
		}
		if(islandSize < 1 || islandSize > 10){
			System.out.println("The island size must be in range {1...10} \n => 1='very large' ... 10='very small'");
			return false;
		}
		if(teamsPerMap < 2 || teamsPerMap > 25){
			System.out.println("Team count must be in range {2...16}");
			return false;
		}
		return true;
	}
	
}
