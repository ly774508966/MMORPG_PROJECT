package fr.technicalgames.network.packet;

import java.net.*;

import fr.technicalgames.client.*;
import fr.technicalgames.network.*;
import fr.technicalgames.network.common.*;

public class Disconnect_Client_Packet implements IPacket{
	
	public String pseudo; 

	public Disconnect_Client_Packet(){}
	
	public Disconnect_Client_Packet(String pseudo){
		this.pseudo = pseudo;
	}

	@Override
	public void read(DataBuffer data) {
		this.pseudo = data.getString();
	}

	@Override
	public void write(DataBuffer data) {
		data.put(this.pseudo);
	}

	@Override
	public void manage(Server server, DatagramSocket socket, Client client, IPacket packet) {
		server.closeConnection(client);
		server.sendToClients(packet);
	}
	
	
	
}
