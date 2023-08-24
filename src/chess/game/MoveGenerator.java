package chess.game;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MoveGenerator {

    private final Board board;
    private Square square;
    private Piece piece;
    private Player player;
    private long pseudoLegalMoves;

    public MoveGenerator(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        StringJoiner repr = new StringJoiner(" ");
        //System.out.printf("%016x\n", indexedBitBoard);
        for (int rank = 7; rank >= 0; rank--) {
            repr.add(String.format("\n%d |", rank + 1));
            for (int file = 0; file < 8; file++) {
                String p = rank * 8 + file == square.getValue() ? "*" : ".";
                long tempWhy = pseudoLegalMoves & (1L << (rank * 8 + file));
                p = tempWhy != 0L ? "X" : p;
                repr.add(p);
            }
        }
        repr.add("\n  -----------------\n    a b c d e f g h");
        return repr.toString();
    }

    public void calculateAllPseudoLegalMoves() {
        pseudoLegalMoves = 0L;
        long bbEnemeyPieces = player == Player.WHITE ? board.getBbBlack() : board.getBbWhite();
        long bbFriendlyPieces = player == Player.WHITE ? board.getBbWhite() : board.getBbBlack();
        switch (piece) {
            case PAWN -> {
                long piece = (1L << square.getValue()) & board.getBbPawns();
                if (player == Player.WHITE) {
                    pseudoLegalMoves |= piece << 8 & ~bbEnemeyPieces & ~bbFriendlyPieces;
                    pseudoLegalMoves |= piece << 7 & bbEnemeyPieces & ~bbFriendlyPieces;
                    pseudoLegalMoves |= piece << 9 & bbEnemeyPieces & ~bbFriendlyPieces;
                    if (square.getValue() / 8 == 1) { // pawns can go two steps on the first move
                        pseudoLegalMoves |= piece << 16 & ~(bbEnemeyPieces | bbFriendlyPieces);
                    } // TODO: en-passant
                } else {
                    pseudoLegalMoves |= piece >> 8 & ~bbEnemeyPieces & ~bbFriendlyPieces;
                    pseudoLegalMoves |= piece >> 7 & bbEnemeyPieces & ~bbFriendlyPieces;
                    pseudoLegalMoves |= piece >> 9 & bbEnemeyPieces & ~bbFriendlyPieces;
                    if (square.getValue() / 8 == 6) {
                        pseudoLegalMoves |= piece >> 16 & ~(bbEnemeyPieces | bbFriendlyPieces);
                    }
                }
            }
            case KNIGHT -> board.getBbKnights();
            case BISHOP -> board.getBbBishops();
            case ROOK -> board.getBbRooks();
            case QUEEN -> board.getBbQueens();
            case KING -> board.getBbKings();
        }
    }

    private void calcPawnMoves() {
        //TODO: fix mapping bitboards and pieces :(
        /*
        1 shift, wenn feld frei
        2 shift, wenn auf 2/7.rang
         */
    }

    public void focusOnSquare(Square square) {
        this.square = square;
        this.piece = board.getBoardByPieces()[square.getValue()];
        this.player = board.getBoardByPlayers()[square.getValue()];
    }

    public List<Square> getLegalMoves(Square square) {
        List<Square> possibleMoves = new ArrayList<>();
        int idx = square.getValue();
        Player player = board.getBoardByPlayers()[idx];
        Piece piece = board.getBoardByPieces()[idx];
        switch (piece) {
            case PAWN -> {
                if (player == Player.WHITE) {
                    if (board.getBoardByPlayers()[idx + 8] == Player.EMPTY) {
                        possibleMoves.add(Square.valueOf(idx + 8));
                    }
                    if (square.getValue() / 8 == 1 && board.getBoardByPlayers()[idx + 16] == Player.EMPTY) {
                        possibleMoves.add(Square.valueOf(idx + 16));
                    }
                    if (board.getBoardByPlayers()[idx + 7] == Player.BLACK) {
                        possibleMoves.add(Square.valueOf(idx + 7));
                    }
                    if (board.getBoardByPlayers()[idx + 9] == Player.BLACK) {
                        possibleMoves.add(Square.valueOf(idx + 9));
                    }
                } else {
                    if (board.getBoardByPlayers()[idx - 8] == Player.EMPTY) {
                        possibleMoves.add(Square.valueOf(idx - 8));
                    }
                    if (square.getValue() / 8 == 6 && board.getBoardByPlayers()[idx - 16] == Player.EMPTY) {
                        possibleMoves.add(Square.valueOf(idx - 16));
                    }
                    if (board.getBoardByPlayers()[idx - 7] == Player.WHITE) {
                        possibleMoves.add(Square.valueOf(idx + 7));
                    }
                    if (board.getBoardByPlayers()[idx - 9] == Player.WHITE) {
                        possibleMoves.add(Square.valueOf(idx + 9));
                    }
                }
            }
            case KNIGHT -> {
                if (idx + 15 < 64 && board.getBoardByPlayers()[idx + 15] != player) {
                    possibleMoves.add(Square.valueOf(idx + 15));
                }
                if (idx + 6 < 64 && board.getBoardByPlayers()[idx + 6] != player) {
                    possibleMoves.add(Square.valueOf(idx + 6));
                }
                if (idx + 17 < 64 && board.getBoardByPlayers()[idx + 17] != player) {
                    possibleMoves.add(Square.valueOf(idx + 17));
                }
                if (idx + 10 < 64 && board.getBoardByPlayers()[idx + 10] != player) {
                    possibleMoves.add(Square.valueOf(idx + 10));
                }
                if (idx - 6 >= 0 && board.getBoardByPlayers()[idx - 6] != player) {
                    possibleMoves.add(Square.valueOf(idx - 6));
                }
                if (idx - 15 >= 0 && board.getBoardByPlayers()[idx - 15] != player) {
                    possibleMoves.add(Square.valueOf(idx - 15));
                }
                if (idx - 17 >= 0 && board.getBoardByPlayers()[idx - 17] != player) {
                    possibleMoves.add(Square.valueOf(idx - 17));
                }
                if (idx - 10 >= 0 && board.getBoardByPlayers()[idx - 10] != player) {
                    possibleMoves.add(Square.valueOf(idx - 10));
                }
            }
        }
        // TODO implement!
        //return List.of(Square.h8);
        System.out.println(possibleMoves);
        return possibleMoves;
    }
}
