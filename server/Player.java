package server;

import java.io.*;
import java.util.*;

public class Player
{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public Player(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }
    
    public Location getMove(String s) {
        try {
            out.writeObject(s);
            out.writeObject("getMove");
            String sString = in.readObject().toString();
            return new Location(sString.charAt(0)-'0',sString.charAt(1)-'0');
        } catch (IOException|ClassNotFoundException e) {
            return null;
        }
    }

    public PieceType pawnPromotion(String s) {
        try {
            out.writeObject(s);
            out.writeObject("getMove");
            char c = in.readObject().toString().charAt(0);
            switch (c) {
                case 'R': return PieceType.ROOK;
                case 'K': return PieceType.KNIGHT;
                case 'B': return PieceType.BISHOP;
                case 'Q': return PieceType.QUEEN;
                default: return null;
            }
        } catch (IOException|ClassNotFoundException e) {
            return null;
        }
    }
    
    public void send(String s) {
        try {
            out.writeObject(s);
        } catch (IOException e) {
            return;
        }
    }
    
    public void endGame(String s) {
        try {
            out.writeObject(s);
            out.writeObject("endGame");
            in.close();
            out.close();
        } catch (IOException e) {
            return;
        }
    }
}

