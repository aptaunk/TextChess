import java.util.*;

public class BoardPrinter
{
    public static void print(Piece[][] board) {
        String horizontalLine = "  +----+----+----+----+----+----+----+----+";
        System.out.println(horizontalLine);
        for (int i=7; i>=0; i--) {
            System.out.print(i+" |");
            for (int j=0; j<8; j++) {
                if (board[j][i] == null) {
                    System.out.print("    ");
                } else {
                    System.out.print(" "+board[j][i].team.s);
                    System.out.print(board[j][i].type.s+" ");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.println(horizontalLine);
        }
        System.out.println("    0    1    2    3    4    5    6    7");
        System.out.println();
    }
    
    public static void print(Set<Location> validPieces, Set<Location> validMoves, Piece[][] board) {
        String horizontalLine = "  +----+----+----+----+----+----+----+----+";
        System.out.println(horizontalLine);
        for (int i=7; i>=0; i--) {
            System.out.print(i+" |");
            for (int j=0; j<8; j++) {
                if (validMoves.contains(new Location(j,i))) {
                    if (board[j][i] == null) {
                        System.out.print("[  ]");
                    } else {
                        System.out.print("["+board[j][i].team.s);
                        System.out.print(board[j][i].type.s+"]");
                    }
                } else if (validPieces.contains(new Location(j,i))) {
                    if (board[j][i] == null) {
                        System.out.print("{  }");
                    } else {
                        System.out.print("{"+board[j][i].team.s);
                        System.out.print(board[j][i].type.s+"}");
                    }
                } else {
                    if (board[j][i] == null) {
                        System.out.print("    ");
                    } else {
                        System.out.print(" "+board[j][i].team.s);
                        System.out.print(board[j][i].type.s+" ");
                    }
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.println(horizontalLine);
        }
        System.out.println("    0    1    2    3    4    5    6    7");
        System.out.println();
    }
}
