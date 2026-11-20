package dev.marvin.evolvingdungeon.generation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DungeonGenerator {

  private final Random random = new Random();

  private static final int[][] DIRECTIONS = {
      {1, 0},
      {-1, 0},
      {0, 1},
      {0, -1}
  };

  public DungeonLayout generate(int roomCount) {

    if (roomCount < 1) {
      throw new IllegalArgumentException(
          "roomCount must be positive"
      );
    }

    Map<RoomPos, Set<RoomPos>> graph =
        new HashMap<>();

    List<RoomPos> rooms = new ArrayList<>();

    RoomPos start = new RoomPos(0, 0);
    graph.put(start, new HashSet<>());
    rooms.add(start);

    while (rooms.size() < roomCount) {

      RoomPos current =
          rooms.get(random.nextInt(rooms.size()));

      int[] direction =
          DIRECTIONS[random.nextInt(4)];

      RoomPos next = new RoomPos(
          current.x() + direction[0],
          current.z() + direction[1]
      );

      if (graph.containsKey(next)) {
        continue;
      }

      graph.put(next, new HashSet<>());
      rooms.add(next);

      graph.get(current).add(next);
      graph.get(next).add(current);
    }

    return new DungeonLayout(graph, start);
  }
}