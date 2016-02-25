import java.util.*;

public class Board
{
    private Piece[][] board;
    private PieceFactory pf;
    private Team turn;
    private Piece whiteKing;
    private Piece blackKing;
    private Set<Piece> whitePieces;
    private Set<Piece> blackPieces;
    
    public Board(Team startingTeam) {
        board = new Piece[8][8];
        pf = new PieceFactory();
        whitePieces = new HashSet<Piece>();
        blackPieces = new HashSet<Piece>();
        turn = startingTeam;
        initializeBoard();
    }
    
    private void initializeBoard() {
        //Make white pawns
        for (int i=0; i<8; i++) {
            createPiece(PieceType.PAWN,Team.WHITE,new Location(i,1));
        }
        //Make black pawns
        for (int i=0; i<8; i++) {
            createPiece(PieceType.PAWN,Team.BLACK,new Location(i,6));
        }
        //Make rooks
        createPiece(PieceType.ROOK,Team.WHITE,new Location(0,0));
        createPiece(PieceType.ROOK,Team.WHITE,new Location(7,0));
        createPiece(PieceType.ROOK,Team.BLACK,new Location(0,7));
        createPiece(PieceType.ROOK,Team.BLACK,new Location(7,7));
        //Make kights
        createPiece(PieceType.KNIGHT,Team.WHITE,new Location(1,0));
        createPiece(PieceType.KNIGHT,Team.WHITE,new Location(6,0));
        createPiece(PieceType.KNIGHT,Team.BLACK,new Location(1,7));
        createPiece(PieceType.KNIGHT,Team.BLACK,new Location(6,7));
        //Make bishops
        createPiece(PieceType.BISHOP,Team.WHITE,new Location(2,0));
        createPiece(PieceType.BISHOP,Team.WHITE,new Location(5,0));
        createPiece(PieceType.BISHOP,Team.BLACK,new Location(2,7));
        createPiece(PieceType.BISHOP,Team.BLACK,new Location(5,7));
        //Make queens
        createPiece(PieceType.QUEEN,Team.WHITE,new Location(3,0));
        createPiece(PieceType.QUEEN,Team.BLACK,new Location(3,7));
        //Make kings
        createPiece(PieceType.KING,Team.WHITE,new Location(4,0));
        createPiece(PieceType.KING,Team.BLACK,new Location(4,7));
        whiteKing = board[4][0];
        blackKing = board[4][7];
    }
    
    public Team getTurn() {
        return turn;
    }
    
    public void flip() {
        if (turn == Team.WHITE) {
            turn = Team.BLACK;
            for (Piece p : blackPieces)
                p.enPassantable = false;
        } else if (turn == Team.BLACK) {
            turn = Team.WHITE;
            for (Piece p : whitePieces)
                p.enPassantable = false;
        }
    }
    
    public Location getKingLocation(Team t) {
        Location returnThis = null;
        if (turn == Team.WHITE) {
            if (t == Team.WHITE) {
                returnThis = whiteKing.loc;
            } else if (t == Team.BLACK) {
                returnThis = new Location(7-blackKing.loc.x,7-blackKing.loc.y);
            }
        } else if (turn == Team.BLACK) {
            if (t == Team.WHITE) {
                returnThis = new Location(7-whiteKing.loc.x,7-whiteKing.loc.y);
            } else if (t == Team.BLACK) {
                returnThis = blackKing.loc;
            }
        }
        return returnThis;
    }
    
    public Piece getPiece(Location l) {
        if (turn == Team.WHITE) {
            return board[l.x][l.y];
        } else if (turn == Team.BLACK) {
            return board[7-l.x][7-l.y];
        } else {
            return null;
        }
    }
    
    public Set<Location> getPiecesLocations(Team t) {
        Set<Location> returnThis = new HashSet<Location>();
        Set<Piece> pieces = t==Team.WHITE?whitePieces:t==Team.BLACK?blackPieces:null;
        for (Piece p : pieces) {
            returnThis.add(p.loc);
        }
        return returnThis;
    }
    
