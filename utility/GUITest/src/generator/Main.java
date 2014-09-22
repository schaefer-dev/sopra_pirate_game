package generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.imageio.ImageIO;

public class Main {

	private static Integer height;
	private static Integer width;
	private static Integer teamsPerMap;
	private static Integer islandSize;
	
	public static void main(String[] args) throws FileNotFoundException{
		switch(args[0]){
			case "generate":
				//Standard configuration: 'generate 199 199 40 5 5 8'
				height = Integer.parseInt(args[1]);
				width  = Integer.parseInt(args[2]);
				Integer islandsCount = Integer.parseInt(args[3]);
				islandSize = Integer.parseInt(args[4]);
				teamsPerMap  = Integer.parseInt(args[5]);
				Integer boatsPerTeam = Integer.parseInt(args[6]);
	
				if(!checkGenerateInput()) return;
				
				Generator gen = new Generator(height, width, islandsCount, islandSize, teamsPerMap, boatsPerTeam);
				write(gen.generate());	
				break;
			case "url":
				if(!createImageFromURL(args[5], args[1])){
					System.out.println("Invalid input");
					return;
				}
			case "transform":
				//Standard configuration: 'transform europe.jpg 200 5 8'
				String mapFile = args[1];
				Integer scale = Integer.parseInt(args[2]);
				teamsPerMap  = Integer.parseInt(args[3]);
				Integer boatsPerTeam1 = Integer.parseInt(args[4]);
				
				Color[][] image = read(mapFile, scale);
				if(image == null) return;
				
				Transformer trans = new Transformer(image);
				char[][] fields = trans.transform();
				height = fields.length;
				width = fields[0].length;
				Generator gen1 = new Generator(fields, teamsPerMap, boatsPerTeam1);

				write(gen1.placeObjects());
				break;
			default:
				System.out.println("args[0] must be either 'generate' to generate a random map or 'transform' to create a map from an image file(args[1])");
		}			
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
	
	public static Color[][] read(String fileName, int scale){
		
		File file = new File(fileName);
		Image img;
		int scaledWidth, scaledHeight;
		
		try{
			img = ImageIO.read(file);
			
			scaledWidth = scale;
			float h;
			if(img.getHeight(null) < img.getWidth(null))
				h = ((float)img.getHeight(null)/(float)img.getWidth(null))*scale;
			else
				h = ((float)img.getWidth(null)/(float)img.getHeight(null))*scale;
			scaledHeight = (int)h;
			
			img = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
		}
		catch(IOException e){
			System.out.println("No Image found");
			return null;
		}
		
		BufferedImage image = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		Color[][] colors = new Color[image.getHeight()][image.getWidth()];
		for(int y = 0; y < image.getHeight(); y++){
			for(int x = 0; x < image.getWidth(); x++)
				colors[y][x] = new Color(image.getRGB(x, y));
		}	
		return colors;
	}
	
	public static boolean createImageFromURL(String location, String fileLocation){
		
		location = location.replaceAll(" ", "");
		try{
		    BufferedImage img = ImageIO.read(new URL("http://maps.googleapis.com/maps/api/staticmap?center="+location+"+&zoom=5&scale=false&size=600x600&maptype=terrain&sensor=false&format=png&visual_refresh=true"));
		    File outputfile = new File(fileLocation);
		    ImageIO.write(img, "png", outputfile);
		    return true;
		} 
		catch(Exception e) {
			System.out.println(e);
			return false;
		}	
	}
	
	private static boolean checkGenerateInput(){
		if(height < 2 || height > 199){
			System.out.println("Height must be in range {2...200}");
			return false;
		}
		if(width < 2 || width > 199){
			System.out.println("Width must be in range {2...200}");
			return false;
		}
		if(islandSize < 1 || islandSize > 10){
			System.out.println("The island size must be in range {1...10} \n => 1='very large' ... 10='very small'");
			return false;
		}
		if(teamsPerMap < 2 || teamsPerMap > 26){
			System.out.println("Team count must be in range {2...26}");
			return false;
		}
		return true;
	}
}
