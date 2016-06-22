package fr.technicalgames.network.common;

import java.util.*;

import fr.technicalgames.network.packet.*;

public class Register {

	public static ArrayList<Class> registeredClass = new ArrayList<Class>();
	
	public static void registerClass(){
		addClass(MessagePacket.class);
		addClass(MainState_Connection_Request_Packet.class);
		addClass(Disconnect_Client_Packet.class);
	}
	
	public static void addClass(Class cl){
		registeredClass.add(cl);
	}
	
	public static Class getClass(int id){
		return registeredClass.get(id);
	}
	
	public static int getId(Class cl){
		for(int i = 0;i < registeredClass.size();i++){
			if(registeredClass.get(i).equals(cl))return i;
		}
		return -1;
	}
	
	public static Object instantiate(int id) {
		try {
			return getClass(id).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
