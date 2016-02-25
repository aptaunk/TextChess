import java.io.*;
import java.util.*;

public class Game
{
    private Board board = new Board(Team.WHITE);
    private Player whitePlayer = new Player(Team.WHITE);
    private Player blackPlayer = new Player(Team.BLACK);
    private Player nextPlayer = whitePlayer;
    
    private Piece pickStartingPiece(Player p, Set<Location> validStartingPieces) {
        System.out.println(board.getTurn()+" starting piece:");
        Location s = p.getMove();
        return validStartingPieces.contains(s)?board.getPiece(s):null;
    }
    
    private boolean makeMove(Player p, Piece sp, Set<Location> validMoves) {

        System.out.println("Move to:");
        Location e = p.getMove();
        
        if (!validMoves.contains(e)) {
            return false;
        }
        
        //Check for pawn promotion
        if (sp.type==PieceType.PAWN && e.y==7) {
            
            System.out.println("Pawn promoted to (Rook, Knight, Bishop, Queen):");
            PieceType pt = p.pawnPromotion();
            
            if (pt == null) {
                return false;
            }
            
            board.removePiece(sp);
            board.createPiece(pt,board.getTurn(),e);
            
        } else {
            sp.moveTo(e,board);
        }
        
        board.flip();
        return true;
    }

    public static void main(String[] args) throws IOException {
        
        Game game = new Game();
        
        while (true) {
            
            //Find valid starting pieces
            Set<Location> vspl = game.board.getPiecesLocations(game.board.getTurn());
            Iterator<Location> iter = vspl.iterator();
            while (iter.hasNext()) {
                Location l = iter.next();
                if (game.board.getPiece(l).validMoves(game.board).isEmpty()) {
                    iter.remove();
                }
            }
            
            if (vspl.isEmpty()) {
                if (Utils.isCheck(game.board.getKingLocation(game.board.getTurn()),game.board.getBoard())) {
                    game.board.flip();
                    BoardPrinter.print(game.board.getBoard());
                    System.out.println(game.board.getTurn()+" WINS!!!!!");
                } else {
                    System.out.println("STALEMATE!!!!");
                }
                return;
            }
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
            BoardPrinter.print(vspl,new HashSet<Location>(),game.board.getBoard());
            
            //pick starting piece
            Piece sp = game.pickStartingPiece(game.nextPlayer,vspl);
            if (sp == null) {
                continue;
            }
            
            //Find valid moves
            Set<Location> vm = sp.validMoves(game.board);
            
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
            BoardPrinter.print(new HashSet<Location>(){{add(sp.loc);}},vm,game.board.getBoard());
        
            //Make move
            if (!game.makeMove(game.nextPlayer,sp,vm)) {
                continue;
            }
            
            
            //Switch players
            game.nextPlayer = game.nextPlayer==game.whitePlayer?game.blackPlayer:game.whitePlayer;
            
        }
        
        //Implement stalemeate detection
        
    }
}
