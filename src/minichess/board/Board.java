package minichess.board;

import minichess.pieces.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class Board extends JComponent {

    private static final Image nullImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    private final int SQUARE_WIDTH        = 65;
    private final int CHESS_BOARD_ROWS    = 5;
    private final int CHESS_BOARD_COLUMNS = 5;
    private final byte PICTURE_ALIGN      = 15;

    public int turnCounter = 0;

    public ArrayList<Piece> whitePieces;
    public ArrayList<Piece> blackPieces;
    public ArrayList<Piece> turtlePieces;
    public ArrayList<DrawingShape> staticShapes;
    public ArrayList<DrawingShape> pieceGraphics;

    public Piece activePiece;

    private final String boardFilePath        = "images" + File.separator + "board.png";
    private final String activeSquareFilePath = "images" + File.separator + "active_square.png";

    public Board() {

        staticShapes  = new ArrayList();
        pieceGraphics = new ArrayList();
        whitePieces   = new ArrayList();
        blackPieces   = new ArrayList();
        turtlePieces  = new ArrayList();

        pieceCoordinates();

        this.setBackground(new Color(37, 13, 84));
        this.setPreferredSize(new Dimension(325, 325));

        MouseAdapter mouseAdapter = new MouseAdapter() {

            /**
             * Method that gets the mouse clicks and does moves
             *
             * @param e Mouse event
             */
            @Override
            public void mousePressed(MouseEvent e) {

                int dX = e.getX();
                int dY = e.getY();

                int clickedRow = dY / SQUARE_WIDTH;
                int clickedColumn = dX / SQUARE_WIDTH;

                boolean isWhiteTurn;

                isWhiteTurn = isWhiteTurn(turnCounter);

                Piece clickedPiece = getPiece(clickedColumn, clickedRow);

                if (isFigureChosenCorrectlyAndPlace(isWhiteTurn, clickedPiece)) { activePiece = clickedPiece; }

                else if (isClickedOnSamePlace(clickedColumn, clickedRow)) { activePiece = null;}

                else if (isEverythingOkayToMove(clickedColumn, clickedRow, isWhiteTurn)) {

                    isClickedPieceNotNull(clickedPiece, activePiece);

                    doTheFigureMoves(clickedColumn, clickedRow);

                    ifGuardAndHasMove();

                    isThereAWinner(clickedColumn, clickedRow, activePiece, isWhiteTurn);

                    activePiece = null;
                    turnCounter++;
                }
                drawBoard();
            }

            /**
             * Method that checks if it the game has ended
             * @param clickedColumn Clicked Column
             * @param clickedRow Clicked Row
             * @param activePiece The piece that is chosen to move
             * @param isWhiteTurn Which player's turn it is
             */
            private void isThereAWinner(int clickedColumn, int clickedRow, Piece activePiece, boolean isWhiteTurn) {

                if(activePiece.getClass().getSimpleName().equalsIgnoreCase("Leader")) {

                    if(clickedColumn == 2 && clickedRow == 2) {

                        displayWinner(isWhiteTurn);
                    }
                }
            }

            /**
             * Method that displays on screen who is the winner
             * @param isWhiteTurn Which player's turn it is
             */
            private void displayWinner(boolean isWhiteTurn) {

                if(isWhiteTurn) {

                    showMessageDialog(null, "White Player is the WINNER.");
                }
                else {
                    showMessageDialog(null, "Black Player is the WINNER.");
                }
                System.exit(0);
            }

            /**
             * Method that tells which player's turn is
             *
             * @param turnCounter Counter for turns
             * @return If it is white player's turn
             */
            private boolean isWhiteTurn(int turnCounter) {

                return turnCounter % 2 != 1;
            }

            /**
             * Method that is called if the guard has moved
             */
            private void ifGuardAndHasMove() {

                if (activePiece.getClass().equals(Guard.class)) {

                    Guard castedPawn = (Guard) (activePiece);

                    castedPawn.setHasMoved(true);
                }
            }

            /**
             * Method that does the movement if everything is correct
             *
             * @param clickedColumn Clicked column
             * @param clickedRow    Clicked row
             */
            private void doTheFigureMoves(int clickedColumn, int clickedRow) {

                activePiece.setColumnPosition(clickedColumn);
                activePiece.setRowPosition(clickedRow);
            }

            /**
             * Method to check if the clicked piece is not null
             *
             * @param clickedPiece The clicked Piece
             */
            private void isClickedPieceNotNull(Piece clickedPiece, Piece activePiece) {

                if (clickedPiece != null) {

                    if (clickedPiece.isFigureTurtle()) {

                        if (activePiece.isFigureWhite()) {

                            whitePieces.remove(activePiece);

                        } else if (activePiece.isFigureBlack()) {

                            blackPieces.remove(activePiece);
                        }

                        turtlePieces.remove(clickedPiece);
                    }
                }
            }

            /**
             * Method that checks if everything is correct and figure can be moved
             *
             * @param clickedColumn Clicked Column
             * @param clickedRow    Clicked Row
             * @param isWhiteTurn   Which player's turn it is
             * @return If everything is correct
             */
            private boolean isEverythingOkayToMove(int clickedColumn, int clickedRow, boolean isWhiteTurn) {

                return activePiece != null && activePiece.canFigureBeMoved(clickedColumn, clickedRow) && ((isWhiteTurn && activePiece.isFigureWhite()) ||
                        (!isWhiteTurn && activePiece.isFigureBlack()));
            }

            /**
             * Method that checks if the player has clicked on the same place
             *
             * @param clickedColumn Clicked column
             * @param clickedRow    Clicked Row
             * @return If the player did click on a same place
             */
            private boolean isClickedOnSamePlace(int clickedColumn, int clickedRow) {

                boolean isActivePiece = activePiece != null;

                return isActivePiece && (activePiece.getColumnPosition() == clickedColumn) && (activePiece.getRowPosition() == clickedRow);
            }

            /**
             * Method that checks if the figure can be moved and if Piece is chosen correctly and the place to move
             *
             * @param isWhiteTurn  Which player's turn it is
             * @param clickedPiece The Piece that is clicked on
             * @return If Piece can be moved
             */
            private boolean isFigureChosenCorrectlyAndPlace(boolean isWhiteTurn, Piece clickedPiece) {

                boolean isClickedCorrectly = activePiece == null && clickedPiece != null;

                return isClickedCorrectly && ((isWhiteTurn && clickedPiece.isFigureWhite()) ||
                        (!isWhiteTurn && clickedPiece.isFigureBlack()));
            }
        };

        this.addMouseListener(mouseAdapter);

        ComponentAdapter componentAdapter = new ComponentAdapter() {

        };

        this.addComponentListener(componentAdapter);
        KeyAdapter keyAdapter = new KeyAdapter() {

        };
        this.addKeyListener(keyAdapter);

        this.setVisible(true);
        this.requestFocus();

        drawBoard();
    }

    /**
     * Method to set coordinates of the pieces
     */
    public void pieceCoordinates() {

        int randomColumnForTurtleOne = randomColumnPickerForTurtle();
        int randomColumnForTurtleTwo = randomColumnPickerForTurtle();

        randomColumnForTurtleOne = areTurtlesOnSameColumn(randomColumnForTurtleOne, randomColumnForTurtleTwo);

        //White Guard and pawn Leader
        whitePieces.add(new Guard (0, 0, true, "Pawn.png", this,false));
        whitePieces.add(new Guard (0, 1, true, "Pawn.png", this,false));
        whitePieces.add(new Guard (0, 2, true, "Pawn.png", this,false));
        whitePieces.add(new Guard (0, 3, true, "Pawn.png", this,false));
        whitePieces.add(new Leader(4, 0, true, "Rook.png", this,false));

        //Black Guard and Leader pieces
        blackPieces.add(new Leader(0, 4, false, "Rook.png", this,false));
        blackPieces.add(new Guard (4, 1, false, "Pawn.png", this,false));
        blackPieces.add(new Guard (4, 2, false, "Pawn.png", this,false));
        blackPieces.add(new Guard (4, 3, false, "Pawn.png", this,false));
        blackPieces.add(new Guard (4, 4, false, "Pawn.png", this,false));

        //Turtle pieces
        turtlePieces.add(new Turtle(2, randomColumnForTurtleOne, false, "Turtle.png", this, true));
        turtlePieces.add(new Turtle(2, randomColumnForTurtleTwo, false, "Turtle.png", this, true));
    }


    /**
     * Method that makes sure that turtle column positions are not same
     * @param randomColumnForTurtleOne Column position of turtle one
     * @param randomColumnForTurtleTwo Column position of turtle two
     * @return New or old column position for turtle One
     */
    private int areTurtlesOnSameColumn(int randomColumnForTurtleOne, int randomColumnForTurtleTwo) {

        if(randomColumnForTurtleOne == randomColumnForTurtleTwo) {

            randomColumnForTurtleOne = randomColumnPickerForTurtle();

            while(randomColumnForTurtleOne == randomColumnForTurtleTwo) {

                randomColumnForTurtleOne = randomColumnPickerForTurtle();
            }
        }
        return randomColumnForTurtleOne;
    }

    /**
     * Method that rolls a random number for Turtles
     * @return The number used for their column
     */
    public int randomColumnPickerForTurtle() {

        int randomNumber;

        Random random = new Random();

        randomNumber = random.nextInt(5);

        while(randomNumber == 2) {

            randomNumber = random.nextInt(5);
        }

        return randomNumber;
    }

    /**
     * Method that draws the entire board
     */
    private void drawBoard() {

        pieceGraphics.clear();
        staticShapes.clear();

        boardImageLoader();

        activeSquareImageLoader();

        whitePieceImageLoader();

        blackPieceImageLoader();

        turtleImageLoader();

        this.repaint();
    }

    /**
     * Method that loads the picture that is applied to a square when it is clicked
     */
    private void activeSquareImageLoader() {

        if (activePiece != null) {

            Image activeSquare = loadImage("images" + File.separator + "active_square.png");

            staticShapes.add(new DrawingImage(activeSquare, new Rectangle2D.Double(SQUARE_WIDTH * activePiece.getColumnPosition(), SQUARE_WIDTH * activePiece.getRowPosition(), activeSquare.getWidth(null), activeSquare.getHeight(null))));
        }
    }

    /**
     * Method that loads the image that is used for the board
     */
    private void boardImageLoader() {

        Image board = loadImage(boardFilePath);

        staticShapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
    }

    /**
     * Method that loads the images used to show White pieces
     */
    private void whitePieceImageLoader() {

        int column;
        int row;

        for (Piece whitePiece : whitePieces) {

            column = whitePiece.getColumnPosition();
            row = whitePiece.getRowPosition();

            Image piece = loadImage("images" + File.separator + "white_pieces" + File.separator + whitePiece.getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * column + PICTURE_ALIGN, SQUARE_WIDTH * row + PICTURE_ALIGN, piece.getWidth(null), piece.getHeight(null))));
        }
    }

    /**
     * Method that loads the images used to show Black pieces
     */
    private void blackPieceImageLoader() {

        int column;
        int row;

        for (Piece blackPiece : blackPieces) {

            column = blackPiece.getColumnPosition();
            row = blackPiece.getRowPosition();

            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + blackPiece.getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * column + PICTURE_ALIGN, SQUARE_WIDTH * row + PICTURE_ALIGN, piece.getWidth(null), piece.getHeight(null))));
        }
    }

    /**
     * Method that loads the images used to show Turtle pieces
     */
    private void turtleImageLoader() {

        int column;
        int row;

        for (Piece turtlePiece : turtlePieces) {

            column = turtlePiece.getColumnPosition();
            row = turtlePiece.getRowPosition();

            Image piece = loadImage("images" + File.separator + "turtle_piece" + File.separator + turtlePiece.getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * column + PICTURE_ALIGN, SQUARE_WIDTH * row + PICTURE_ALIGN, piece.getWidth(null), piece.getHeight(null))));
        }
    }

    /**
     * Method that gets the White, Black and Turtle pieces of the game
     *
     * @param column Column of the piece
     * @param row    Row of the piece
     * @return Object of the class Piece
     */
    public Piece getPiece(int column, int row) {


        for (Piece p : whitePieces) {

            if (p.getColumnPosition() == column && p.getRowPosition() == row) {

                return p;
            }
        }

        for (Piece p : blackPieces) {

            if (p.getColumnPosition() == column && p.getRowPosition() == row) {

                return p;
            }
        }

        for (Piece p : turtlePieces) {

            if (p.getColumnPosition() == column && p.getRowPosition() == row) {

                return p;
            }
        }

        return null;
    }

    /**
     * Method that loads the images
     *
     * @param imageFile The Image file
     * @return null image
     */
    private Image loadImage(String imageFile) {

        try {

            return ImageIO.read(new File(imageFile));

        } catch (IOException e) {

            return nullImage;
        }
    }

    /**
     * Method that paints the components of the game
     *
     * @param g Object of the super class for all the graphics contexts
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawShapes(g2);
    }

    /**
     * Method that draws the background of the game board
     *
     * @param g2 Object of the super class for all the graphics contexts
     */
    private void drawBackground(Graphics2D g2) {

        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
    }


    /**
     * Method that draws the shapes
     *
     * @param g2 Object of the super class for all the graphics contexts
     */
    private void drawShapes(Graphics2D g2) {

        for (DrawingShape shape : staticShapes) {

            shape.draw(g2);
        }

        for (DrawingShape shape : pieceGraphics) {

            shape.draw(g2);
        }
    }

}