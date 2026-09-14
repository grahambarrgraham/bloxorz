# bloxorz
This is an attempt to (optimally) solve the bloxorz puzzle game using an algorithm, that I wrote on the bus to work over several weeks. The solver currently solves all levels in 2000
moves -- matching the formally-verified optimum exactly (see [Comparison with bloxorz-aristotle](#comparison-with-bloxorz-aristotle)
below). The code was written in 2009 for the game as it was then (with 33 levels). At that time the solver found a solution in 2002 moves.

# Algorithm
The program attempts to use the [A*](https://en.wikipedia.org/wiki/A*_search_algorithm) graph traversal algorithm. The tricky bits were to model 
the switches (which toggle the presence/absence of one or more tiles), as this effectively changes the shape of the graph being searched, and teleports, which it turns out make it inpractical to implement an heuristic which is admissible for use by A* (explained below).

As it turns out that Breadth First Search (BFS) is more that sufficient for the number of states that have to be explored, and is much simpler and faster. As noted below, in the A* approach the heurstic had to be switched off, so the alogorithm is now logically the same as BFS, but with more overhead. The lesson here is always do the simplest thing first and see if it works, so leaving this repo as-is for prosperity.

## Heuristic (disabled)

`BloxPathComparator.cost()` holds a properly-scaled heuristic, kept but disabled
(`BasePathComparator.dijkstraMode = true`, plain Dijkstra). It's inadmissible on
any level with a teleport -- a teleport move covers arbitrary distance for the
cost of one roll, so the heuristic can overestimate and break A*'s optimality
guarantee (verified: level 16 regressed 28 -> 30 rolls with it on). 10 of 33
levels have teleports, so this isn't a corner case worth ignoring.

# Level Models
Each level in the game is modelled in a resource file in `/src/main/resources/level<x>.txt`. These files contain an array of characters/codes which map to the tiles on the level. The characters/codes are intepretted according to this table :

    x = missing (i.e. no tile)
    p = plain tile
    s = starting point
    e = end point (target)
    t = teleport
    S = strong switch ( i.e. block needs to be end-on)
    W = weak switch (i.e. any contact will trigger)
    w = weak tile (i.e. will break if arrived at end on)

Where a tile type is followed by a number, the number identifies a switch. 

Example:

    x  x  p  p  x  x  x  x  x  x  x  x  x  x
    x  x  p  p  x  x  x  x  x  x  x  x  x  x
    x  x  p  p  p  x  x  x  x  x  x  x  x  x
    x  x  p  p  W1 x  x  x  x  x  p  p  p  x4
    x  x  x  p  p  p  p  x3 x  x  p  e  p  x4
    x  x  x  x  x  x  p  p  x2 x2 p  p  p  x
    x  p  p  x  x  x  p  p  x  x  x  x  x  x
    p  p  S1 p  p1 p1 p  p  x  x  x  x  x  x
    p  s  p2 x  x  x  p  p  x  x  x  p  p  p
    p  p  p2 x  x  x  p  p  W2 p  p  p  p  p
    x  x  x  x  x  x  x  x  x  x  x  p  p  p
    W1 toggles x2, x4
    W2 closes p1
    W2 opens x3
    S1 opens x2

# Move Counts

Only Up, Down, Left, Right moves are counted, switches between blocks are (after a teleport the block is split into two small ones) are not counted. This is a practical limitation in transcribing from the real game rules, I could not be sure for every situation which of the two blocks would be active after a teleport. 

# Validator / Runner

`Validator` (`src/test/java/blox/Validator.java`) replays a move file against
the real game rules (`successors()`, which now resolves switch/teleport effects
per generated edge) instead of trusting whatever produced the moves. It rejects
a level if any move is illegal on the board, or if the final state isn't at the
target. After a multi-target teleport, a state can have more than one block,
and the game lets you freely pick which is active (that's what `nextBlock`/"S"
does); rather than trust the arbitrary pick `Scape.invokeTeleportRule` makes
internally, the validator tries every block-as-active variant and accepts a
move if any of them supports it.

`Runner` (`src/test/java/blox/Runner.java`) solves all 33 levels with
`BloxAStarSearcher`, writes the result to `generated/solution.txt` (same format,
gitignored), and prints a per-level move-count report.

## Via Make

```
make build      # gradle build (compiles main + test sources)
make test       # gradle test (runs the JUnit 5 suite)
make run        # solves all levels, writes generated/solution.txt
make validate   # validates a moves file (defaults to generated/solution.txt)
make clean      # gradle clean, removes generated/
```

`run` and `validate` always report each level's result (and how long it took)
at `info`, regardless of the root log level; the solver's own internal
chatter stays at `warn` by default. Raise or lower the root level with
`LOG_LEVEL`:

```
make run LOG_LEVEL=debug
make validate LOG_LEVEL=debug MOVES=path/to/moves.txt
```

Typical workflow after changing the solver: `make run` to regenerate a
solution, then `make validate` to confirm the solver's own output is legal
end-to-end.

## Directly with Gradle

```
./gradlew run -PoutputDir=generated -PlogLevel=warn
./gradlew validate -Pmoves=path/to/moves.txt -PlogLevel=warn
```

# Comparison with bloxorz-aristotle

[bloxorz-aristotle](https://github.com/grahambarrgraham/bloxorz-aristotle) formally
verifies (in Lean 4) the optimal move count for each of these 33 levels, proving a
total of **2000** moves is optimal and that 1999 is impossible. It also refutes an
earlier "1999 moves" claim: the walkthrough behind that claim rolls a block onto a
missing tile at move 59 on level 21, so it isn't a legal play at all.

This solver now totals **2000** moves -- matching the proven optimum exactly on
all 33 levels.

# Update Log

2026 - fixes applied:

- **Eager switch resolution** (state-representation bug, cost level 15 two extra
  moves -- 59 vs. the proven-optimal 57). Switch application used to happen
  lazily, once per *closed* node, and was skipped entirely when the preceding
  move was `nextBlock` (correct in isolation -- switching focus doesn't move
  anything, so it shouldn't re-trigger a switch). But this meant two different
  arrivals at the same raw board position -- one via `nextBlock`, one via an
  actual roll -- could collide in the same `closed`-set entry despite needing
  genuinely different futures (one with a switch toggled, one without). When the
  cheaper arrival happened to be the `nextBlock` one, it could permanently
  foreclose the costlier roll arrival's switch-triggered continuation. Fixed by
  resolving switches eagerly, per generated edge, in
  `BloxAStarSearcher.successors()`, so the two arrivals now produce genuinely
  distinct (non-`.equals()`) nodes instead of colliding. Trades a modest amount
  of search performance (~10% slower across all 33 levels, still well under a
  second each) for removing an over-pruning bug that could silently return
  suboptimal solutions.
- Corrected an integer overflow in the A* heuristic (`BloxPathComparator.distance`)
  that computed a running sum instead of a minimum across blocks.
- Fixed a double-toggle bug where a switch spanning two tiles under a flat block
  fired its rule twice, canceling itself out (`BloxAStarSearcher.applySwitchRules`
  now dedupes triggered switches before applying them).
- Added `Validator` and `Runner`, and updated to modern tooling (Gradle, JVM, libs).
