using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using UnityEngine;
using System.IO;
using UnityEditor;

class TerrainExport : EditorWindow
{

    static TerrainData terraindata;

    [MenuItem("Terrain/Export Height Map as MAP")]
    static void Init()
    {
        terraindata = null;
        Terrain terrain = null;

        if (Selection.activeGameObject)
            terrain = Selection.activeGameObject.GetComponent<Terrain>();

        if (!terrain)
        {
            terrain = Terrain.activeTerrain;
        }
        if (terrain)
        {
            terraindata = terrain.terrainData;
        }
        if (terraindata == null)
        {
            EditorUtility.DisplayDialog("No terrain selected", "Please select a terrain.", "Cancel");
            return;
        }

        float[,] rawHeights = terraindata.GetHeights(0, 0, terraindata.heightmapWidth, terraindata.heightmapHeight);
        byte[] data = new byte[terraindata.heightmapWidth * terraindata.heightmapHeight * 4 + 16];

        int a = terraindata.heightmapWidth;
        data[0] = (byte)((a >> 24) & 0xff);
        data[1] = (byte)((a >> 16) & 0xff);
        data[2] = (byte)((a >> 8) & 0xff);
        data[3] = (byte)((a >> 0) & 0xff);

        a = terraindata.heightmapHeight;
        data[4] = (byte)((a >> 24) & 0xff);
        data[5] = (byte)((a >> 16) & 0xff);
        data[6] = (byte)((a >> 8) & 0xff);
        data[7] = (byte)((a >> 0) & 0xff);

        int pointer = 8;

        byte[] array = BitConverter.GetBytes(terraindata.size.x);
        foreach (byte b in array)
        {
            data[pointer++] = b;
        }

        array = BitConverter.GetBytes(terraindata.size.z);
        foreach (byte b in array)
        {
            data[pointer++] = b;
        }

        float max = 0;
        for (int x = 0; x < terraindata.heightmapWidth; x++)
        {
            for (int y = 0; y < terraindata.heightmapHeight; y++)
            {
                float s = rawHeights[y, x] * terraindata.heightmapScale.y;
                max = (s > max) ? s : max;
                array = BitConverter.GetBytes(s);
                foreach (byte b in array)
                {
                    data[pointer++] = b;
                }
            }
        }
        File.WriteAllBytes(Application.dataPath + "/" + "heightmap.map", data);
        EditorUtility.DisplayDialog("Heightmap Duplicated", "Saved as PNG in Assets/ as: " + "heightmap.map  maxheight:" + max + " width:" + terraindata.size.x + " height:" + terraindata.size.z, "Ok");

    }

}
