package antgame.gui.screen;

import antgame.core.Ant;
import antgame.core.Colony;
import antgame.core.Match;
import antgame.core.brain.parser.BrainParser;
import antgame.core.world.Cell;
import antgame.core.world.World;
import antgame.gui.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sam Marsh
 */
public class MatchView extends View {

    private static final Color COLOR_ROCKY_CELL = new Color(100, 57, 0);
    private static final Color COLOR_RED_ANTHILL_CELL = new Color(195, 80, 63);
    private static final Color COLOR_BLACK_ANTHILL_CELL = new Color(88, 88, 88);
    private static final Color COLOR_FOOD_CELL = Color.GREEN.darker().darker();

    public MatchView(GUI context, Match match) {
        super(context);
        setLayout(new BorderLayout());
        try {
            match.world().spawnAnts(new Colony(Colony.Colour.RED, BrainParser.parse(
                    new File("/Users/Sam/Projects/ant-game/ant-game/src/test/resources/brain/ant-brain-1.txt")
            )), new Colony(Colony.Colour.BLACK, BrainParser.parse(
                    new File("/Users/Sam/Projects/ant-game/ant-game/src/test/resources/brain/ant-brain-2.txt")
            )));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(new WorldPanel(match), BorderLayout.CENTER);
    }

    private class WorldPanel extends JPanel {

        private Match match;
        private World world;
        private TexturePaint texture;

        private WorldPanel(Match match) {
            this.world = match.world();
            this.match = match;
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            try {
                texture = new TexturePaint(
                        ImageIO.read(MatchView.class.getResource("/texture.jpg")),
                        new Rectangle(0, 0, 200, 200)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(() -> {
                while (true) {
                    Set<Ant> remove = world.getAnts().parallelStream().filter(Ant::surrounded).collect(Collectors
                            .toSet());
                    remove.parallelStream().forEach(world::murder);
                    world.getAnts().forEach(Ant::step);

                    try {
                        Thread.sleep(1);
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

        @Override
        public void paintComponent(Graphics g) {
            display((Graphics2D) g);
        }

        private void display(Graphics2D gfx) {
            double panelWidth = getWidth();
            double panelHeight = getHeight();
            gfx.clearRect(0, 0, (int) panelWidth, (int) panelHeight);
            gfx.setColor(Color.BLACK);
            gfx.fillRect(0, 0, (int) panelWidth, (int) panelHeight);

            int worldWidth = world.width();
            int worldHeight = world.height();

            double cellWidth = panelWidth / worldWidth * 0.95;
            double cellHeight = panelHeight / worldHeight * 0.95;

            cellWidth = cellHeight = Math.min(cellWidth, cellHeight);
            int yOffset = (int) (Math.abs(panelHeight - (cellHeight * worldHeight)) / 2);
            int xOffset = (int) (Math.abs(panelWidth - (cellWidth * worldWidth)) / 2);

            gfx.setPaint(texture);
            gfx.fillRect(
                    xOffset + (int) cellWidth / 2, yOffset,
                    (int) (cellWidth * worldWidth),
                    (int) (cellHeight * worldHeight)
            );

            for (int y = 0; y < worldHeight; ++y) {
                double offset = xOffset + (y % 2 == 1 ? cellWidth : 0);

                for (int x = 0; x < worldWidth; ++x) {
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

                    if (cell.hasFood()) {
                        cellColour = blend(cellColour, COLOR_FOOD_CELL, 0.5f);
                    }

                    if (cell.hasAnt()) {
                        if (cell.getAnt().getColony().getColour() == Colony.Colour.RED) {
                            cellColour = Color.RED;
                        } else {
                            cellColour = Color.BLACK;
                        }
                    }

                    if (cellColour == null)
                        continue;

                    gfx.setColor(cellColour);

                    gfx.fillOval(
                            (int) (x * cellWidth + offset), (int) (y * cellHeight + yOffset),
                            (int) cellWidth, (int) cellHeight
                    );

                    Color current = gfx.getColor();
                    gfx.setColor(new Color(current.getRed(), current.getGreen(), current.getBlue(), 50));

                    gfx.drawOval(
                            (int) (x * cellWidth + offset), (int) (y * cellHeight + yOffset),
                            (int) cellWidth, (int) cellHeight
                    );
                }
            }
        }

    }

    //http://stackoverflow.com/questions/19398238/how-to-mix-two-int-colors-correctly
    private Color blend( Color c1, Color c2, float ratio ) {
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
