package dev.marvin.evolvingdungeon.generation;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DungeonGeneratorTest {

  @Test
  void generatedDungeonShouldHave20Rooms() {

    DungeonGenerator generator = new DungeonGenerator();
    DungeonLayout layout = generator.generate(20);

    assertEquals(20, layout.connections().size());
  }

  @Test
  void generatedDungeonShouldHave20ReachableRooms() {
    DungeonGenerator generator = new DungeonGenerator();
    DungeonLayout layout = generator.generate(20);

    Deque<RoomPos> roomPosDeque = new ArrayDeque<>();
    Set<RoomPos> visitedRooms = new HashSet<>();

    Map<RoomPos, Set<RoomPos>> connections = layout.connections();
    RoomPos startRoom = layout.start();

    visitedRooms.add(startRoom);
    roomPosDeque.addLast(startRoom);


    while (!roomPosDeque.isEmpty()) {
      RoomPos currentRoom = roomPosDeque.pollFirst();

      Iterator<RoomPos> roomIterator = connections.get(currentRoom).iterator();
      while (roomIterator.hasNext()) {
        RoomPos nextRoom = roomIterator.next();
        if (!visitedRooms.contains(nextRoom)) {
          visitedRooms.add(nextRoom);
          roomPosDeque.addLast(nextRoom);
        }
      }


    }

    assertEquals(visitedRooms.size(), 20);

  }

  @Test
  void generatedDungeonWith20RoomsShouldHaveBidirectionalConnections() {
    DungeonGenerator generator = new DungeonGenerator();
    DungeonLayout layout = generator.generate(20);

    Deque<RoomPos> roomPosDeque = new ArrayDeque<>();
    Set<RoomPos> visitedRooms = new HashSet<>();

    Map<RoomPos, Set<RoomPos>> connections = layout.connections();
    RoomPos startRoom = layout.start();

    visitedRooms.add(startRoom);
    roomPosDeque.addLast(startRoom);


    boolean isBidirectional = true;
    while (!roomPosDeque.isEmpty()) {
      RoomPos currentRoom = roomPosDeque.pollFirst();

      Iterator<RoomPos> roomIterator = connections.get(currentRoom).iterator();
      while (roomIterator.hasNext()) {
        RoomPos nextRoom = roomIterator.next();
        if (!visitedRooms.contains(nextRoom)) {
          visitedRooms.add(nextRoom);
          roomPosDeque.addLast(nextRoom);
          if (!connections.get(currentRoom).contains(nextRoom) || !connections.get(nextRoom).contains(currentRoom)) {
            isBidirectional = false;
          }

        }
      }


    }

    assertEquals(isBidirectional, true);
  }


}