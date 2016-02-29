package server;

import java.io.*;
import java.util.*;

public class Game
{
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player nextPlayer;
    
    public Game(Player p1, Player p2) {
        board = new Board(Team.WHITE);
        whitePlayer = p1;
        blackPlayer = p2;
        nextPlayer = whitePlayer;
    }
    
    private Piece pickStartingPiece(Player p, Set<Location> validStartingPieces) {
        String sendThis = BoardPrinter.print(validStartingPieces,new HashSet<Location>(),board.getBoard());
        sendThis += board.getTurn()+" starting piece:\n:";
        Location s = p.getMove(sendThis);
        return validStartingPieces.contains(s)?board.getPiece(s):null;
    }
    
    private boolean makeMove(Player p, Piece sp, Set<Location> validMoves) {

        String sendThis = BoardPrinter.print(new HashSet<Location>(){{add(sp.loc);}},validMoves,board.getBoard());
        sendThis += "Move to:\n:";
        Location e = p.getMove(sendThis);
        
        if (!validMoves.contains(e)) {
            return false;
        }
        
        //Check for pawn promotion
        if (sp.type==PieceType.PAWN && e.y==7) {
            
            sendThis = BoardPrinter.print(new HashSet<Location>(),new HashSet<Location>(){{add(e);}},board.getBoard());
            sendThis += "Pawn promoted to (Rook, Knight, Bishop, Queen):\n:";
            PieceType pt = p.pawnPromotion(sendThis);
            
            if (pt == null) {
                return false;
            }
            
            board.removePiece(sp);
            board.createPiece(pt,board.getTurn(),e);
            
        } else {
            sp.moveTo(e,board);
        }
        
        sendThis = BoardPrinter.print(board.getBoard());
        sendThis += "Waiting for the other player to make a move\n";
        p.send(sendThis);
        
        board.flip();
        return true;
    }

    public void startGame() throws IOException {
        
        while (true) {
            
            //Find valid starting pieces
            Set<Location> vspl = board.getPiecesLocations(board.getTurn());
            Iterator<Location> iter = vspl.iterator();
            while (iter.hasNext()) {
                Location l = iter.next();
                if (board.getPiece(l).validMoves(board).isEmpty()) {
                    iter.remove();
                }
            }
            
            if (vspl.isEmpty()) {
                if (Utils.isCheck(board.getKingLocation(board.getTurn()),board.getBoard())) {
                    
                    String sendThis = BoardPrinter.print(board.getBoard());
                    sendThis += "YOU LOSE!!!!!\n";
                    nextPlayer.endGame(sendThis);
                    
                    nextPlayer = nextPlayer==whitePlayer?blackPlayer:whitePlayer;
                    board.flip();
                    
                    sendThis = BoardPrinter.print(board.getBoard());
                    sendThis += "YOU WIN!!!!!\n";
                    nextPlayer.endGame(sendThis);
                    
                } else {
                    
                    String sendThis = BoardPrinter.print(board.getBoard());
                    sendThis += "STALEMATE!!!!";
                    nextPlayer.endGame(sendThis);
                    
                    nextPlayer = nextPlayer==whitePlayer?blackPlayer:whitePlayer;
                    board.flip();
                    
                    sendThis = BoardPrinter.print(board.getBoard());
                    sendThis += "STALEMATE!!!!";
                    nextPlayer.endGame(sendThis);
                    
                }
                return;
            }

            //pick starting piece
            Piece sp = pickStartingPiece(nextPlayer,vspl);
            if (sp == null) {
                continue;
            }
            
            //Find valid moves
            Set<Location> vm = sp.validMoves(board);
        
            //Make move
            if (!makeMove(nextPlayer,sp,vm)) {
                continue;
            }
            
            //Switch players
            nextPlayer = nextPlayer==whitePlayer?blackPlayer:whitePlayer;
            
        }
        
        //Implement stalemeate detection
        
    }
}