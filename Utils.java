
public class Utils
{
    public static Location obstruction(Location p, Direction d, Piece[][] b) {
        int x,y;
        switch(d) {
            case N: for(y=p.y+1;y<8;y++){if(b[p.x][y]!=null){return new Location(p.x,y);}} break;
            case E: for(x=p.x+1;x<8;x++){if(b[x][p.y]!=null){return new Location(x,p.y);}} break;
            case W: for(x=p.x-1;x>=0;x--){if(b[x][p.y]!=null){return new Location(x,p.y);}} break;
            case S: for(y=p.y-1;y>=0;y--){if(b[p.x][y]!=null){return new Location(p.x,y);}} break;
            case NE: for(x=p.x+1,y=p.y+1;x<8&&y<8;x++,y++){if(b[x][y]!=null){return new Location(x,y);}} break;
            case NW: for(x=p.x-1,y=p.y+1;x>=0&&y<8;x--,y++){if(b[x][y]!=null){return new Location(x,y);}} break;
            case SE: for(x=p.x+1,y=p.y-1;x<8&&y>=0;x++,y--){if(b[x][y]!=null){return new Location(x,y);}} break;
            case SW: for(x=p.x-1,y=p.y-1;x>=0&&y>=0;x--,y--){if(b[x][y]!=null){return new Location(x,y);}} break;
        }
        return null;
    }
    
    public static boolean insideBoard(int x, int y) {
        return (x>=0 && x<8 && y>=0 && y<8);
    }
    
    public static boolean isCheck(Location king, Piece[][] board) {
        Team kingTeam = board[king.x][king.y].team;
        //Check Rooks and Queens
        for (Direction d : new Direction[]{Direction.N,Direction.E,Direction.W,Direction.S}) {
            Location l = obstruction(king,d,board);
            Piece p = l==null?null:board[l.x][l.y];
            if (p!=null && p.team!=kingTeam && (p.type==PieceType.ROOK || p.type==PieceType.QUEEN)) {
                return true;
            }
        }
        //Check Bishops and Queens
        for (Direction d : new Direction[]{Direction.NE,Direction.NW,Direction.SE,Direction.SW}) {
            Location l = obstruction(king,d,board);
            Piece p = l==null?null:board[l.x][l.y];
            if (p!=null && p.team!=kingTeam && (p.type==PieceType.BISHOP || p.type==PieceType.QUEEN)) {
                return true;
            }
        }
        //Check pawns
        if (king.y < 7) {
            if (king.x > 0) {
                Piece lp = board[king.x-1][king.y+1];
                if (lp!=null && lp.team!=kingTeam && lp.type==PieceType.PAWN) {
                    return true;
                }
            }
            if (king.x < 7) {
                Piece rp = board[king.x+1][king.y+1];
                if (rp!=null && rp.team!=kingTeam && rp.type==PieceType.PAWN) {
                    return true;
                }
            }
        }
        //Check knights
        int[][] arr = new int[][]{{1,-1},{2,-2}};
        for (int i=0; i<2; i++) {
            for (int movX : arr[i]) {
                for (int movY : arr[1-i]) {
                    Piece k = insideBoard(king.x+movX,king.y+movY)?board[king.x+movX][king.y+movY]:null;
                    if(k!=null && k.team!=kingTeam && k.type==PieceType.KNIGHT) {
                        return true;
                    }
                }
            }
        }
        //Check kings
        for (int movX=-1; movX<=1; movX++) {
            for (int movY=-1; movY<=1; movY++) {
                Piece k = insideBoard(king.x+movX,king.y+movY)?board[king.x+movX][king.y+movY]:null;
                if(k!=null && k.team!=kingTeam && k.type==PieceType.KING) {
                    return true;
                }
            }
        }
        return false;
    }
}
