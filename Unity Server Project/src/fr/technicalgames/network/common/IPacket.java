package fr.technicalgames.network.common;

import java.net.*;

import fr.technicalgames.client.*;
import fr.technicalgames.network.*;

public interface IPacket {

	public void read(DataBuffer data);
	public void write(DataBuffer data);
	public void manage(Server server,DatagramSocket socket,Client client,IPacket packet);
	
}
