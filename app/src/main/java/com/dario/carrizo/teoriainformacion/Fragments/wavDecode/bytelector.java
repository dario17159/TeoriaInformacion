package com.dario.carrizo.teoriainformacion.Fragments.wavDecode;

import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;

public class bytelector {

    public bytelector()
    {
    }

    public static byte[] readFromFile(String filePath, int position, int size)
            throws IOException
    {
        RandomAccessFile file = new RandomAccessFile(filePath, "r");
        file.seek(position);
        byte bytes[] = new byte[size];
        file.read(bytes);
        file.close();
        return bytes;
    }
}
