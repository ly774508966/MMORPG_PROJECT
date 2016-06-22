package fr.technicalgames.settings;

import java.io.File;
import java.util.HashMap;

import fr.technicalgames.io.Log;

public class Settings {
	
	public static void loadSettings(){
		File t = new File("settings/");
		for(File f : t.listFiles()){
			Log.println(Log.INFO, f.getName());
		}
	}

	class SettingsFile{
		
		private String filename;
		private HashMap<String,String> data = new HashMap<String,String>();
		
		public SettingsFile(String filename,String path){
			File f = new File(path + filename);
			
		}
		
		
	}
	
}
