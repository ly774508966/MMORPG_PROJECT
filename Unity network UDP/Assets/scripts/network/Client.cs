using System;
using UnityEngine;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Threading;


class Client
{
    private UdpClient client;
    private IPEndPoint ep;
    private Thread receiveThread;

    private volatile Boolean connected = false;

    public Client(String ip,int port)
    {
        ep = new IPEndPoint(IPAddress.Parse(ip), port);
        client = new UdpClient();
        receiveThread = new Thread(new ThreadStart(loop));
        Register.registerClass();
    }

    public void connect()
    {
        client.Connect(ep);
        connected = true;
        receiveThread.Start();
    }

    public void loop()
    {
        while (connected)
        {
            byte[] data = client.Receive(ref ep);
            DataBuffer dataBuffer = new DataBuffer(data);
            int id = dataBuffer.getInt();
            IPacket packet = (IPacket)Register.instantiate(id);
            packet.read(dataBuffer);
            packet.manage(data, this);
        }
    }

    public void send(IPacket packet)
    {
        DataBuffer data = new DataBuffer();
        packet.write(data);
        client.Send(data.getData(), data.getData().Length);
    }

    public void disconnect()
    {
        client.Close();
        connected = false;
    }

}
