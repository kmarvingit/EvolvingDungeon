package dev.marvin.evolvingdungeon.generation;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DungeonPathFinderTest {

  @Test
  void singleRoomShouldBeBossRoom() {

    RoomPos start = new RoomPos(0, 0);


    Map<RoomPos, Set<RoomPos>> graph =
        new HashMap<>();

    graph.put(start, new HashSet<>());

    DungeonLayout layout =
        new DungeonLayout(graph, start);

    DungeonPathFinder pathFinder =
        new DungeonPathFinder();


    RoomPos roomPos = pathFinder.findBossRoom(layout);

    assertEquals(roomPos, start);

  }


  @Test
  void linearDungeonShouldSelectLastRoom() {
    RoomPos start = new RoomPos(0, 0);


    Map<RoomPos, Set<RoomPos>> graph =
        new HashMap<>();

    graph.put(start, new HashSet<>());
    RoomPos roomB = new RoomPos(1, 0);
    graph.put(roomB, new HashSet<>());
    graph.get(start).add(roomB);
    graph.get(roomB).add(start);

    RoomPos roomC = new RoomPos(2, 0);
    graph.put(roomC, new HashSet<>());
    graph.get(roomB).add(roomC);
    graph.get(roomC).add(roomB);


    RoomPos roomD = new RoomPos(3, 0);
    graph.put(roomD, new HashSet<>());
    graph.get(roomC).add(roomD);
    graph.get(roomD).add(roomC);



    DungeonLayout dungeonLayout = new DungeonLayout(graph, start);
    DungeonPathFinder pathFinder = new DungeonPathFinder();
    assertEquals(pathFinder.findBossRoom(dungeonLayout), roomD);

  }



  @Test
  void branchedDungeonShouldSelectFarthestRoom() {

    RoomPos roomA = new RoomPos(0, 0);
    RoomPos roomB = new RoomPos(1, 0);
    RoomPos roomC = new RoomPos(2, 0);
    RoomPos roomD = new RoomPos(1, 1);
    RoomPos roomE = new RoomPos(2, 1);
    RoomPos roomF = new RoomPos(3, 1);

    Map<RoomPos, Set<RoomPos>> graph = new HashMap<>();

    graph.put(roomA, new HashSet<>());
    graph.put(roomB, new HashSet<>());
    graph.put(roomC, new HashSet<>());
    graph.put(roomD, new HashSet<>());
    graph.put(roomE, new HashSet<>());
    graph.put(roomF, new HashSet<>());

    graph.get(roomA).add(roomB);
    graph.get(roomB).add(roomA);

    graph.get(roomB).add(roomC);
    graph.get(roomC).add(roomB);

    graph.get(roomB).add(roomD);
    graph.get(roomD).add(roomB);

    graph.get(roomD).add(roomE);
    graph.get(roomE).add(roomD);

    graph.get(roomE).add(roomF);
    graph.get(roomF).add(roomE);

    DungeonLayout layout = new DungeonLayout(graph, roomA);


    DungeonPathFinder pathFinder = new DungeonPathFinder();
    RoomPos bossRoom = pathFinder.findBossRoom(layout);

    assertEquals(roomF, bossRoom);
  }

}
