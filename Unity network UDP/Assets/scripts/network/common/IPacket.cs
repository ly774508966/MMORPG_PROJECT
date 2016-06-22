using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

interface IPacket
{
    void read(DataBuffer data);
    void write(DataBuffer data);
    void manage(byte[] data, Client client);
}
