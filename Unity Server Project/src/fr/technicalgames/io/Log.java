package fr.technicalgames.io;

import java.io.*;

public class Log {
	
	public static final int INFO = 0,WARNING = 1,ERROR = 2;
	
	private static PrintStream out = System.out;

	public static void println(int type,String a){
		switch(type){
		case INFO:
			out.println(a);
			break;
		case WARNING:
			out.println(a);
			break;
		case ERROR:
			out.println(a);
			break;
		}
	}
	
	public static void print(int type,String a){
		a = "\b" + a;
		switch(type){
		case INFO:
			out.print(a);
			break;
		case WARNING:
			out.print(a);
			break;
		case ERROR:
			out.print(a);
			break;
		}
	}
	
}
