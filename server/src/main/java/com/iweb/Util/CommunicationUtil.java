package com.iweb.Util;

import java.io.*;
import java.net.Socket;

/**
 * @author Liu Xiong
 * @date 26/11/2023 下午5:06
 */
public class CommunicationUtil {
    public static boolean send(Socket socket, String message) throws IOException {
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(message);
        return true;
    }

    public static String receive(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        return dis.readUTF();
    }
}
