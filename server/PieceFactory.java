package server;

import java.util.*;

public class PieceFactory
{
    private int numInstances = 0;
    
    private class Pawn extends Piece{
        public Pawn(Team team, Location loc) {
            super(PieceType.PAWN,team,loc,numInstances++);
        }
        
        public Set<Location> _validMoves(Board board) {
            HashSet<Location> returnThis = new HashSet<Location>();
            //check if pawn can move one up
            if (board.getPiece(new Location(loc.x,loc.y+1))==null) {
                returnThis.add(new Location(loc.x,loc.y+1));
                //check if pawn can move two up
                if (!hasMoved && board.getPiece(new Location(loc.x,loc.y+2))==null) {
                    returnThis.add(new Location(loc.x,loc.y+2));
                }
            }
            //check if the pawn can take diagonal
            if (loc.x<7) {
                if (board.getPiece(new Location(loc.x+1,loc.y+1))!=null) {
                    if (board.getPiece(new Location(loc.x+1,loc.y+1)).team!=team) {
                        returnThis.add(new Location(loc.x+1,loc.y+1));
                    }
                } else {
                    if (board.getPiece(new Location(loc.x+1,loc.y))!=null && board.getPiece(new Location(loc.x+1,loc.y)).enPassantable) {
                        returnThis.add(new Location(loc.x+1,loc.y+1));
                    }
                }
            }
            if (loc.x>0) {
                if (board.getPiece(new Location(loc.x-1,loc.y+1))!=null) {
                    if (board.getPiece(new Location(loc.x-1,loc.y+1)).team!=team) {
                        returnThis.add(new Location(loc.x-1,loc.y+1));
                    }
                } else {
                    if (board.getPiece(new Location(loc.x-1,loc.y))!=null && board.getPiece(new Location(loc.x-1,loc.y)).enPassantable) {
                        returnThis.add(new Location(loc.x-1,loc.y+1));
                    }
                }
            }

            return returnThis;
        }
        
        public void moveTo(Location l, Board b) {
            if (l.y-loc.y == 2) {
                enPassantable = true;
            }
            super.moveTo(l,b);
            Piece killedByEnPassant = b.getPiece(new Location(loc.x,loc.y-1));
            if (killedByEnPassant!=null && killedByEnPassant.enPassantable) {
                b.removePiece(killedByEnPassant);
            }
        }
    }
    
    private class Rook extends Piece{
        public Rook(Team team, Location loc) {
            super(PieceType.ROOK,team,loc,numInstances++);
        }
        
        public Set<Location> _validMoves(Board board) {
            HashSet<Location> returnThis = new HashSet<Location>();
            //Valid moves N
            Location oN = Utils.obstruction(loc,Direction.N,board.getBoard());
            Piece pN = oN==null?null:board.getPiece(oN);
            if (pN!=null&&pN.team!=team) {
                returnThis.add(oN);
            }
            for (int y=loc.y+1; (oN!=null&&y<oN.y)||(oN==null&&y<8); y++) {
                returnThis.add(new Location(loc.x,y));
            }
            //Valid moves E
            Location oE = Utils.obstruction(loc,Direction.E,board.getBoard());
            Piece pE = oE==null?null:board.getPiece(oE);
            if (pE!=null&&pE.team!=team) {
                returnThis.add(oE);
            }
            for (int x=loc.x+1; (oE!=null&&x<oE.x)||(oE==null&&x<8); x++) {
                returnThis.add(new Location(x,loc.y));
            }
            //Valid moves W
            Location oW = Utils.obstruction(loc,Direction.W,board.getBoard());
            Piece pW = oW==null?null:board.getPiece(oW);
            if (pW!=null&&pW.team!=team) {
                returnThis.add(oW);
            }
            for (int x=loc.x-1; (oW!=null&&x>oW.x)||(oW==null&&x>=0); x--) {
                returnThis.add(new Location(x,loc.y));
            }
            //Valid moves S
            Location oS = Utils.obstruction(loc,Direction.S,board.getBoard());
            Piece pS = oS==null?null:board.getPiece(oS);
            if (pS!=null&&pS.team!=team) {
                returnThis.add(oS);
            }
            for (int y=loc.y-1; (oS!=null&&y>oS.y)||(oS==null&&y>=0); y--) {
                returnThis.add(new Location(loc.x,y));
            }
            return returnThis;
        }
    }
    
    private class Knight extends Piece{
        public Knight(Team team, Location loc) {
            super(PieceType.KNIGHT,team,loc,numInstances++);
        }
        
        public Set<Location> _validMoves(Board board) {
            HashSet<Location> returnThis = new HashSet<Location>();
            int[][] arr = new int[][]{{1,-1},{2,-2}};
            for (int i=0; i<2; i++) {
                for (int movX : arr[i]) {
                    for (int movY : arr[1-i]) {
                        if (Utils.insideBoard(loc.x+movX,loc.y+movY)) {
                            Piece d = board.getPiece(new Location(loc.x+movX,loc.y+movY));
                            if (d==null || d.team!=team) {
                                returnThis.add(new Location(loc.x+movX,loc.y+movY));
                            }
                        }
                    }
                }
            }
            return returnThis;
        }
    }
    