    public void createPiece(PieceType p, Team t, Location l) {
        if (turn == Team.WHITE) {
            if (t == Team.WHITE) {
                board[l.x][l.y] = pf.newPiece(p,t,l);
                whitePieces.add(board[l.x][l.y]);
            } else if (t == Team.BLACK) {
                board[l.x][l.y] = pf.newPiece(p,t,new Location(7-l.x,7-l.y));
                blackPieces.add(board[l.x][l.y]);
            }
        } else if (turn == Team.BLACK) {
            if (t == Team.WHITE) {
                board[7-l.x][7-l.y] = pf.newPiece(p,t,new Location(7-l.x,7-l.y));
                whitePieces.add(board[7-l.x][7-l.y]);
            } else if (t == Team.BLACK) {
                board[7-l.x][7-l.y] = pf.newPiece(p,t,l);
                blackPieces.add(board[7-l.x][7-l.y]);
            }
        }
    }
    
    public void removePiece(Piece p) {
        if (turn == Team.WHITE) {
            if (p.team == Team.WHITE) {
                whitePieces.remove(p);
                board[p.loc.x][p.loc.y] = null;
            } else if (p.team == Team.BLACK) {
                blackPieces.remove(p);
                board[7-p.loc.x][7-p.loc.y] = null;
            }
        } else if (turn == Team.BLACK) {
            if (p.team == Team.WHITE) {
                whitePieces.remove(p);
                board[p.loc.x][p.loc.y] = null;
            } else if (p.team == Team.BLACK) {
                blackPieces.remove(p);
                board[7-p.loc.x][7-p.loc.y] = null;
            }
        }
    }
    
    public void movePiece(Piece p, Location l) {
        if (turn == Team.WHITE) {
            if (p.team == Team.WHITE) {
                board[p.loc.x][p.loc.y] = null;
                p.loc = l;
                board[l.x][l.y] = p;
            } else if (p.team == Team.BLACK) {
                board[7-p.loc.x][7-p.loc.y] = null;
                p.loc = new Location(7-l.x,7-l.y);
                board[l.x][l.y] = p;
            }
        } else if (turn == Team.BLACK) {
            if (p.team == Team.WHITE) {
                board[p.loc.x][p.loc.y] = null;
                p.loc = new Location(7-l.x,7-l.y);
                board[7-l.x][7-l.y] = p;
            } else if (p.team == Team.BLACK) {
                board[7-p.loc.x][7-p.loc.y] = null;
                p.loc = l;
                board[7-l.x][7-l.y] = p;
            }
        }
    }
    
    private Piece[][] copyBoard() {
        Piece[][] returnThis = new Piece[8][8];
        if (turn == Team.WHITE) {
            for (int i=0; i<8; i++) {
                for (int j=0; j<8; j++) {
                    if (board[i][j] != null) {
                        returnThis[i][j] = board[i][j];
                    }
                }
            }
        } else if (turn == Team.BLACK) {
            for (int i=0; i<8; i++) {
                for (int j=0; j<8; j++) {
                    if (board[i][j] != null) {
                        returnThis[7-i][7-j] = board[i][j];
                    }
                }
            }
        }
        return returnThis;
    }
    
    public Piece[][] getBoard() {
        return copyBoard();
    }
    
    public Piece[][] getBoardIfMove(Piece p, Location l) {
        Piece[][] returnThis = getBoard();
        if (turn == Team.WHITE) {
            if (p.team == Team.WHITE) {
                returnThis[p.loc.x][p.loc.y] = null;
                returnThis[l.x][l.y] = p;
            } else if (p.team == Team.BLACK) {
                returnThis[7-p.loc.x][7-p.loc.y] = null;
                returnThis[l.x][l.y] = p;
            }
        } else if (turn == Team.BLACK) {
            if (p.team == Team.WHITE) {
                returnThis[7-p.loc.x][7-p.loc.y] = null;
                returnThis[l.x][l.y] = p;
            } else if (p.team == Team.BLACK) {
                returnThis[p.loc.x][p.loc.y] = null;
                returnThis[l.x][l.y] = p;
            }
        }
        return returnThis;
    }

}
