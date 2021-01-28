package minichess.pieces;

import minichess.board.Board;

public class Leader extends Piece {

    public Leader(int rowPosition, int columnPosition, boolean isFigureWhite, String filePath, Board board, boolean canFigureKill) {

        super(columnPosition, rowPosition, isFigureWhite, filePath, board, canFigureKill);
    }

    /**
     * Method that checks if the figure "Rook" can be moved to player's chosen coordinates
     * @param destinationX The Column where the players wants to place its Rook
     * @param destinationY The Row where the players wants to place its Rook
     * @return if the figure can be moved or not
     */
    @Override
    public boolean canFigureBeMoved(int destinationX, int destinationY) {

        Piece isTherePiece = board.getPiece(destinationX, destinationY);

        if (isTherePiece != null) {

            if(areYouPlacingOnTurtle(destinationX, destinationY, isTherePiece)) {

                   return isThereTurtleFIgure(destinationX, destinationY, isTherePiece);
            }

           if(!isThereYourFigure(isTherePiece)) {

               return false;
           }

        }if (!isRookMovingCorrectly(destinationX, destinationY)) {

            return false;

        }
        return !canRookMoveThere(destinationX, destinationY);
    }

    private boolean isThereTurtleFIgure(int destinationX, int destinationY, Piece isTherePiece) {

        if(areYouPlacingOnTurtle(destinationX, destinationY, isTherePiece)) {

            if(canRookMoveThere(destinationX, destinationY)) {

                return true;
            }
        }
        return true;
    }

    private boolean areYouPlacingOnTurtle (int destinationX, int destinationY, Piece isTherePiece) {

       return isTherePiece.isFigureTurtle() && isRookMovingCorrectly(destinationX, destinationY);
    }

    /**
     * Method that checks if the rook is not moving against the rules
     * @param destinationX The Column where the players wants to place its Rook
     * @param destinationY The Row where the players wants to place its Rook
     * @return if the Rook is moved correctly
     */
    private boolean isRookMovingCorrectly(int destinationX, int destinationY) {

        return this.getColumnPosition() == destinationX || this.getRowPosition() == destinationY;
    }

    /**
     *  Method that was created to check if can kill own figures, then changed so cant kill any figures
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
     * Method that calls methods to check if the room can be moved there
     * @param destinationX Column that the user wants to move its rook
     * @param destinationY row that the user wants to move its rook
     * @return boolean to check if the rook can be moved
     */
    private boolean canRookMoveThere(int destinationX, int destinationY) {

        boolean canRookBeMovedThere = true;

        String direction = rookDirectionAnalyzer(destinationX, destinationY);

        switch (direction) {

            case "south" -> canRookBeMovedThere = isRookGoingSouth(destinationY);

            case "north" -> canRookBeMovedThere = isRookGoingNorth(destinationY);

            case "west" -> canRookBeMovedThere  = isRookGoingWest(destinationX);

            case "east" -> canRookBeMovedThere  = isRookGoingEast(destinationX);
        }

        return !canRookBeMovedThere;
    }

    /**
     * Method that analyses to which direction rook is being moved
     * @param destinationX Column that the user wants to move its rook
     * @param destinationY row that the user wants to move its rook
     * @return String that tells to which direction rook is being moved
     */
    private String rookDirectionAnalyzer(int destinationX, int destinationY) {

        String direction = "";

        if (destinationY > this.getRowPosition()) {

            direction = "south";

        } else if (destinationY < this.getRowPosition()) {

            direction = "north";

        } else if (destinationX > getColumnPosition()) {

            direction = "east";

        } else if (destinationX < getColumnPosition()) {

            direction = "west";
        }

        return direction;
    }

    /**
     * Method that checks if the rook is going South
     * @param destinationY row that the user wants to move its rook
     * @return boolean to check if the rook can go South
     */
    private Boolean isRookGoingSouth(int destinationY) {

        boolean canRookGoSouth = false;

        final byte offSetForBoxCounter = 2;

        byte boxCounter;

            for (boxCounter = 1; boxCounter <= 5; boxCounter++) {

                Piece p = board.getPiece(this.getColumnPosition(), this.getRowPosition() + boxCounter);

                if (p != null) {

                    canRookGoSouth = p.getRowPosition() - 1 == destinationY;
                    break;
                }
            }

            boxCounter -= offSetForBoxCounter;

            if(boxCounter == destinationY && boxCounter == 4) {

                return true;
            }
        return canRookGoSouth;
    }

    /**
     * Method that checks if the rook is going North
     * @param destinationY row that the user wants to move its rook
     * @return boolean to check if the rook can go North
     */
    private Boolean isRookGoingNorth(int destinationY) {

        boolean canRookGoNorth = false;

        final byte offSetForBoxCounter = 6;

        byte boxCounter;

            for (boxCounter = 1; boxCounter <= 5; boxCounter++) {

                Piece p = board.getPiece(this.getColumnPosition(), this.getRowPosition() - boxCounter);

                if (p != null) {

                    canRookGoNorth = p.getRowPosition() + 1 == destinationY;
                    break;
                }
            }
        boxCounter -= offSetForBoxCounter;

        if(boxCounter == destinationY && boxCounter == 0) {

            return true;
        }

        return canRookGoNorth;
    }

    /**
     * Method that checks if the rook is going West
     * @param destinationX Column that the user wants to move its rook
     * @return boolean to check if the rook can go West
     */
    private Boolean isRookGoingWest(int destinationX) {

        boolean canRookGoWest = false;

        final byte offSetForBoxCounter = 1;

        byte boxCounter;

        for (boxCounter = 1; boxCounter <= 5; boxCounter++) {

            Piece p = board.getPiece(this.getColumnPosition() - boxCounter, this.getRowPosition());

            if (p != null) {

                canRookGoWest = p.getColumnPosition() + 1 == destinationX;
                break;
            }
        }

        boxCounter -= offSetForBoxCounter;

        if(boxCounter == 5 && destinationX == 0) {

            return true;
        }

        return canRookGoWest;
    }

    /**
     * Method that checks if the rook is going East and checks last position
     * @param destinationX Column that the user wants to move its rook
     * @return boolean to check if the rook can go East
     */
    private Boolean isRookGoingEast(int destinationX) {

        boolean canRookGoEast = false;

        final byte offSetForBoxCounter = 2;

        byte boxCounter;

        for (boxCounter = 1; boxCounter <= 5; boxCounter++) {

            Piece p = board.getPiece(this.getColumnPosition() + boxCounter, this.getRowPosition());

            if (p != null) {

                canRookGoEast = p.getColumnPosition() - 1 == destinationX;
                break;
            }
        }

        boxCounter -= offSetForBoxCounter;

        if(boxCounter == destinationX && boxCounter == 4) {

            return true;
        }

        return canRookGoEast;
    }
}