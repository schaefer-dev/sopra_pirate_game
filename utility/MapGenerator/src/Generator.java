import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Generator {

	private final char emptyIsland = '#';
	private final char water = '.';
	private final char provision = '$';
	
	private final double provisionChance = 0.1;
	private final double treasureChance = 0.2;
	private final double baseChance = 0.7;
	private final double islandChance = 0.8;

	private Integer height;
	private Integer width;
	private Integer islandsCount;
	private Integer islandSize;
	private Integer minIslandSize;
	private Integer maxIslandSize;
	private Integer levelOfDetail = 3;					//no gaps if < 3
	private Integer teamsPerMap;
	private Integer boatsPerTeam;

	private int[] bases;
	private int[] islandSizes;
	private char[][] fields;
	
	private boolean built = false;
	private boolean placed = false;
	
	private boolean baseMustBeNearIsland = true;		//broken
	private int genMethodCount = 2;
	private int genMethodNr = 2;
	
	
	public Generator(Integer newHeight, Integer newWidth, Integer islands,  Integer isSize, Integer tpm, Integer bpt){

		height = newHeight;
		width  = newWidth;
		islandsCount = islands;
		islandSize = isSize;
		minIslandSize = (height*width)/(islandSize*20);
		maxIslandSize = (height*width)/(islandSize*10);
		teamsPerMap  = tpm;
		boatsPerTeam = bpt;

		islandSizes = new int[islandsCount];
		bases = new int[teamsPerMap];
		fields = new char[height][width];
	}
	
	public Generator(char[][] newFields, Integer tpm, Integer bpt){
		
		fields = newFields;
		height = fields.length;
		width  = fields[0].length;
		teamsPerMap  = tpm;
		boatsPerTeam = bpt;
		bases = new int[teamsPerMap];
		built = true;
		baseMustBeNearIsland = false;
	}
	
	
	public char[][] generate(){	
			
		if(built){
			System.out.println("You already generated a map");
			return fields;
		}
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++)
				fields[y][x] = water;
		}
		
		setIslands();
		placeObjects();
		
		built = true;
		return fields;
	}
	
	public char[][] placeObjects(){
		
		if(placed){
			System.out.println("You already placed objects on the map");
			return fields;
		}
		
		smoothIslands(5);
		setProvision();
		setTreasure();
		setBases();	

		placed = true;
		return fields;
	}
		
		
	private void setIslands(){
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
					genMethodNr = 1 + gen.nextInt(genMethodCount);
					switch(genMethodNr){
						case 1:
							generateIslandSwarm(i, y, x);
							break;
						case 2:
							generateIslandSwarm2(i, y, x);
							break;
						case 3:
							generateIslandSwarm3(i, y, x);
							break;
						default:
							generateIslandSwarm2(i, y, x);
					}
					set = true;
				}
			}
		}
	}
	

	private void setProvision(){
		Random gen = new Random();
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				
				float pick = gen.nextFloat();
				if((fields[y][x] == emptyIsland) && (pick < provisionChance) && hasNeighbor(y, x, water))
					fields[y][x] = provision;		
			}
		}
	}
	
	private void setTreasure(){
		Random gen = new Random();
				
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				
				float pick = gen.nextFloat();
				if((fields[y][x] == emptyIsland) && (pick < treasureChance) && hasNeighbor(y, x, water)){
					int treasureSize = 1 + gen.nextInt(9);
					fields[y][x] = (char)(48 + treasureSize);	
				}		
			}
		}
	}
	
	private void setBases(){
		Random gen = new Random();
		
		Arrays.fill(bases, boatsPerTeam);	
		for(int i = 0; i < bases.length; i++){
			boolean set = false;
			while(!set){
				int x = gen.nextInt(width);
				int y = gen.nextInt(height);
				
				if(fields[y][x] == water){
					if(baseMustBeNearIsland && (hasNeigbors(y, x, emptyIsland) < 2))
						continue;

					fields[y][x] = teamName(i);
					bases[i]--;
					generateBaseSwarm(i, y, x);
					set = true;
				}
			}
		}
	}

	private void smoothIslands(int factor){
		for(int i = 0; i < factor; i++){
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){
					if(fields[y][x] == water){
						if(hasNeigbors(y, x, emptyIsland) > levelOfDetail)
							fields[y][x] = emptyIsland;
					}
				}
			}
		}
	}
	
	
	private void generateBaseSwarm(int teamNumber, int y, int x){
		Random gen = new Random();

		int[] ys = {y-1, y, y+1};
		int[] xs = {x-1, x, x+1};
		
		while(bases[teamNumber] > 0){
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
	
	private boolean generateBase(int teamNumber, int y, int x){
		if(bases[teamNumber] > 0){
			
			Random gen = new Random();
			float pick = gen.nextFloat();
			if((pick < baseChance) && !checkBase(mod(y, height), mod(x, width))){
				fields[mod(y, height)][mod(x, width)] = teamName(teamNumber);
				bases[teamNumber]--;
				return true;
			}			
		}
		return false;
	}
	
	private void generateIslandSwarm(int islandIndex, int y, int x){
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
	
	private void generateIslandSwarm2(int islandIndex, int y, int x){
		Random gen = new Random();
		
		int[] ys = {y-1, y, y+1};
		int[] xs = {x-1, x, x+1};
		
		List<Point>points = new ArrayList<Point>();
		
		while(islandSizes[islandIndex] > 0){
			for(int i = 0; i <= 6; i++){
				int xPick;
				if(y%2 == 0)
					xPick = gen.nextInt(xs.length-1);
				else
					xPick = 1 + gen.nextInt(xs.length-1);
			
				int yPick = gen.nextInt(ys.length);
				
				if(generateIsland(islandIndex, ys[yPick], xs[xPick]))
					points.add(new Point(ys[yPick], xs[xPick]));	
			}
			if(!points.isEmpty()){
				Point p = points.get(gen.nextInt(points.size()));
				generateIslandSwarm2(islandIndex, p.y, p.x);	
			}	
		}
	}
	
	private void generateIslandSwarm3(int islandIndex, int y, int x){
		Random gen = new Random();
		
		int[] ys = {y-1, y, y+1};
		int[] xs = {x-1, x, x+1};
		
		if(islandSizes[islandIndex] > 0){
			int xPick;
			if(y%2 == 0)
				xPick = gen.nextInt(xs.length-1);
			else
				xPick = 1 + gen.nextInt(xs.length-1);
		
			int yPick = gen.nextInt(ys.length);
			
			generateIsland(islandIndex, ys[yPick], xs[xPick], 1);
			generateIslandSwarm(islandIndex, ys[yPick], xs[xPick]);	
		}
	}
	
	private boolean generateIsland(int islandIndex, int y, int x, double chance){
		if(islandSizes[islandIndex] > 0){
			
			Random gen = new Random();
			float pick = gen.nextFloat();
			if(pick < chance){
				fields[mod(y, height)][mod(x, width)] = emptyIsland;
				islandSizes[islandIndex]--;
				return true;
			}			
		}
		return false;
	}
	
	private boolean generateIsland(int islandIndex, int y, int x){
		return generateIsland(islandIndex, y, x, islandChance);
	}
	
		
	private int hasNeigbors(int y, int x, char fieldType){
		int counter = 0;
		int xx, zz;
		if(y%2 == 0){
			xx = mod(x-1, width);
			zz = mod(x+1, width);
		}
		else{
			xx = mod(x+1, width);
			zz = mod(x-1, width);
		}
			
		int yy = mod(y-1, height);
		if(fields[yy][xx] == fieldType)
			counter++;
		yy = mod(y, height);
		if(fields[yy][xx] == fieldType)
			counter++;
		if(fields[yy][zz] == fieldType)
			counter++;
		yy = mod(y+1, height);
		if(fields[yy][xx] == fieldType)
			counter++;
		xx = mod(x, width);
		yy = mod(y-1, height);
		if(fields[yy][xx] == fieldType)
			counter++;
		yy = mod(y+1, height);
		if(fields[yy][xx] == fieldType)
			counter++;
		
		return counter;
	}
	
	private boolean hasNeighbor(int y, int x, char fieldType){
		if(hasNeigbors(y, x, fieldType) > 0)
			return true;
		
		return false;
	}
	
	private boolean checkBase(int y, int x){
		if((fields[y][x] >= 97) && fields[y][x] <= 122)
			return true;
		
		return false;
	}
	
	private int mod(int a, int b){
		return (a%b + b) % b;
	}
	
	private char teamName(int number){
		return ((char) (97 + number));
	}
}
