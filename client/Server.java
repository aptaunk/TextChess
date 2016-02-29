package client;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
    private Socket s;
    
    private InputStream in;
    private OutputStream out;
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    public Server() {
        s = new Socket();
    }
    
    public boolean connectToServer(String hostname, int port) {
        try {
            s.connect(new InetSocketAddress(hostname,port));
            
            in = s.getInputStream();
            out = s.getOutputStream();
            
            oos = new ObjectOutputStream(out);
            oos.writeObject("handshake");
            oos.flush();
            ois = new ObjectInputStream(in);
            ois.readObject().toString();
            return true;
        } catch (IOException|ClassNotFoundException e) {
            return false;
        }
    }
    
    public String read() {
        try {
            return ois.readObject().toString();
        } catch (IOException|ClassNotFoundException e) {
            return null;
        }
    }
    
    public void write(String s) {
        try {
            oos.writeObject(s);
            oos.flush();
        } catch (IOException e) {}
    }
    
}
