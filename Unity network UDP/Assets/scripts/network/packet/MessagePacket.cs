using System;
using UnityEngine;
using System.Collections.Generic;
using System.Linq;
using System.Text;

class MessagePacket : IPacket
{

    private String pseudo, message;

    public MessagePacket() { }

    public MessagePacket(String pseudo, String message)
    {
        this.message = message;
        this.pseudo = pseudo;
    }

    void IPacket.read(DataBuffer data)
    {
        this.pseudo = data.getString();
        this.message = data.getString();
    }

    void IPacket.write(DataBuffer data)
    {
        data.put(Register.getId(typeof(MessagePacket)));
        data.put(this.pseudo);
        data.put(this.message);
    }

    void IPacket.manage(byte[] data,Client client)
    {
        Debug.Log(this.pseudo + " : " + this.message);
    }

}
