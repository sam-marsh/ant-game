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

    private static final Color COLOR_CLEAR_CELL = new Color(100, 57, 0, 60);
    private static final Color COLOR_ROCKY_CELL = new Color(100, 57, 0);
    private static final Color COLOR_RED_ANTHILL_CELL = new Color(195, 80, 63);
    private static final Color COLOR_BLACK_ANTHILL_CELL = new Color(88, 88, 88);
    private static final Color COLOR_FOOD_CELL = Color.GREEN.darker().darker();

    public MatchView(GUI context, Match match) {
        super(context);
        setLayout(new BorderLayout());
        try {
            match.world().spawnAnts(new Colony(Colony.Colour.RED, BrainParser.parse(
                    new File("/Users/Sam/Projects/ant-game/ant-game/src/test/resources/brain/ant-brain-2.txt")
            )), new Colony(Colony.Colour.BLACK, BrainParser.parse(
                    new File("/Users/Sam/Projects/ant-game/ant-game/src/test/resources/brain/ant-brain-2.txt")
            )));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        add(new WorldPanel(match.world()), BorderLayout.CENTER);
    }

    private class WorldPanel extends JPanel {

        private World world;
        private TexturePaint texture;

        private WorldPanel(World world) {
            this.world = world;
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createBevelBorder(BevelBorder.LOWERED)
            ));
            try {
                texture = new TexturePaint(
                        ImageIO.read(MatchView.class.getResource("/texture.jpg")),
                        new Rectangle(0, 0, getWidth(), getHeight())
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

            int worldWidth = world.width();
            int worldHeight = world.height();

            double cellWidth = panelWidth / worldWidth * 0.95;
            double cellHeight = panelHeight / worldHeight * 0.95;

            cellWidth = cellHeight = Math.min(cellWidth, cellHeight);
            int yOffset = (int) (Math.abs(panelHeight - (cellHeight * worldHeight)) / 2);
            int xOffset = (int) (Math.abs(panelWidth - (cellWidth * worldWidth)) / 2);

            gfx.setPaint(texture);

            for (int y = 0; y < worldHeight; ++y) {
                double offset = xOffset + (y % 2 == 1 ? cellWidth : 0);

                for (int x = 0; x < worldWidth; ++x) {
                    Color cellColour;
                    Cell cell = world.cell(x, y);
                    switch (cell.getType()) {
                        case CLEAR:
                            cellColour = COLOR_CLEAR_CELL;
                            break;
                        case ROCK:
                            cellColour = COLOR_ROCKY_CELL;
                            break;
                        case ANTHILL_BLACK:
                            cellColour = COLOR_BLACK_ANTHILL_CELL;
                            break;
                        case ANTHILL_RED:
                            cellColour = COLOR_RED_ANTHILL_CELL;
                            break;
                        default:
                            throw new AssertionError("unimplemented");
                    }
                    if (cell.hasFood()) {
                        cellColour = COLOR_FOOD_CELL;
                    }
                    if (cell.hasAnt()) {
                        if (cell.getAnt().getColony().getColour() == Colony.Colour.RED) {
                            cellColour = Color.RED;
                        } else {
                            cellColour = Color.BLACK;
                        }
                    }
                    if (cellColour == COLOR_CLEAR_CELL) {
                        continue;
                    }
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

}
