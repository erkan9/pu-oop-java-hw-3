package minichess.pieces;

import minichess.board.Board;

public class Turtle extends Piece {

    /**
     * Constructor
     *  @param rowPosition    row position of the figure
     * @param columnPosition column position of the figure
     * @param isFigureWhite  if the figure is white
     * @param filePath       file path
     * @param board          the board
     * @param canFigureKill
     */
    public Turtle(int rowPosition, int columnPosition, boolean isFigureWhite, String filePath, Board board, boolean canFigureKill) {

        super(rowPosition, columnPosition, isFigureWhite, filePath, board, canFigureKill);
    }
}
