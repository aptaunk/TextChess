package client;

import java.io.*;
import java.util.*;

public class TextChessClient
{
    private static Server s = new Server();
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        
        if (!s.connectToServer(args[0],Integer.parseInt(args[1]))) {
            System.out.println("Can't connect to the server");
            return;
        }
        
        while(true) {
            String str = s.read();
            if (str.equals("getMove")) {
                System.in.read(new byte[System.in.available()]);
                s.write(sc.nextLine());
                continue;
            } else if (str.equals("endGame")) {
                return;
            }
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"+str);
        }
        
    }
}
