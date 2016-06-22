using System;
using UnityEngine;
using System.Collections.Generic;
using System.Linq;
using System.Text;

class MainState : MonoBehaviour
{
    public string stringPseudo = "Pseudo";
    public string stringHost = "IP:PORT";
    private int preStatus = -1;

    public float preTime = 0;
    public Boolean windowOpen = false;
    public int winID = -1;
    public Rect windowRect = new Rect(Screen.width / 2 - 100, Screen.height / 2 - 50, 200, 100);

    void Start()
    {
        windowRect = new Rect(Screen.width / 2 - 100, Screen.height / 2 - 50, 200, 100);
    }

    void Update()
    {
        if(preStatus != MainClient.connected)
        {
            if (MainClient.connected == MainState_Connection_Request_Packet.REQUEST)
            {
                preTime = Time.time;
            }
            if (MainClient.connected == MainState_Connection_Request_Packet.ACCEPTED)
            {
                winID = 0;
                windowOpen = true;
            }
            if (MainClient.connected == MainState_Connection_Request_Packet.REFUSED)
            {
                winID = 5;
                windowOpen = true;
            }
            if (MainClient.connected == MainState_Connection_Request_Packet.TIME_OUT)
            {
                winID = 4;
                windowOpen = true;
                MainClient.client.disconnect();
            }
            if (MainClient.connected == MainState_Connection_Request_Packet.ALREADY_CONNECTED)
            {
                winID = 7;
                windowOpen = true;
            }
            if (MainClient.connected == MainState_Connection_Request_Packet.PSEUDO_INCORRECT)
            {
                winID = 2;
                windowOpen = true;
            }
            preStatus = MainClient.connected;
        }
        if(preStatus == 0 && preStatus == MainClient.connected)
        {
            if ((Time.time - preTime) >= 10)
            {
                MainClient.connected = MainState_Connection_Request_Packet.TIME_OUT;
            }
        }
    }

    void OnGUI()
    {
        GUILayout.BeginArea(new Rect(Screen.width/2 - 50, Screen.height / 2 - 50, 100, 100));
        stringHost = GUILayout.TextField(stringHost);
        stringPseudo = GUILayout.TextField(stringPseudo);
        if (GUILayout.Button("Login"))
        {
            if(stringHost.Length > 0)
            {
                if(stringPseudo.Length > 0)
                {
                    string[] c = stringHost.Split(':');
                    string host = "";
                    int port = 9999;
                    if (c.Length == 1)
                    {
                        host = c[0];
                        MainClient.connect(host, port, stringPseudo);
                        winID = 6;
                        windowOpen = true;
                    }
                    else
                    {
                        host = c[0];
                        if (Int32.TryParse(c[1], out port))
                        {
                            MainClient.connect(host, port, stringPseudo);
                            winID = 6;
                            windowOpen = true;
                        }
                        else
                        {
                            winID = 1;
                            windowOpen = true;
                        }
                    }
                }
                else
                {
                    winID = 2;
                    windowOpen = true;
                }

            }else
            {
                winID = 3;
                windowOpen = true;
            }
        }
        GUILayout.EndArea();
        if (windowOpen)
        {
            GUI.Box(windowRect, "");
            GUI.Box(windowRect, "");
            GUI.Box(windowRect, "");
            GUI.Box(windowRect, "");
            windowRect = GUILayout.Window(winID, windowRect, connectedWindow, "Information");
        }

    }

    void connectedWindow(int windowID)
    {
        if(windowID == 0)
        {
            GUILayout.Label("Connexion réussi !");
            if (GUILayout.Button("Valider"))
            {
                windowOpen = false;
            }
        }
        else if (windowID == 1)
        {
            GUILayout.Label("Le Port entré est incorrect !");
            if (GUILayout.Button("Valider"))
            {
                windowOpen = false;
            }
        }
        else if (windowID == 2)
        {
            GUILayout.Label("Pseudo incorrect !");
            if (GUILayout.Button("Valider"))
            {
                windowOpen = false;
            }
        }
        else if (windowID == 3)
        {
            GUILayout.Label("Aucune ip entré !");
            if (GUILayout.Button("Valider"))
            {
                windowOpen = false;
            }
        }
        else if (windowID == 4)
        {
            GUILayout.Label("Echec de la connexion");
            if (GUILayout.Button("Valider"))
            {
                windowOpen = false;
            }
        }
        else if (windowID == 5)
        {
            GUILayout.Label("Connexion refusée !");
            if (GUILayout.Button("Valider"))
            {
                windowOpen = false;
            }
        }
        else if (windowID == 6)
        {
            GUILayout.Label("Connexion en cours...");
        }
        else if (windowID == 7)
        {
            GUILayout.Label("Vous etes déja connecté !");
            if (GUILayout.Button("Valider"))
            {
                windowOpen = false;
            }
        }
    }
}
