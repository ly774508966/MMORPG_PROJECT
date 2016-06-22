using UnityEngine;
using System.Collections;
using System;

public class DataBuffer{

    //readonly constant static -> const non static
    public static readonly int SIZE = 1024;

    private byte[] data;
    private int pointer;

    public DataBuffer()
    {
        data = new byte[SIZE];
        pointer = 0;
    }

    public DataBuffer(byte[] data)
    {
        this.data = data;
        pointer = 0;
    }

    public void put(byte a)
    {
        if (pointer >= SIZE)
        {
            Debug.Log( "Databuffer write overflow");
            return;
        }
        data[pointer++] = a;
    }

    public void put(sbyte a)
    {
        if (pointer >= SIZE)
        {
            Debug.Log("Databuffer write overflow");
            return;
        }
        data[pointer++] = (byte)a;
    }

    public void put(short a)
    {
        put((byte)((a >> 8) & 0xff));
        put((byte)((a >> 0) & 0xff));
    }

    public void put(int a)
    {
        put((byte)((a >> 24) & 0xff));
        put((byte)((a >> 16) & 0xff));
        put((byte)((a >> 8) & 0xff));
        put((byte)((a >> 0) & 0xff));
    }

    public void put(long a)
    {
        put((byte)((a >> 56) & 0xff));
        put((byte)((a >> 48) & 0xff));
        put((byte)((a >> 40) & 0xff));
        put((byte)((a >> 32) & 0xff));
        put((byte)((a >> 24) & 0xff));
        put((byte)((a >> 16) & 0xff));
        put((byte)((a >> 8) & 0xff));
        put((byte)((a >> 0) & 0xff));
    }

    public void put(float a)
    {
        byte[] array = BitConverter.GetBytes(a);
        foreach(byte b in array)
        {
            put(b);
        }
    }


    public void put(double a)
    {
        byte[] array = BitConverter.GetBytes(a);
        foreach (byte b in array)
        {
            put(b);
        }
    }

    public void put(char a)
    {
        put((byte)a);
    }

    public void put(String a)
    {
        char[] b = a.ToCharArray();
        put(a.Length);
        for (int i = 0; i < a.Length; i++)
        {
            put(b[i]);
        }
    }

    public byte getByte()
    {
        if (pointer >= SIZE)
        {
            Debug.Log("Databuffer write overflow");
            return 0;
        }
        return data[pointer++];
    }

    public sbyte getSByte()
    {
        if (pointer >= SIZE)
        {
            Debug.Log("Databuffer write overflow");
            return 0;
        }
        return (sbyte)data[pointer++];
    }

    public short getShort()
    {
        return (short)((
                (getByte() << 8) & 0xff00) |
                (getByte() & 0xff));
    }

    public int getInt()
    {
        return (int)((
                (getByte() << 24) & 0xff000000) |
                (getByte() << 16 & 0xff0000) |
                (getByte() << 8 & 0xff00) |
                (getByte() & 0xff));
    }

    public long getLong()
    {
        return (long)(
                ((ulong)((long)getByte() << 56) & 0xff00000000000000L) |
                ((ulong)((long)getByte() << 48) & 0xff000000000000L) |
                ((ulong)((long)getByte() << 40) & 0xff0000000000L) |
                (ulong)(((long)getByte() << 32) & 0xff00000000L) |
                ((ulong)((long)getByte() << 24) & 0xff000000L) |
                ((ulong)((long)getByte() << 16) & 0xff0000L) |
                ((ulong)((long)getByte() << 8) & 0xff00L) |
                ((ulong)(long)getByte() & 0xffL));
    }

    public float getFloat()
    {
        byte[] array = new byte[4] { getByte(), getByte(), getByte(), getByte() };
        return BitConverter.ToSingle(array, 0);
    }

    public double getDouble()
    {
        byte[] array = new byte[8] { getByte(), getByte(), getByte(), getByte(), getByte(), getByte(), getByte(), getByte() };
        return BitConverter.ToDouble(array, 0);
    }

    public char getChar()
    {
        return (char)getByte();
    }

    public String getString()
    {
        int size = getInt();
        char[] st = new char[size];
        for (int i = 0; i < size; i++)
        {
            st[i] = getChar();
        }
        return new String(st);
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    public int getPointer()
    {
        return pointer;
    }

    public void setPointer(int pointer)
    {
        this.pointer = pointer;
    }

    public static int getSize()
    {
        return SIZE;
    }

    public void clear()
    {
        pointer = 0;
    }



}
