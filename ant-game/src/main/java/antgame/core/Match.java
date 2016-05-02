/*
 * A match between two supplied players
 */
package antgame.core;

import antgame.core.Colony.Colour;
import antgame.core.world.*;
import antgame.core.world.Cell.Type;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Regan
 */
public class Match {

    public static int NUM_ROUNDS = 300000;

    private final Player playerRed;
    private final Player playerBlack;
    private final World world;

    public Match(Player playerRed, Player playerBlack, World world)
    {
        this.playerRed = playerRed;
        this.playerBlack = playerBlack;
        this.world = world;
    }
    
    public MatchOutcome run(int rounds, int speed)
    {
        int redFood = 0;
        int blackFood = 0;
        
        world.spawnAnts(new Colony(Colour.RED, playerRed.getBrain()), new Colony(Colour.BLACK, playerBlack.getBrain()));
        for (int i = 0; i < rounds; i++)
        {
            Set<Ant> toRemove = world.getAnts().stream().filter(Ant::surrounded).collect(Collectors.toSet());
            toRemove.stream().forEach(world::murder);

            world.getAnts().forEach(Ant::step);

            try {
                Thread.sleep(100 / speed);
            } catch (InterruptedException ignore) {}
        }

        for (int x = 0; x < world.width(); x++)
        {
            for (int y = 0; y < world.height(); y++)
            {
                Cell curr = world.cell(x, y);
                if (world.cell(x, y).getType() == Type.ANTHILL_RED)
                {
                    redFood += curr.getFoodAmount();
                }
                else if (world.cell(x, y).getType() == Type.ANTHILL_BLACK)
                {
                    blackFood += curr.getFoodAmount();
                }
            }
        }

        if (redFood > blackFood) {
            return new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.WIN, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.LOSS, this)
            );
        } else if (blackFood > redFood) {
            return new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.LOSS, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.WIN, this)
            );
        } else {
            return new MatchOutcome(
                    new PlayerOutcome(playerRed, PlayerOutcome.Result.DRAW, this),
                    new PlayerOutcome(playerBlack, PlayerOutcome.Result.DRAW, this)
            );
        }

    }

    public World world() {
        return world;
    }
}
