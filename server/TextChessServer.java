package server;
import java.net.*;
import java.io.*;
import java.util.*;

public class TextChessServer
{

    public static void main(String[] args) throws IOException,ClassNotFoundException {
        
        ServerSocket ss = new ServerSocket(Integer.parseInt(args[0]));
        
        System.out.println("Waiting for clients");
        Socket s1 = ss.accept();
        Socket s2 = ss.accept();
        
        System.out.println("Connecting to clients");
        InputStream in1 = s1.getInputStream();
        OutputStream out1 = s1.getOutputStream();
        InputStream in2 = s2.getInputStream();
        OutputStream out2 = s2.getOutputStream();
        
        ObjectOutputStream oos1 = new ObjectOutputStream(out1);
        oos1.writeObject("handshake");
        oos1.flush();
        ObjectInputStream ois1 = new ObjectInputStream(in1);
        ois1.readObject();
        
        ObjectOutputStream oos2 = new ObjectOutputStream(out2);
        oos2.writeObject("handshake");
        oos2.flush();
        ObjectInputStream ois2 = new ObjectInputStream(in2);
        ois2.readObject();
        
        System.out.println("Creating players");
        Player p1 = new Player(ois1,oos1);
        Player p2 = new Player(ois2,oos2);
        
        System.out.println("Starting game");
        Game g = new Game(p1,p2);
        g.startGame();
        
        in1.close();
        out1.close();
        in2.close();
        out2.close();
        s1.close();
        s2.close();
        ss.close();
       
        System.out.println("Game Over");
    }
}
