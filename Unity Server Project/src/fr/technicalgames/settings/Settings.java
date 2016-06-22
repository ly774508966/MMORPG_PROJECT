package fr.technicalgames.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import fr.technicalgames.io.Log;

public class Settings {
	
	private static HashMap<String,SettingsFile> settings = new HashMap<String,SettingsFile>();
	
	public static void loadSettings(String path){
		Log.println(Log.INFO, "Load configuration files...");
		File t = new File(path);
		for(File f : t.listFiles()){
			settings.put(f.getName(), new SettingsFile(f.getName(), path));
		}
		Log.println(Log.INFO, "Configuration files loaded");
	}
	
	public static String getValue(String filename,String dataname){
		return settings.get(filename).getValue(dataname);
	}

}

class SettingsFile{
	
	private String filename;
	private HashMap<String,String> data = new HashMap<String,String>();
	
	public SettingsFile(String filename,String path){
		try {
			File f = new File(path + filename);
			FileInputStream fis = new FileInputStream(f);
			 
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				if(line.toCharArray()[0] != '#'){
					String[] line_set = line.split("=");
					data.put(line_set[0], line_set[1]);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 

	}
	
	public String getValue(String dataname){
		return data.get(dataname);
	}
	
	
}
