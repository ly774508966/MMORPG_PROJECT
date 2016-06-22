package fr.technicalgames.network.common;

import java.nio.*;

import fr.technicalgames.io.*;

public class DataBuffer {

	public static final int SIZE = 1024;
	
	private byte[] data;
	private int pointer;
	
	public DataBuffer(){
		data = new byte[SIZE];
		pointer = 0;
	}
	
	public DataBuffer(int size){
		data = new byte[size];
		pointer = 0;
	}
	
	public DataBuffer(byte[] data){
		 this.data = data;
		 pointer = 0;
	}
	
	public void put(byte a){
		if(pointer >= data.length){
			Log.println(Log.ERROR, "Databuffer write overflow");
			return;
		}
		data[pointer++] = a;
	}
	
	public void put(short a){
		put((byte)((a >> 8) & 0xff));
		put((byte)((a >> 0) & 0xff));
	}
	
	public void put(int a){
		put((byte)((a >> 24) & 0xff));
		put((byte)((a >> 16) & 0xff));
		put((byte)((a >> 8) & 0xff));
		put((byte)((a >> 0) & 0xff));
	}
	
	public void put(long a){
		put((byte)((a >> 56) & 0xff));
		put((byte)((a >> 48) & 0xff));
		put((byte)((a >> 40) & 0xff));
		put((byte)((a >> 32) & 0xff));
		put((byte)((a >> 24) & 0xff));
		put((byte)((a >> 16) & 0xff));
		put((byte)((a >> 8) & 0xff));
		put((byte)((a >> 0) & 0xff));
	}
	
	public void put(float b){
		int a = Float.floatToIntBits(b);
		put((byte)((a >> 0) & 0xff));
		put((byte)((a >> 8) & 0xff));
		put((byte)((a >> 16) & 0xff));
		put((byte)((a >> 24) & 0xff));
	}
	
	public void put(double b){
		long a = Double.doubleToLongBits(b);
		put((byte)((a >> 0) & 0xff));
		put((byte)((a >> 8) & 0xff));
		put((byte)((a >> 16) & 0xff));
		put((byte)((a >> 24) & 0xff));
		put((byte)((a >> 32) & 0xff));
		put((byte)((a >> 40) & 0xff));
		put((byte)((a >> 48) & 0xff));
		put((byte)((a >> 56) & 0xff));
	}
	
	public void put(char a){
		put((byte)a);
	}
	
	public void put(String a){
		char[] b = a.toCharArray();
		put(b.length);
		for(int i = 0;i < b.length;i++){
			put(b[i]);
		}
	}
	
	public byte getByte(){
		if(pointer >= data.length){
			Log.println(Log.ERROR, "Databuffer write overflow");
			return 0;
		}
		return data[pointer++];
	}
	
	public short getShort(){
		return (short) ((
				(getByte() << 8) & 0xff00) | 
				(getByte() & 0xff));
	}
	
	public int getInt(){
		return (int) ((
				(getByte() << 24) & 0xff000000) | 
				(getByte() << 16 & 0xff0000) | 
				(getByte() << 8 & 0xff00) | 
				(getByte() & 0xff));
	}
	
	public long getLong(){
		return (long) (
				(((long)getByte() << 56) & 0xff00000000000000l) | 
				(((long)getByte() << 48) & 0xff000000000000l) | 
				(((long)getByte() << 40) & 0xff0000000000l) | 
				(((long)getByte() << 32) & 0xff00000000l) | 
				(((long)getByte() << 24) & 0xff000000l) | 
				(((long)getByte() << 16) & 0xff0000l) | 
				(((long)getByte() << 8) & 0xff00l) | 
				((long)getByte() & 0xffl));
	}
	
	public float getFloat(){
		byte[] array = new byte[]{getByte(),getByte(),getByte(),getByte()};
		byte tmp = array[0];
		array[0] = array[3];
		array[3] = tmp;
		tmp = array[1];
		array[1] = array[2];
		array[2] = tmp;
		tmp = 0;
		return Float.intBitsToFloat(ByteBuffer.wrap(array).getInt());
	}
	
	public double getDouble(){
		byte[] array = new byte[]{getByte(),getByte(),getByte(),getByte(),getByte(),getByte(),getByte(),getByte()};
		byte[] d = new byte[8];
		for(int i = 0;i < 8;i++){
			d[7 - i] = array[i];
		}
		array = null;
		return Double.longBitsToDouble(ByteBuffer.wrap(d).getLong());
	}
	
	public char getChar(){
		return (char)getByte();
	}
	
	public String getString(){
		char[] st = new char[getInt()];
		for(int i = 0;i < st.length;i++){
			st[i] = getChar();
		}
		return new String(st);
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	public static int getSize() {
		return SIZE;
	}
	
	public void clear(){
		pointer = 0;
	}
	
}
