package antgame.gui.screen;

import antgame.core.Colony;
import antgame.core.Match;
import antgame.core.world.Cell;
import antgame.core.world.World;
import antgame.gui.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.AuthProvider;

/**
 * The view which shows an in-progress match.
 *
 * @author Sam Marsh
 */
public class MatchView extends View {

    //the colors used to draw various cell types
    private static final Color COLOR_ROCKY_CELL = new Color(100, 57, 0);
    private static final Color COLOR_RED_ANTHILL_CELL = new Color(195, 80, 63);
    private static final Color COLOR_BLACK_ANTHILL_CELL = new Color(88, 88, 88);
    private static final Color COLOR_FOOD_CELL = Color.GREEN.darker().darker();

    //the path to the image used as a background texture of the world
    private static final String TEXTURE_RESOURCE_PATH = "/texture.jpg";

    //how many times to update the screen per second
    private static final int FRAMES_PER_SECOND = 30;

    /**
     * Creates a new match view and initialises the components.
     *
     * @param context the main frame
     * @param match the match to view
     */
    public MatchView(GUI context, Match match) {
        super(context);

        //fill the centre and expand to fill the whole panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //add the main panel
        add(new WorldPanel(match), BorderLayout.CENTER);
    }

    /**
     * A world panel displays the state of a world.
     */
    private class WorldPanel extends JPanel {

        //the world to view
        private World world;

        //used to draw the background
        private Paint texture;

        /**
         * Creates a new world panel to visualise a world.
         *
         * @param match the match that contains the world
         */
        private WorldPanel(Match match) {
            this.world = match.world();

            //create a nice border to embed the world in
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            //load the texture
            try {
                BufferedImage img = ImageIO.read(MatchView.class.getResource(TEXTURE_RESOURCE_PATH));
                texture = new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
            } catch (IOException ignore) {}

            //execute in parallel
            new Thread(() -> {
                while (!match.finished()) {
                    try {
                        //sleep so that we have 30fps
                        Thread.sleep(1000 / FRAMES_PER_SECOND);
                        //switch back to GUI thread and refresh
                        SwingUtilities.invokeLater(() -> {
                            revalidate();
                            repaint();
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        /**
         * Runs when the panel is refreshed.
         *
         * @param g the graphics object
         */
        @Override
        public void paintComponent(Graphics g) {
            //convert to graphics2d for additional functionality
            Graphics2D gfx = (Graphics2D) g;

            //wipe everything
            double panelWidth = getWidth();
            double panelHeight = getHeight();
            gfx.clearRect(0, 0, (int) panelWidth, (int) panelHeight);

            //number of cells
            int worldWidth = world.width();
            int worldHeight = world.height();

            double cellSize;

            {
                //the ratio between the width and height (with a little padding) is the cell width in pixels
                double cellWidth = panelWidth / worldWidth * 0.95;
                double cellHeight = panelHeight / worldHeight * 0.95;
                //use a 1-1 ratio for the cell 'squares'
                cellSize = Math.min(cellWidth, cellHeight);
            }

            //calculate the offsets based on the difference between the panel size and the world size
            int yOffset = (int) (Math.abs(panelHeight - (cellSize * worldHeight)) / 2);
            int xOffset = (int) (Math.abs(panelWidth - (cellSize * worldWidth)) / 2);

            //fill in the background
            gfx.setPaint(texture);
            gfx.fillRect(
                    xOffset + (int) cellSize / 2, yOffset,
                    (int) (cellSize * worldWidth), (int) (cellSize * worldHeight)
            );

            //loop through every cell
            for (int y = 0; y < worldHeight; ++y) {
                //if the y-index is odd, indent x by a bit more
                double newOffset = xOffset + (y % 2 == 1 ? cellSize : 0);

                for (int x = 0; x < worldWidth; ++x) {
                    //the colour to fill this cell with
                    Color cellColour = null;

                    Cell cell = world.cell(x, y);
                    switch (cell.getType()) {
                        case ROCK:
                            cellColour = COLOR_ROCKY_CELL;
                            break;
                        case ANTHILL_BLACK:
                            cellColour = COLOR_BLACK_ANTHILL_CELL;
                            break;
                        case ANTHILL_RED:
                            cellColour = COLOR_RED_ANTHILL_CELL;
                            break;
                    }

                    //blend together the two colours if has food as well
                    if (cell.hasFood()) {
                        cellColour = blend(cellColour, COLOR_FOOD_CELL, 0.5f);
                    }

                    //if has ant, override the colour
                    if (cell.hasAnt()) {
                        if (cell.getAnt().getColony().getColour() == Colony.Colour.RED) {
                            cellColour = Color.RED;
                        } else {
                            cellColour = Color.BLACK;
                        }
                    }

                    //if the cell is empty, ignore
                    if (cellColour == null)
                        continue;

                    //set the fill colour to the above
                    gfx.setColor(cellColour);

                    //fill in the cell
                    gfx.fillOval(
                            (int) (x * cellSize + newOffset), (int) (y * cellSize + yOffset),
                            (int) cellSize, (int) cellSize
                    );
                }
            }
        }

    }

    /**
     * Blends two colours together in a nice way.
     * Originally from: http://stackoverflow.com/questions/19398238/how-to-mix-two-int-colors-correctly
     * Slightly modified.
     *
     * @param c1 the first colour
     * @param c2 the second colour
     * @param ratio how much of the first colour to use from 0-1
     * @return a blend of the two colours
     */
    private Color blend(Color c1, Color c2, float ratio ) {
        if (c1 == null) return c2;
        if (c2 == null) return c1;

        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;

        int i1 = c1.getRGB();
        int i2 = c2.getRGB();

        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);

        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);

        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));

        return new Color( a << 24 | r << 16 | g << 8 | b );
    }

}
