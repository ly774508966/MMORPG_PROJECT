using System;
using UnityEngine;
using System.Collections.Generic;
using System.Linq;
using System.Text;

class MainState_Connection_Request_Packet : IPacket
{

    public static readonly int REQUEST = 0, ACCEPTED = 1, REFUSED = 2, TIME_OUT = 3, ALREADY_CONNECTED = 4, PSEUDO_INCORRECT = 5,DISCONNECTED = 6;

    public String pseudo;
    public int id;

    public MainState_Connection_Request_Packet() { }

    public MainState_Connection_Request_Packet(String pseudo,int id)
    {
        this.pseudo = pseudo;
        this.id = id;
    }

    void IPacket.write(DataBuffer data)
    {
        data.put(Register.getId(typeof(MainState_Connection_Request_Packet)));
        data.put(pseudo);
        data.put(id);
    }

    void IPacket.read(DataBuffer data)
    {
        this.pseudo = data.getString();
        this.id = data.getInt();
    }

    void IPacket.manage(byte[] data, Client client)
    {
        if(id == ACCEPTED)
        {
            MainClient.connected = ACCEPTED;
            MainClient.pseudo = this.pseudo;
        }
        else if(id == REFUSED)
        {
            MainClient.connected = REFUSED;
        }
        else if (id == ALREADY_CONNECTED)
        {
            MainClient.connected = ALREADY_CONNECTED;
        }
        else if (id == PSEUDO_INCORRECT)
        {
            MainClient.connected = PSEUDO_INCORRECT;
        }
    }

}
