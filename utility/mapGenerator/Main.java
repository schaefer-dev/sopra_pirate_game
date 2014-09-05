import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

	private static final char emptyIsland = '#';
	private static final char water = '.';
	private static final char treasure = '$';
	
	private static final double treasureChance = 0.3;
	private static final double baseChance = 0.7;
	private static final double islandChance = 0.05;
	private static final double islandSize = 4.6;

	private static Integer height;
	private static Integer width;
	private static Integer teamsPerMap;
	private static Integer boatsPerTeam;
	private static int[] bases;
	private static char[][] fields;
	
	public static void main(String[] args) throws FileNotFoundException{
				
		height = Integer.parseInt(args[0]);
		width  = Integer.parseInt(args[1]);
		teamsPerMap  = Integer.parseInt(args[2]);
		boatsPerTeam = Integer.parseInt(args[3]);
		bases = new int[teamsPerMap];
		fields = new char[height][width];
		
		write(build());
	}
	
	
	private static char[][] build(){
		Random gen = new Random();
		
		//Set world
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
			
				float pick = gen.nextFloat();
				double isChance = islandChance;
				
				if(fields[mod(y-1, height)][x] == emptyIsland)
					isChance *= islandSize;
				if(fields[y][mod(x-1, width)] == emptyIsland)
					isChance *= islandSize*2;
				if(fields[mod(y-1, height)][mod(x-1, width)] == emptyIsland)
					isChance *= 1.25;
				
				if(pick < isChance)
					fields[y][x] = emptyIsland;
				else
					fields[y][x] = water;
			}
		}
		
		//Set treasures
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				
				float pick = gen.nextFloat();
				if((fields[y][x] == emptyIsland) && (pick < treasureChance))
					fields[y][x] = treasure;
			}
		}
		
		//Set bases
		for(int i = 0; i < bases.length; i++){
			boolean set = false;
			while(!set){
				int x = gen.nextInt(width);
				int y = gen.nextInt(height);
				
				if(fields[y][x] == water){
					fields[y][x] = teamName(i);
					generateBases(i, y, x);
					set = true;
				}
			}
		}	
		return fields;	
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
	
	private static int mod(int a, int b){
		return (a%b + b) % b;
	}
	
	private static char teamName(int number){
		return ((char) (97 + number));
	}
	
	private static void generateBases(int teamNumber, int y, int x){
		Random gen = new Random();
		int[] ys = {y-1, y, y+1};
		int[] xs = {x-1, x, x+1};
		
		while(countBases(teamNumber) < boatsPerTeam){
			int xPick;
			if(y%2 == 0)
				xPick = gen.nextInt(xs.length-1);
			else
				xPick = 1 + gen.nextInt(xs.length-1);
		
			int yPick = gen.nextInt(ys.length);
			
			if(generateBase(teamNumber, ys[yPick], xs[xPick]))
				generateBases(teamNumber, ys[yPick], xs[xPick]);	
		}
	}
	
	private static boolean generateBase(int teamNumber, int y, int x){
		if(countBases(teamNumber) < boatsPerTeam){
			
			Random gen = new Random();
			float pick = gen.nextFloat();
			if(pick < baseChance){
				fields[mod(y, height)][mod(x, width)] = teamName(teamNumber);
				return true;
			}			
		}
		return false;
	}
	
	private static int countBases(int teamNumber){
		
		int counter = 0;
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(fields[y][x] == teamName(teamNumber))
					counter++;
			}
		}
		return counter;
	}
}
