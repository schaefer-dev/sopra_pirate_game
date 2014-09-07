import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

	private static final char emptyIsland = '#';
	private static final char water = '.';
	private static final char treasure = '$';
	
	private static final double treasureChance = 0.3;
	private static final double baseChance = 0.7;
	private static final double islandChance = 1;

	private static Integer height;
	private static Integer width;
	private static Integer islandsCount;
	private static Integer minIslandSize = 80;
	private static Integer maxIslandSize = 300;
	private static Integer teamsPerMap;
	private static Integer boatsPerTeam;
	private static int[] bases;
	private static int[] islandSizes;
	private static char[][] fields;
	
	private static boolean baseMustBeNearIsland = false;		//broken
	
	
	public static void main(String[] args) throws FileNotFoundException{
				
		height = Integer.parseInt(args[0]);
		width  = Integer.parseInt(args[1]);
		islandsCount = Integer.parseInt(args[2]);
		teamsPerMap  = Integer.parseInt(args[3]);
		boatsPerTeam = Integer.parseInt(args[4]);
		
		islandSizes = new int[islandsCount];
		bases = new int[teamsPerMap];
		fields = new char[height][width];
		
		build();
		write();
	}
	
	
	private static void write() throws FileNotFoundException{
		
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
	
	
	private static void build(){	
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++)
				fields[y][x] = water;
		}
		setIslands();
		setTreasures();
		setBases();
		cleanUp();
	}
	
	private static void setIslands(){
		Random gen = new Random();
		
		for(int i = 0; i < islandsCount; i++){
			islandSizes[i] = minIslandSize + gen.nextInt(maxIslandSize - minIslandSize);
			
			boolean set = false;
			while(!set){
				int x = gen.nextInt(width);
				int y = gen.nextInt(height);
				
				if(fields[y][x] == water){
					fields[y][x] = emptyIsland;
					islandSizes[i]--;
					generateIslandSwarm(i, y, x);
					set = true;
				}
			}
		}
	}
	

	private static void setTreasures(){
		Random gen = new Random();
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				
				float pick = gen.nextFloat();
				if((fields[y][x] == emptyIsland) && (pick < treasureChance)){
					if(hasNeighbor(y, x, water))
						fields[y][x] = treasure;
				}	
			}
		}
	}
	
	private static void setBases(){
		Random gen = new Random();
		
		for(int i = 0; i < bases.length; i++){
			boolean set = false;
			while(!set){
				int x = gen.nextInt(width);
				int y = gen.nextInt(height);
				
				if(fields[y][x] == water){
					if(baseMustBeNearIsland && (hasNeigbor(y, x, emptyIsland) < 3))
						continue;
					
					fields[y][x] = teamName(i);
					generateBaseSwarm(i, y, x);
					set = true;
				}
			}
		}
	}
	
	
	private static void generateBaseSwarm(int teamNumber, int y, int x){
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
				generateBaseSwarm(teamNumber, ys[yPick], xs[xPick]);	
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
	
	private static void generateIslandSwarm(int islandIndex, int y, int x){
		Random gen = new Random();
		int[] ys = {y-1, y, y+1};
		int[] xs = {x-1, x, x+1};
		
		while(islandSizes[islandIndex] > 0){
			int xPick;
			if(y%2 == 0)
				xPick = gen.nextInt(xs.length-1);
			else
				xPick = 1 + gen.nextInt(xs.length-1);
		
			int yPick = gen.nextInt(ys.length);
			
			if(generateIsland(islandIndex, ys[yPick], xs[xPick]))
				generateIslandSwarm(islandIndex, ys[yPick], xs[xPick]);	
		}
	}
	
	private static boolean generateIsland(int islandIndex, int y, int x){
		if(islandSizes[islandIndex] > 0){
			
			Random gen = new Random();
			float pick = gen.nextFloat();
			if(pick < islandChance){
				fields[mod(y, height)][mod(x, width)] = emptyIsland;
				islandSizes[islandIndex]--;
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
	
	private static int hasNeigbor(int y, int x, char fieldType){
		int counter = 0;
		int xx = mod(x-1, width);
		int yy = mod(y-1, height);
		
		if(fields[yy][xx] == fieldType)
			counter++;
		xx = mod(x, width);
		if(fields[yy][xx] == fieldType)
			counter++;
		xx = mod(x + 1, width);
		if(fields[yy][xx] == fieldType)
			counter++;
		yy = mod(y, width);
		if(fields[yy][xx] == fieldType)
			counter++;
		xx = mod(x - 1, width);
		if(fields[yy][xx] == fieldType)
			counter++;
		yy = mod(y - 1, width);
		if(fields[yy][xx] == fieldType)
			counter++;
		xx = mod(x, width);
		if(fields[yy][xx] == fieldType)
			counter++;
		xx = mod(x, width);
		if(fields[yy][xx] == fieldType)
			counter++;
		
		return counter;
	}
	
	private static boolean hasNeighbor(int y, int x, char fieldType){
		if(hasNeigbor(y, x, fieldType) > 0)
			return true;
		
		return false;
	}
	
	private static void cleanUp(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
			
				if(fields[y][x] == water){
					int neighborCount = hasNeigbor(y, x, emptyIsland) + hasNeigbor(y, x, treasure); 
					if(neighborCount > 6)
						fields[y][x] = emptyIsland;
				}
			}
		}
	}
	
	private static int mod(int a, int b){
		return (a%b + b) % b;
	}
	
	private static char teamName(int number){
		return ((char) (97 + number));
	}
}
