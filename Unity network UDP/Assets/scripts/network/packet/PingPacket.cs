using UnityEngine;
using System.Collections;

public class PingPacket : IPacket {

    public long current;

    public PingPacket()
    {

    }

	void IPacket.read(DataBuffer data)
    {

    }

    void IPacket.write(DataBuffer data)
    {

    }

    void IPacket.manage(byte[] data, Client client)
    {

    }
}
