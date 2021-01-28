package minichess;

import minichess.board.BoardFrame;

/**
 *
 * @author Erkan Kamber
 *
 */
public class Main {

    public BoardFrame boardFrame;

    public static void main(String[] args) {

        Main chess = new Main();

        chess.boardFrame = new BoardFrame();
        chess.boardFrame.setVisible(true);

    }
}
