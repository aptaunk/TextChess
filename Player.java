import java.util.*;

public class Player
{
    private Team team;
    private Scanner sc = new Scanner(System.in);
    
    public Player(Team team) {
        this.team = team;
    }
    
    public Location getMove() {
        String sString = sc.next();
        return new Location(sString.charAt(0)-'0',sString.charAt(1)-'0');
    }

    public PieceType pawnPromotion() {
        String s = sc.next();
        char c = s.charAt(0);
        switch (c) {
            case 'R': return PieceType.ROOK;
            case 'K': return PieceType.KNIGHT;
            case 'B': return PieceType.BISHOP;
            case 'Q': return PieceType.QUEEN;
            default: return null;
        }
    }
}
