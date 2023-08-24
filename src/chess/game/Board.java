package chess.game;

import java.util.Arrays;
import java.util.StringJoiner;

public class Board {

    private Piece[] pieces;
    private Player[] players;
    private long bbBlack;
    private long bbWhite;
    private long bbPawns;
    private long bbRooks;
    private long bbKnights;
    private long bbBishops;
    private long bbKings;
    private long bbQueens;

    public Board() {
        clearBoard();
    }

    public void clearBoard() {
        pieces = new Piece[64];
        players = new Player[64];
        Arrays.fill(players, Player.EMPTY);
        Arrays.fill(pieces, Piece.EMPTY);
        bbBlack = 0L;
        bbWhite = 0L;
        bbPawns = 0L;
        bbKnights = 0L;
        bbBishops = 0L;
        bbRooks = 0L;
        bbQueens = 0L;
        bbKings = 0L;
    }

    public void setupNewGame() {
        pieces = new Piece[]{
                Piece.ROOK, Piece.KNIGHT, Piece.BISHOP, Piece.QUEEN, Piece.KING, Piece.BISHOP, Piece.KNIGHT, Piece.ROOK,
                Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN,
                Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
                Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
                Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
                Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY,
                Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN,
                Piece.ROOK, Piece.KNIGHT, Piece.BISHOP, Piece.QUEEN, Piece.KING, Piece.BISHOP, Piece.KNIGHT, Piece.ROOK
        };
        players = new Player[]{
                Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE,
                Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE, Player.WHITE,
                Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY,
                Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY,
                Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY,
                Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY, Player.EMPTY,
                Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK,
                Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK, Player.BLACK
        };
        bbWhite = 0x000000000000ffffL;
        bbBlack = 0xffff000000000000L;
        bbPawns = 0x00ff00000000ff00L;
        bbKnights = 0x4200000000000042L;
        bbBishops = 0x2400000000000024L;
        bbRooks = 0x8100000000000081L;
        bbQueens = 0x0800000000000008L;
        bbKings = 0x1000000000000010L;
    }

    public void addSinglePieceToBoard(Piece piece, Player player, Square square) {
        int idx = square.getValue();
        pieces[idx] = piece;
        players[idx] = player;
        long insert = 1L << idx;
        if (player == Player.WHITE) {
            bbWhite |= insert;
        } else {
            bbBlack |= insert;
        }
        switch (piece) {
            case PAWN -> bbPawns |= insert;
            case KNIGHT -> bbKnights |= insert;
            case BISHOP -> bbBishops |= insert;
            case ROOK -> bbRooks |= insert;
            case QUEEN -> bbQueens |= insert;
            case KING -> bbKings |= insert;
        }
    }

    public void makeMove(Square source, Square target) {
        int idx = source.getValue();
        System.out.printf("\n\n%s %s: %s-%s\n", players[idx], pieces[idx], source, target);
        addSinglePieceToBoard(pieces[idx], players[idx], target);
        removeSinglePieceFromBoard(source);
    }

    public void removeSinglePieceFromBoard(Square square) {
        int idx = square.getValue();
        Piece piece = pieces[idx];
        Player player = players[idx];
        pieces[idx] = Piece.EMPTY;
        players[idx] = Player.EMPTY;
        long remove = 1L << idx;
        if (player == Player.WHITE) {
            bbWhite ^= remove;
        } else {
            bbBlack ^= remove;
        }
        switch (piece) {
            case PAWN -> bbPawns ^= remove;
            case KNIGHT -> bbKnights ^= remove;
            case BISHOP -> bbBishops ^= remove;
            case ROOK -> bbRooks ^= remove;
            case QUEEN -> bbQueens ^= remove;
            case KING -> bbKings ^= remove;
        }
    }

    public Piece[] getBoardByPieces() {
        return pieces;
    }

    public Player[] getBoardByPlayers() {
        return players;
    }

    public long getBbBlack() {
        return bbBlack;
    }

    public long getBbWhite() {
        return bbWhite;
    }

    public long getBbPawns() {
        return bbPawns;
    }

    public long getBbRooks() {
        return bbRooks;
    }

    public long getBbKnights() {
        return bbKnights;
    }

    public long getBbBishops() {
        return bbBishops;
    }

    public long getBbKings() {
        return bbKings;
    }

    public long getBbQueens() {
        return bbQueens;
    }

    @Override
    public String toString() {
        StringJoiner repr = new StringJoiner(" ");
        for (int rank = 7; rank >= 0; rank--) {
            repr.add(String.format("\n%d |", rank + 1));
            for (int file = 0; file < 8; file++) {
                String p = switch (pieces[rank * 8 + file]) {
                    case PAWN -> "P";
                    case KNIGHT -> "N";
                    case BISHOP -> "B";
                    case ROOK -> "R";
                    case QUEEN -> "Q";
                    case KING -> "K";
                    case EMPTY -> ".";
                };
                p = players[rank * 8 + file] == Player.WHITE ? p : p.toLowerCase();
                repr.add(p);
            }
        }
        repr.add("\n  -----------------\n    a b c d e f g h");
        return repr.toString();
    }
}
