using UnityEngine;

class MainClient {

    public static Client client;
    public static string pseudo;
    public static int connected = -1;

    public static void connect (string host,int port,string pseudo) {
        client = new Client(host, port);
        client.connect();
        connected = 0;
        client.send(new MainState_Connection_Request_Packet(pseudo, 0));
    }


    public static void OnGUI()
    {

    }

}