    private class Bishop extends Piece{
        public Bishop(Team team, Location loc) {
            super(PieceType.BISHOP,team,loc,numInstances++);
        }
        
        public Set<Location> _validMoves(Board board) {
            HashSet<Location> returnThis = new HashSet<Location>();
            //Valid moves NE
            Location oNE = Utils.obstruction(loc,Direction.NE,board.getBoard());
            Piece pNE = oNE==null?null:board.getPiece(oNE);
            if (pNE!=null&&pNE.team!=team) {
                returnThis.add(oNE);
            }
            for (int x=loc.x+1, y=loc.y+1; (oNE!=null&&x<oNE.x&&y<oNE.y)||(oNE==null&&x<8&&y<8); x++,y++) {
                returnThis.add(new Location(x,y));
            }
            //Valid moves NW
            Location oNW = Utils.obstruction(loc,Direction.NW,board.getBoard());
            Piece pNW = oNW==null?null:board.getPiece(oNW);
            if (pNW!=null&&pNW.team!=team) {
                returnThis.add(oNW);
            }
            for (int x=loc.x-1, y=loc.y+1; (oNW!=null&&x>oNW.x&&y<oNW.y)||(oNW==null&&x>=0&&y<8); x--,y++) {
                returnThis.add(new Location(x,y));
            }
            //Valid moves SE
            Location oSE = Utils.obstruction(loc,Direction.SE,board.getBoard());
            Piece pSE = oSE==null?null:board.getPiece(oSE);
            if (pSE!=null&&pSE.team!=team) {
                returnThis.add(oSE);
            }
            for (int x=loc.x+1, y=loc.y-1; (oSE!=null&&x<oSE.x&&y>oSE.y)||(oSE==null&&x<8&&y>=0); x++,y--) {
                returnThis.add(new Location(x,y));
            }
            //Valid moves SW
            Location oSW = Utils.obstruction(loc,Direction.SW,board.getBoard());
            Piece pSW = oSW==null?null:board.getPiece(oSW);
            if (pSW!=null&&pSW.team!=team) {
                returnThis.add(oSW);
            }
            for (int x=loc.x-1, y=loc.y-1; (oSW!=null&&x>oSW.x&&y>oSW.y)||(oSW==null&&x>=0&&y>=0); x--,y--) {
                returnThis.add(new Location(x,y));
            }
            return returnThis;
        }
    }
    
    private class Queen extends Piece{
        public Queen(Team team, Location loc) {
            super(PieceType.QUEEN,team,loc,numInstances++);
        }
        
        public Set<Location> _validMoves(Board board) {
            HashSet<Location> returnThis = new HashSet<Location>();
            //Valid moves N
            Location oN = Utils.obstruction(loc,Direction.N,board.getBoard());
            Piece pN = oN==null?null:board.getPiece(oN);
            if (pN!=null&&pN.team!=team) {
                returnThis.add(oN);
            }
            for (int y=loc.y+1; (oN!=null&&y<oN.y)||(oN==null&&y<8); y++) {
                returnThis.add(new Location(loc.x,y));
            }
            //Valid moves E
            Location oE = Utils.obstruction(loc,Direction.E,board.getBoard());
            Piece pE = oE==null?null:board.getPiece(oE);
            if (pE!=null&&pE.team!=team) {
                returnThis.add(oE);
            }
            for (int x=loc.x+1; (oE!=null&&x<oE.x)||(oE==null&&x<8); x++) {
                returnThis.add(new Location(x,loc.y));
            }
            //Valid moves W
            Location oW = Utils.obstruction(loc,Direction.W,board.getBoard());
            Piece pW = oW==null?null:board.getPiece(oW);
            if (pW!=null&&pW.team!=team) {
                returnThis.add(oW);
            }
            for (int x=loc.x-1; (oW!=null&&x>oW.x)||(oW==null&&x>=0); x--) {
                returnThis.add(new Location(x,loc.y));
            }
            //Valid moves S
            Location oS = Utils.obstruction(loc,Direction.S,board.getBoard());
            Piece pS = oS==null?null:board.getPiece(oS);
            if (pS!=null&&pS.team!=team) {
                returnThis.add(oS);
            }
            for (int y=loc.y-1; (oS!=null&&y>oS.y)||(oS==null&&y>=0); y--) {
                returnThis.add(new Location(loc.x,y));
            }
            //Valid moves NE
            Location oNE = Utils.obstruction(loc,Direction.NE,board.getBoard());
            Piece pNE = oNE==null?null:board.getPiece(oNE);
            if (pNE!=null&&pNE.team!=team) {
                returnThis.add(oNE);
            }
            for (int x=loc.x+1, y=loc.y+1; (oNE!=null&&x<oNE.x&&y<oNE.y)||(oNE==null&&x<8&&y<8); x++,y++) {
                returnThis.add(new Location(x,y));
            }
            //Valid moves NW
            Location oNW = Utils.obstruction(loc,Direction.NW,board.getBoard());
            Piece pNW = oNW==null?null:board.getPiece(oNW);
            if (pNW!=null&&pNW.team!=team) {
                returnThis.add(oNW);
            }
            for (int x=loc.x-1, y=loc.y+1; (oNW!=null&&x>oNW.x&&y<oNW.y)||(oNW==null&&x>=0&&y<8); x--,y++) {
                returnThis.add(new Location(x,y));
            }
            //Valid moves SE
            Location oSE = Utils.obstruction(loc,Direction.SE,board.getBoard());
            Piece pSE = oSE==null?null:board.getPiece(oSE);
            if (pSE!=null&&pSE.team!=team) {
                returnThis.add(oSE);
            }
            for (int x=loc.x+1, y=loc.y-1; (oSE!=null&&x<oSE.x&&y>oSE.y)||(oSE==null&&x<8&&y>=0); x++,y--) {
                returnThis.add(new Location(x,y));
            }
            //Valid moves SW
            Location oSW = Utils.obstruction(loc,Direction.SW,board.getBoard());
            Piece pSW = oSW==null?null:board.getPiece(oSW);
            if (pSW!=null&&pSW.team!=team) {
                returnThis.add(oSW);
            }
            for (int x=loc.x-1, y=loc.y-1; (oSW!=null&&x>oSW.x&&y>oSW.y)||(oSW==null&&x>=0&&y>=0); x--,y--) {
                returnThis.add(new Location(x,y));
            }
            return returnThis;
        }
    }
    
