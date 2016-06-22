package fr.technicalgames;

import java.io.*;
import java.util.Map.*;

import javax.xml.bind.*;

import fr.technicalgames.data.*;
import fr.technicalgames.network.*;
import fr.technicalgames.settings.Settings;

public class MainServer{
	
	public static Server server;

	public static void main(String[] args) throws JAXBException {
		Settings.loadSettings();
		World.loadWorlds();
		server = new Server(9999);
	}
	
}
