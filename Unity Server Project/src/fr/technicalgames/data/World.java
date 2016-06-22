package fr.technicalgames.data;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;

import javax.xml.bind.*;

import fr.technicalgames.data.xml.WorldList;
import fr.technicalgames.io.*;
import fr.technicalgames.math.*;
import fr.technicalgames.network.common.*;

public class World {
	
	private int heightmapWidth,heightmapHeight;
	private float mapWidth,mapHeight;
	private float[][] heightsMap;
	
	private static Map<Integer,World> worlds = new HashMap<Integer,World>();
	
	public World(String filename) throws IOException{
		Path path = Paths.get("ressources/world/" + filename);
		byte[] data = Files.readAllBytes(path);
		
		DataBuffer d = new DataBuffer(data);
		heightmapWidth = d.getInt();
		heightmapHeight = d.getInt();
		mapWidth = d.getFloat();
		mapHeight = d.getFloat();
		
		heightsMap = new float[heightmapWidth][heightmapHeight];
		
		for(int x = 0;x < heightmapWidth;x++){
			for(int y = 0;y < heightmapHeight;y++){
				heightsMap[x][y] = d.getFloat();
			}
		}
		
	}

	public float getHeights(float x,float y){
		float rx = (x * (float)heightmapWidth)/mapWidth;
		float ry = (y * (float)heightmapHeight)/mapHeight;
		
		int ix = (int)rx;
		int iy = (int)ry;
		
		float interpolate1 = Mathf.linearInterpolate(heightsMap[ix][iy],heightsMap[ix + 1][iy],rx - ix);
		float interpolate2 = Mathf.linearInterpolate(heightsMap[ix][iy + 1],heightsMap[ix + 1][iy + 1],rx - ix);
		
		return Mathf.linearInterpolate(interpolate1,interpolate2,ry - iy);
	}
	
	public static void loadWorlds(){
		Log.println(Log.INFO, "Loading World ...");
		try {
			JAXBContext jc = JAXBContext.newInstance(WorldList.class);
			Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
			WorldList worldList = (WorldList)jaxbUnmarshaller.unmarshal(new File("data/world/worldList.xml"));
			for(Entry<Integer, String> s : worldList.getWorlds().entrySet()){
				try {
					World w = new World(s.getValue());
					worlds.put(s.getKey(), w);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Log.println(Log.INFO, "World loaded !");
	}

	public static Map<Integer, World> getWorlds() {
		return worlds;
	}

	public static void setWorlds(Map<Integer, World> worlds) {
		World.worlds = worlds;
	}

	public int getHeightmapWidth() {
		return heightmapWidth;
	}

	public void setHeightmapWidth(int heightmapWidth) {
		this.heightmapWidth = heightmapWidth;
	}

	public int getHeightmapHeight() {
		return heightmapHeight;
	}

	public void setHeightmapHeight(int heightmapHeight) {
		this.heightmapHeight = heightmapHeight;
	}

	public float getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(float mapWidth) {
		this.mapWidth = mapWidth;
	}

	public float getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(float mapHeight) {
		this.mapHeight = mapHeight;
	}

	public float[][] getHeightsMap() {
		return heightsMap;
	}

	public void setHeightsMap(float[][] heightsMap) {
		this.heightsMap = heightsMap;
	}
	
	
}
