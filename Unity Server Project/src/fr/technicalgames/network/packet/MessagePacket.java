package fr.technicalgames.network.packet;

import java.net.*;

import fr.technicalgames.client.*;
import fr.technicalgames.io.*;
import fr.technicalgames.network.*;
import fr.technicalgames.network.common.*;

public class MessagePacket implements IPacket{

	private String pseudo, message;

    public MessagePacket() { }

    public MessagePacket(String pseudo, String message){
        this.message = message;
        this.pseudo = pseudo;
    }

    public void read(DataBuffer data){
        this.pseudo = data.getString();
        this.message = data.getString();
    }

    public void write(DataBuffer data){
        data.put(this.pseudo);
        data.put(this.message);
    }

    public void manage(Server server,DatagramSocket socket,Client client,IPacket packet){
        Log.println(Log.INFO, pseudo + " : " + message);
    }

}
