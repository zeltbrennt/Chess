package chess.ui;

import chess.game.Board;
import chess.game.MoveGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int SQUARE_SIZE = 100;

    public static void main(String[] args) {
        PieceSprite.setScaling(SQUARE_SIZE);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View UI = new View(SQUARE_SIZE);
        Board board = new Board();
        board.setupNewGame();
        MoveGenerator moveGenerator = new MoveGenerator(board);
        Presenter presenter = new Presenter();
        presenter.setBoard(board);
        presenter.setMoveGenerator(moveGenerator);
        presenter.setUI(UI);
        UI.setPresenter(presenter);
        presenter.addPiecesToUI();
        Pane canvas = UI.getBoard();
        Scene scene = new Scene(canvas, SQUARE_SIZE * 8, SQUARE_SIZE * 8);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess");
        primaryStage.show();
    }
}