    private class King extends Piece{
        public King(Team team, Location loc) {
            super(PieceType.KING,team,loc,numInstances++);
        }
        
        public Set<Location> _validMoves(Board board) {
            HashSet<Location> returnThis = new HashSet<Location>();
            for (int movX=-1; movX<=1; movX++) {
                for (int movY=-1; movY<=1; movY++) {
                    if (Utils.insideBoard(loc.x+movX,loc.y+movY)) {
                        Piece d = board.getPiece(new Location(loc.x+movX,loc.y+movY));
                        if(d==null || d.team!=team) {
                            returnThis.add(new Location(loc.x+movX,loc.y+movY));
                        }
                    }
                }
            }
            //Check for left side casteling
            LEFT:{
                //king and the rook havent moved
                Piece leftRook = board.getPiece(new Location(0,0));
                if (hasMoved || (leftRook!=null && leftRook.hasMoved)) {
                    break LEFT;
                }
                //there are not obstructions between the king and the rook
                if (!Utils.obstruction(loc,Direction.W,board.getBoard()).equals(new Location(0,0))) {
                    break LEFT;
                }
                //the king is not and will not be in check for every piece till destination
                if (Utils.isCheck(loc,board.getBoard()) || 
                    Utils.isCheck(new Location(loc.x-1,0),board.getBoardIfMove(this,new Location(loc.x-1,0))) ||
                    Utils.isCheck(new Location(loc.x-2,0),board.getBoardIfMove(this,new Location(loc.x-2,0)))) {
                    break LEFT;
                }
                returnThis.add(new Location(loc.x-2,0));
            }
            //Check for right side casteling
            RIGHT:{
                //king and the rook havent moved
                Piece rightRook = board.getPiece(new Location(7,0));
                if (hasMoved || (rightRook!=null && rightRook.hasMoved)) {
                    break RIGHT;
                }
                //there are not obstructions between the king and the rook
                if (!Utils.obstruction(loc,Direction.E,board.getBoard()).equals(new Location(7,0))) {
                    break RIGHT;
                }
                //the king is not and will not be in check for every piece till destination
                if (Utils.isCheck(loc,board.getBoard()) || 
                    Utils.isCheck(new Location(loc.x+1,0),board.getBoardIfMove(this,new Location(loc.x+1,0))) ||
                    Utils.isCheck(new Location(loc.x+2,0),board.getBoardIfMove(this,new Location(loc.x+2,0)))) {
                    break RIGHT;
                }
                returnThis.add(new Location(loc.x+2,0));
            }
            return returnThis;
        }
        public void moveTo(Location l, Board b) {
            if (l.x-loc.x == 2) {
                b.getPiece(new Location(7,0)).moveTo(new Location(l.x-1,0),b);
            }
            if (l.x-loc.x == -2) {
                b.getPiece(new Location(0,0)).moveTo(new Location(l.x+1,0),b);
            }
            super.moveTo(l,b);
        }
    }
    
    public Piece newPiece(PieceType type, Team team, Location loc) {
        switch (type) {
            case PAWN: return new Pawn(team,loc); 
            case ROOK: return new Rook(team,loc); 
            case KNIGHT: return new Knight(team,loc); 
            case BISHOP: return new Bishop(team,loc); 
            case QUEEN: return new Queen(team,loc); 
            case KING: return new King(team,loc); 
            default: return null;
        }
    }
}
