using UnityEngine;
using System.Collections;
using System;

public class Register {

    public static Type[] registeredClass;

    public static void registerClass()
    {
        registeredClass = new Type[] {
            typeof(MessagePacket),
            typeof(MainState_Connection_Request_Packet),
            typeof(Disconnect_Client_Packet)
        };
    }

    public static Type getClass(int id)
    {
        return registeredClass[id];
    }

    public static int getId(Type cl)
    {
        for (int i = 0; i < registeredClass.Length; i++)
        {
            if (cl == registeredClass[i]) return i;
        }
        return -1;
    }

    public static object instantiate(int id)
    {
        return getClass(id).GetConstructor(Type.EmptyTypes).Invoke(Type.EmptyTypes);
    }
}
