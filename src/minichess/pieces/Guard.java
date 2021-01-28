package minichess.pieces;

import minichess.board.Board;

public class Guard extends Piece {

    private boolean hasFigureMoved;


    /**
     * Constructor
     * @param rowPosition row position of the figure
     * @param columnPosition column position of the figure
     * @param isFigureWhite if the figure is white
     * @param filePath file path
     * @param board the board
     */
    public Guard(int rowPosition, int columnPosition, boolean isFigureWhite, String filePath, Board board, boolean canFigureKill) {

        super(rowPosition, columnPosition, isFigureWhite, filePath, board, canFigureKill);
        hasFigureMoved = false;
    }

    /**
     * sets of figure has moved
     * @param hasFigureMoved if the figure is moved
     */
    public void setHasMoved(boolean hasFigureMoved) {

        this.hasFigureMoved = hasFigureMoved;
    }

    /**
     * gets if the figure is moved
     * @return if the figure is moved
     */
    public boolean getHasMoved() {
        return hasFigureMoved;
    }

    /**
     * Method that calls the others methods to check if the figure "Rook" can be moved
     * @param destinationX column position of the figure
     * @param destinationY row position of the figure
     * @return if the figure can be moved
     */
    @Override
    public boolean canFigureBeMoved(int destinationX, int destinationY) {

        Piece isTherePiece = board.getPiece(destinationX, destinationY);

        if (isTherePiece != null) {

            if(isTherePiece.isFigureTurtle() && isPawnCanBeMoved(destinationX, destinationY)) {

                    return true;
            }
            if(!isThereYourFigure(isTherePiece)) {

                return false;
            }
        }
        return isPawnCanBeMoved(destinationX, destinationY);
    }

    /**
     * Method that was created to check if can kill own figures, then changed so cant kill any figures
     * @param isTherePiece Object that contains information about the place where i want to but my figure
     * @return boolean to check if I can put it there
     */
    private boolean isThereYourFigure(Piece isTherePiece) {

        if (isTherePiece.isFigureWhite() && this.isFigureWhite()) {

            return false;
        }
        isTherePiece.isFigureBlack();

        return false;
    }

    /**
     * Method that checks if the pawn can be moved
     * @param destinationX Column that the players want to place its figure
     * @param destinationY Row that the players want to place its figure
     * @return boolean to check figure can be moved
     */
    private boolean isPawnCanBeMoved(int destinationX, int destinationY) {

        return isPawnMovingNorthOrSouth(destinationX, destinationY) || isPawnMovingWestOrEast(destinationX, destinationY);
    }

    /**
     * Method that checks if the Pawn is moving South or North
     * @param destinationX Column that the players want to place its figure
     * @param destinationY Row that the players want to place its figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isPawnMovingNorthOrSouth(int destinationX, int destinationY) {

        boolean didPawnChangeRow    = Math.abs(destinationY - this.getRowPosition()) == 1;
        boolean didPawnChangeColumn =   Math.abs(destinationX - this.getColumnPosition()) == 0;

        return didPawnChangeColumn && didPawnChangeRow;
    }

    /**
     * Method that checks if the Pawn is moving West or East
     * @param destinationX Column that the players want to place its figure
     * @param destinationY Row that the players want to place its figure
     * @return if the figure can go West or East
     */
    private boolean isPawnMovingWestOrEast(int destinationX, int destinationY) {

        boolean didPawnChangeRow    = Math.abs(destinationY - this.getRowPosition()) == 0;
        boolean didPawnChangeColumn =   Math.abs(destinationX - this.getColumnPosition()) == 1;

        return didPawnChangeColumn && didPawnChangeRow;
    }
}
