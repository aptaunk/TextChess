package server;

import java.util.*;

public abstract class Piece
{
    public final Team team;
    protected Location loc;
    protected final PieceType type;
    private final int instance;
    public boolean hasMoved = false;
    public boolean enPassantable = false;
    
    protected Piece (PieceType type, Team team, Location loc, int instance) {
        this.type = type;
        this.team = team;
        this.loc = loc;
        this.instance = instance;
    }
    
    protected abstract Set<Location> _validMoves(Board board);
    
    private Set<Location> kingValidMoves(Board board) {
        Set<Location> returnThis = _validMoves(board);
        Iterator<Location> iter = returnThis.iterator();
        while (iter.hasNext()) {
            Location l = iter.next();
            if (Utils.isCheck(l,board.getBoardIfMove(this,l))) {
                iter.remove();
            }
        }
        return returnThis;
    }
    
    public Set<Location> validMoves(Board board) {
        if (board.getPiece(loc).type==PieceType.KING) {
            return kingValidMoves(board);
        }
        Set<Location> returnThis = _validMoves(board);
        Iterator<Location> iter = returnThis.iterator();
        while (iter.hasNext()) {
            Location l = iter.next();
            if (Utils.isCheck(board.getKingLocation(team),board.getBoardIfMove(this,l))) {
                iter.remove();
            }
        }
        return returnThis;
    }
    
    public void moveTo(Location l, Board board) {
        hasMoved = true;
        Piece opponent = board.getPiece(l);
        if (opponent != null) {
            board.removePiece(opponent);
        }
        board.movePiece(this,l);
    }
    
    public final boolean equals(Object o) {
        return (o instanceof Piece && ((Piece)o).instance == this.instance);
    }
    
    public final int hashCode() {
        return new String(""+instance).hashCode();
    }
}

