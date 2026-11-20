package dev.marvin.evolvingdungeon.generation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DungeonPathFinder {

  private Map<RoomPos, Integer> findDistances(DungeonLayout layout) {
    Deque<RoomPos> roomPosDeque = new ArrayDeque<>();
    Set<RoomPos> visitedRooms = new HashSet<>();
    Map<RoomPos, Integer> roomDistanceMap = new HashMap<>();

    Map<RoomPos, Set<RoomPos>> connections = layout.connections();
    RoomPos startRoom = layout.start();

    visitedRooms.add(startRoom);
    roomPosDeque.addLast(startRoom);
    roomDistanceMap.put(startRoom, 0);


    while (!roomPosDeque.isEmpty()) {
      RoomPos currentRoom = roomPosDeque.pollFirst();

      Iterator<RoomPos> roomIterator = connections.get(currentRoom).iterator();
      while (roomIterator.hasNext()) {
        RoomPos nextRoom = roomIterator.next();
        if (!visitedRooms.contains(nextRoom)) {
          visitedRooms.add(nextRoom);
          roomPosDeque.addLast(nextRoom);
          roomDistanceMap.put(nextRoom, roomDistanceMap.get(currentRoom) + 1);
        }
      }
    }


    return roomDistanceMap;
  }


  public RoomPos findBossRoom(DungeonLayout layout) {
    Map<RoomPos, Integer> roomDistanceMap = findDistances(layout);
    Integer max = 0;
    RoomPos maxDistancedRoom = layout.start();

    Iterator<RoomPos> roomPosIterator = roomDistanceMap.keySet().iterator();
    while (roomPosIterator.hasNext()) {
      RoomPos nextRoom = roomPosIterator.next();

      int potentialMax = roomDistanceMap.get(nextRoom);
      if (potentialMax > max) {
        maxDistancedRoom = nextRoom;
        max = potentialMax;
      }
    }

    return maxDistancedRoom;
  }


}
