package server;

import java.util.*;

public class BoardPrinter
{
    public static String print(Piece[][] board) {
        String returnThis = "";
        String horizontalLine = "  +----+----+----+----+----+----+----+----+";
        returnThis += horizontalLine+"\n";
        for (int i=7; i>=0; i--) {
            returnThis += i+" |";
            for (int j=0; j<8; j++) {
                if (board[j][i] == null) {
                    returnThis += "    ";
                } else {
                    returnThis += " "+board[j][i].team.s;
                    returnThis += board[j][i].type.s+" ";
                }
                returnThis += "|";
            }
            returnThis += "\n"+horizontalLine+"\n";
        }
        returnThis += "    0    1    2    3    4    5    6    7\n\n";
        return returnThis;
    }
    
    public static String print(Set<Location> validPieces, Set<Location> validMoves, Piece[][] board) {
        String returnThis = "";
        String horizontalLine = "  +----+----+----+----+----+----+----+----+";
        returnThis += horizontalLine+"\n";
        for (int i=7; i>=0; i--) {
            returnThis += i+" |";
            for (int j=0; j<8; j++) {
                if (validMoves.contains(new Location(j,i))) {
                    if (board[j][i] == null) {
                        returnThis += "[  ]";
                    } else {
                        returnThis += "["+board[j][i].team.s;
                        returnThis += board[j][i].type.s+"]";
                    }
                } else if (validPieces.contains(new Location(j,i))) {
                    if (board[j][i] == null) {
                        returnThis += "{  }";
                    } else {
                        returnThis += "{"+board[j][i].team.s;
                        returnThis += board[j][i].type.s+"}";
                    }
                } else {
                    if (board[j][i] == null) {
                        returnThis += "    ";
                    } else {
                        returnThis += " "+board[j][i].team.s;
                        returnThis += board[j][i].type.s+" ";
                    }
                }
                returnThis += "|";
            }
            returnThis += "\n"+horizontalLine+"\n";
        }
        returnThis += "    0    1    2    3    4    5    6    7\n\n";
        return returnThis;
    }
}

