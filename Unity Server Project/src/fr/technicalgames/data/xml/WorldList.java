package fr.technicalgames.data.xml;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class WorldList {

	private Map<Integer,String> worlds = new HashMap<Integer,String>();

	public Map<Integer, String> getWorlds() {
		return worlds;
	}

	public void setWorlds(Map<Integer, String> worlds) {
		this.worlds = worlds;
	}
	
}
