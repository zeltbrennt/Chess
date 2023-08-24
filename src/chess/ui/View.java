package chess.ui;

import chess.game.Piece;
import chess.game.Player;
import chess.game.Square;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;

public class View {

    private final Pane board;
    private final Pane pieces;
    private final Pane overlay;
    private final int squareSize;
    //private Presenter presenter;
    private Square sourcePosition;
    private Presenter presenter;

    public View(int squareSize) {
        this.squareSize = squareSize;
        board = new Pane();
        pieces = new Pane();
        overlay = new Pane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(j * squareSize, i * squareSize, squareSize, squareSize);
                square.setFill((j + i) % 2 == 0 ? Color.rgb(119, 153, 84) : Color.rgb(233, 237, 204));
                board.getChildren().add(square);
            }
        }
        board.getChildren().add(pieces);
        board.getChildren().add(overlay);
    }

    public Pane getBoard() {
        return board;
    }

    /*
        public void setPresenter(Presenter presenter) {
            this.presenter = presenter;
        }
    */
    public Pane getPieces() {
        return pieces;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    private int getSquareIndexOfMousePosition(MouseEvent e) {
        return (int) e.getX() / squareSize + (int) e.getY() / squareSize * 8;
    }

    private Square getSquareAtMousePosition(MouseEvent e) {
        return Square.valueOfBigEndian((int) e.getX() / squareSize, (int) e.getY() / squareSize);
    }

    public void addPieceToSquare(Square square, Piece piece, Player player) {
        if (piece == Piece.EMPTY) return;
        PieceSprite tempPiece = new PieceSprite(piece, player, square);
        pieces.getChildren().add(tempPiece);
        tempPiece.setOnMousePressed(e -> {
            sourcePosition = getSquareAtMousePosition(e);
            pieces.getChildren().remove(tempPiece);
            pieces.getChildren().add(tempPiece);
            tempPiece.attachToCurser(e.getX(), e.getY());
            presenter.highlightLegalMoves(sourcePosition);
        });
        tempPiece.setOnMouseDragged(e -> tempPiece.attachToCurser(e.getX(), e.getY()));
        tempPiece.setOnMouseReleased(e -> {
            Square targetPosition = getSquareAtMousePosition(e);
            // TODO: fix this mess, together with legal moves
            if (sourcePosition != targetPosition) {
                presenter.updateBoard(sourcePosition, targetPosition);
                tempPiece.setSquare(targetPosition);
                Iterator<Node> iter = pieces.getChildren().iterator();
                while (iter.hasNext()) {
                    PieceSprite capture = (PieceSprite) iter.next();
                    if (capture.getSquare() == targetPosition && capture.getPiece() != tempPiece.getPiece()) {
                        iter.remove();
                        break;
                    }
                }
            } else {
                tempPiece.setSquare(sourcePosition);
            }
            overlay.getChildren().clear();
            /*
            tempPiece.setY(Math.floor(targetPosition / 8.0) * squareSize + squareSize / 2.0);
            tempPiece.setX(targetPosition % 8 * squareSize + squareSize / 2.0);
            */
        });
    }

    public void addMoveToOverlay(Square square) {
        int x = square.getValue() % 8 * squareSize + squareSize / 2;
        int y = (7 - square.getValue() / 8) * squareSize + squareSize / 2;
        Circle hint = new Circle(x, y, squareSize / 4.0);
        hint.setFill(Color.grayRgb(50, 0.5));
        overlay.getChildren().add(hint);
    }
}



