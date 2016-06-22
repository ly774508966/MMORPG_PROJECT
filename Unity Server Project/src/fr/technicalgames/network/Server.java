package fr.technicalgames.network;

import java.io.*;
import java.net.*;
import java.util.*;

import fr.technicalgames.client.*;
import fr.technicalgames.io.*;
import fr.technicalgames.network.common.*;
import fr.technicalgames.network.packet.*;

public class Server extends Thread{

	private ArrayList<Client> clients = new ArrayList<Client>();
	
	private DatagramSocket server;
	private boolean running = false;
	private Scanner sc = new Scanner(System.in);
	public static int down = 0,up = 0,pup = 0,pdown = 0;
	public long previous = System.currentTimeMillis(),prev = System.currentTimeMillis();
	
	public Server(int port){
		try {
			this.server = new DatagramSocket(port);
			Log.println(Log.INFO, "Serveur lance a l'adresse " + server.getLocalAddress().getHostAddress() + ":" + server.getLocalPort());
			running = true;
			Register.registerClass();
			this.start();
			(new Thread(new Runnable() {
				@Override
				public void run() {
					while(running){
						String a = sc.nextLine();
						sendToClients(new MessagePacket("Server", a));
					}
				}
			})).start();
			(new Thread(new Runnable() {
				@Override
				public void run() {
					while(running){
						if(System.currentTimeMillis() - previous > 1000){
							pup = up;
							pdown = down;
							System.out.print("DOWN: " + pdown/1024.0f + "ko/s UP: " + pup/1024.0f + "ko/s                                \r");
							up = 0;
							down = 0;
							previous = System.currentTimeMillis();
						}
					}
				}
			})).start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(running){
	        try {
				byte[] data = new byte[DataBuffer.getSize()];
				DatagramPacket packet = new DatagramPacket(data,data.length);
				server.receive(packet);
				int size = packet.getLength();
				down += size;
				DataBuffer dataBuffer = new DataBuffer(data);
				if(packet.getAddress() != null){
					Client cl = getClient(packet.getAddress(),packet.getPort());
					if(cl != null){
						try{
							IPacket packetObject = (IPacket) Register.instantiate(dataBuffer.getInt());
							packetObject.read(dataBuffer);
							packetObject.manage(this,server,cl, packetObject);
						}catch(Exception e){
							String log = "Unknown packet : {\n   ";
							int i = 0;
							for(byte d : data){
								log += byteToHex(d) + " ";
								i++;
								if(i%32 == 0)log += "\n   ";
								if(i >= size)break;
							}
							log += "\n}";
							Log.println(Log.WARNING, log);
						}
					}else{
						try{
							Log.println(Log.INFO, packet.getAddress().getHostAddress() + ":" + packet.getPort() + " s'est connecter !");
							clients.add(cl = new Client(packet.getAddress(),packet.getPort()));
							IPacket packetObject = (IPacket) Register.instantiate(dataBuffer.getInt());
							packetObject.read(dataBuffer);
							packetObject.manage(this,server,cl, packetObject);
						}catch(Exception e){
							String log = "Unknown packet : {\n   ";
							int i = 0;
							for(byte d : data){
								log += byteToHex(d) + " ";
								i++;
								if(i%32 == 0)log += "\n   ";
								if(i >= size)break;
							}
							log += "\n}";
							Log.println(Log.WARNING, log);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Client getClient(InetAddress address,int port){
		for(Client cl : clients){
			if(cl.getAddress().getHostAddress().equals(address.getHostName()) && port == cl.getPort()){
				return cl;
			}
		}
		return null;
	}
	
	public Client getClientByName(String name){
		for(Client cl: clients){
			if(cl.getPlayer().getName().equals(name))return cl;
		}
		return null;
	}
	
	public void sendToClients(IPacket packet){
		for(Client cl : clients){
			cl.send(server, packet);
		}
	}
	
	public static String byteToHex(byte bytes) {
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
	    char[] hexChars = new char[2];
	    int v = bytes & 0xFF;
	    hexChars[0] = hexArray[v >>> 4];
	    hexChars[1] = hexArray[v & 0x0F];
	    return new String(hexChars);
	}
	
	public void closeConnection(Client client){
		clients.remove(client);
		Log.println(Log.INFO, client.getAddress().getHostName() + ":" + client.getPort() + " s'est deconnecter !");
	}
	
	public void stopServer(){
		running = false;
		this.server.close();
		this.stop();
	}
	
	
	
}
