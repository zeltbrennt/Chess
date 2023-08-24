package chess.ui;

import chess.game.Piece;
import chess.game.Player;
import chess.game.Square;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PieceSprite extends Text {

    public static int scaling;
    private final Piece piece;
    private final Player player;
    private Square square;

    public PieceSprite(Piece piece, Player player, Square square) {
        super();
        this.piece = piece;
        this.player = player;
        this.setSquare(square);
        int symbol = switch (piece) {
            case EMPTY -> 0;
            case PAWN -> 0x2659;
            case KNIGHT -> 0x2658;
            case BISHOP -> 0x2657;
            case ROOK -> 0x2656;
            case QUEEN -> 0x2655;
            case KING -> 0x2654;
        };
        symbol += player == Player.BLACK ? 6 : 0;
        this.setText(String.format("%c", symbol));
        int idx = square.getValue();
        int y = (7 - idx / 8) * scaling;
        int x = idx % 8 * scaling;
    }

    public static void setScaling(int scaling) {
        PieceSprite.scaling = scaling;
    }

    public Piece getPiece() {
        return piece;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
        this.setFont(new Font(80));
        setX(square.getValue() % 8 * scaling + scaling * 0.1);
        setY((8 - square.getValue() / 8) * scaling - scaling * 0.2);
    }

    public void attachToCurser(double x, double y) {
        this.setFont(new Font(90));
        setX(x - scaling * 0.4);
        setY(y + scaling * 0.2);
    }
}
