package fr.technicalgames.client;

import java.io.*;
import java.net.*;

import fr.technicalgames.entity.*;
import fr.technicalgames.entity.player.Player;
import fr.technicalgames.network.*;
import fr.technicalgames.network.common.*;

public class Client {

	private InetAddress address;
	private int port;
	private Player player;
	
	public Client(InetAddress address,int port){
		this.address = address;
		this.port = port;
		this.player = new Player("Default");
	}
	
	public void send(DatagramSocket server,IPacket packet){
		try {
			DataBuffer data = new DataBuffer();
			data.put(Register.getId(packet.getClass()));
			packet.write(data);
			Server.up += data.getPointer();
			server.send(new DatagramPacket(data.getData(),data.getPointer(),address,port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	
}
