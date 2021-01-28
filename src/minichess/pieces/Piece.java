package minichess.pieces;

import minichess.board.Board;

public class Piece {

    protected int columnPosition;
    protected int rowPosition;
    private String filePath;
    final private boolean isFigureWhite;
    final private boolean isFigureTurtle;
    public Board board;

    /**
     * Constructor
     * @param y Row position
     * @param x Column position
     * @param isFigureWhite To check if the figure is white
     * @param filePath The file path
     * @param board The board
     */
    public Piece(int y, int x, boolean isFigureWhite, String filePath, Board board, boolean canFigureKill) {

        this.isFigureWhite  = isFigureWhite;
        this.columnPosition = x;
        this.rowPosition    = y;
        this.filePath       = filePath;
        this.board          = board;
        this.isFigureTurtle = canFigureKill;
    }

    /**
     * Setter for file path
     * @param path The file path
     */
    public void setFilePath(String path) {
        this.filePath = path;
    }

    /**
     * Getter for file path
     * @return The file path
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Setter for column position
     * @param columnPosition The column where the figure is positioned
     */
    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    /**
     * Method that gives the column position of the figure
     * @return The column position
     */
    public int getColumnPosition() {
        return this.columnPosition;
    }

    /**
     * Setter for figure's row position
     * @param rowPosition figure's row position
     */
    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    /**
     * Method that gets figure's row position
     * @return Figure's row position
     */
    public int getRowPosition() {
        return this.rowPosition;
    }

    /**
     * Method that returns boolean to check if the figure is white
     * @return Boolean that tells that the figure is white
     */
    public boolean isFigureWhite() {
        return this.isFigureWhite;
    }

    /**
     * Method returns boolean to check if the figure is black
     * @return Boolean that tells that the figure is not white
     */
    public boolean isFigureBlack() {
        return !this.isFigureWhite;
    }

    public boolean isFigureTurtle() {
        return this.isFigureTurtle;
    }

    /**
     * Method that checks if figure can be moved
     * @param destinationX column position of the figure
     * @param destinationY row position of the figure
     * @return if the figure can be moved
     */
    public boolean canFigureBeMoved(int destinationX, int destinationY) {

        return false;
    }
}
