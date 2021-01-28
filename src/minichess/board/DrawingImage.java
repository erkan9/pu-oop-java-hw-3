package minichess.board;

import java.awt.*;
import java.awt.geom.Rectangle2D;

class DrawingImage implements DrawingShape {

    public Image image;
    public Rectangle2D rect;

    /**
     * Constructor
     *
     * @param image THe image
     * @param rect  object of Rectangle2D
     */
    public DrawingImage(Image image, Rectangle2D rect) {

        this.image = image;
        this.rect = rect;
    }

    /**
     * Method created for drawing Images
     *
     * @param g2 Object of the super class for all the graphics contexts
     */
    @Override
    public void draw(Graphics2D g2) {

        Rectangle2D bounds = rect.getBounds2D();

        g2.drawImage(image, (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getMaxX(), (int) bounds.getMaxY(),

                0, 0, image.getWidth(null), image.getHeight(null), null);
    }
}
