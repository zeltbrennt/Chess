package chess.ui;

import chess.game.Board;
import chess.game.MoveGenerator;
import chess.game.Square;

import java.util.List;

public class Presenter {

    private Board board;
    private MoveGenerator moveGenerator;
    private View view;

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setMoveGenerator(MoveGenerator moveGenerator) {
        this.moveGenerator = moveGenerator;
    }

    public void setUI(View ui) {
        this.view = ui;
    }

    public void addPiecesToUI() {
        for (Square square : Square.values()) {
            view.addPieceToSquare(square,
                    board.getBoardByPieces()[square.getValue()],
                    board.getBoardByPlayers()[square.getValue()]);
        }
    }

    public void updateBoard(Square source, Square target) {
        board.makeMove(source, target);
        System.out.println(board);
    }

    public void highlightLegalMoves(Square square) {
        List<Square> moves = moveGenerator.getLegalMoves(square);
        for (Square m : moves) {
            view.addMoveToOverlay(m);
        }
    }
}
