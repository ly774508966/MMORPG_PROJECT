package fr.technicalgames.network.packet;

import java.net.*;

import fr.technicalgames.client.*;
import fr.technicalgames.network.*;
import fr.technicalgames.network.common.*;

public class MainState_Connection_Request_Packet implements IPacket{
	
	public static final int REQUEST = 0, ACCEPTED = 1, REFUSED = 2,TIME_OUT = 3, ALREADY_CONNECTED = 4, PSEUDO_INCORRECT = 5,DISCONNECTED = 6;

	public String pseudo;
    public int id;

    public MainState_Connection_Request_Packet() { }

    public MainState_Connection_Request_Packet(String pseudo,int id)
    {
        this.pseudo = pseudo;
        this.id = id;
    }
	
	@Override
	public void read(DataBuffer data) {
		this.pseudo = data.getString();
        this.id = data.getInt();
	}

	@Override
	public void write(DataBuffer data) {
        data.put(pseudo);
        data.put(id);
	}

	@Override
	public void manage(Server server,DatagramSocket socket,Client client,IPacket packet) {
		if(server.getClientByName(pseudo) == null){
			client.getPlayer().setName(pseudo);
			id = ACCEPTED;
		}else{
			Client cl = server.getClientByName(pseudo);
			while(cl != null){
				server.closeConnection(cl);
				cl = server.getClientByName(pseudo);
			}
			server.closeConnection(client);
			id = ALREADY_CONNECTED;
		}
		client.send(socket, this);
	}

}
