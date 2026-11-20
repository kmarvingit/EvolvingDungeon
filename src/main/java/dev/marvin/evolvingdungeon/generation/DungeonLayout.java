package dev.marvin.evolvingdungeon.generation;

import java.util.Map;
import java.util.Set;

public record DungeonLayout(
    Map<RoomPos, Set<RoomPos>> connections,
    RoomPos start
) {
}