using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

class Disconnect_Client_Packet : IPacket
{
    public string pseudo;

    public Disconnect_Client_Packet() { }

    public Disconnect_Client_Packet(string pseudo) { this.pseudo = pseudo; }

    void IPacket.read(DataBuffer data)
    {
        this.pseudo = data.getString();
    }

    void IPacket.write(DataBuffer data)
    {
        data.put(Register.getId(typeof(Disconnect_Client_Packet)));
        data.put(this.pseudo);
    }

    void IPacket.manage(byte[] data, Client client)
    {
        if (pseudo.Equals(MainClient.pseudo))
        {
            MainClient.connected = MainState_Connection_Request_Packet.DISCONNECTED;
            MainClient.client.disconnect();
        }
        else
        {

        }
    }


}
