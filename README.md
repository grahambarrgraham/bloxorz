# bloxorz
This is an attempt to (optimally) solve the bloxorz puzzle game using an algorithm. The solver currently solves all levels in 2002
moves -- 2 moves off the formally-verified optimum of 2000 (see [Comparison with bloxorz-aristotle](#comparison-with-bloxorz-aristotle)
below). The code was written in 2009 for the game as it was then (with 33 levels).

# Algorithm
The program uses a simple implementation of the [A*](https://en.wikipedia.org/wiki/A*_search_algorithm) graph traversal algorithm. The tricky bit was to model 
the switches (which toggle the presence/absence of one or more tiles), as this effectively changes the shape of the graph being searched.

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

# Validator / Runner

`Validator` (`src/test/java/blox/Validator.java`) replays a move file against the
real game rules -- `successors()` for move legality and `transformCurrentNode()`
for switch/teleport effects -- instead of trusting whatever produced the moves.
It rejects a level if any move is illegal on the board, or if the final state
isn't at the target. After a multi-target teleport, a state can have more than
one block, and the game lets you freely pick which is active (that's what
`nextBlock`/"S" does); rather than trust the arbitrary pick `Scape.invokeTeleportRule`
makes internally, the validator tries every block-as-active variant and accepts
a move if any of them supports it.

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

This solver currently totals **2002** moves -- matching the proven optimum exactly
on 32 of 33 levels. The one gap is level 15, where this solver finds 59 moves
against a proven optimum of 57. Every other level, including 21 and 24 (the two
levels this repo's `Validator` independently flagged as illegal in the old
`postedSolution.txt` walkthrough), matches the Lean-proven count exactly.

# Update Log

**Current total: 2002 moves** across all 33 levels, validated legal end-to-end --
matches [bloxorz-aristotle](https://github.com/grahambarrgraham/bloxorz-aristotle)'s
proven-optimal 2000 on 32 of 33 levels; level 15 is 2 moves over optimal.

Fixes applied to reach this:
- Corrected an integer overflow in the A* heuristic (`BloxPathComparator.distance`)
  that computed a running sum instead of a minimum across blocks.
- Fixed a double-toggle bug where a switch spanning two tiles under a flat block
  fired its rule twice, canceling itself out (`BloxAStarSearcher.applySwitchRules`
  now dedupes triggered switches before applying them).
- Added `Validator` to check move legality against the actual rules instead of
  trusting hand-authored or externally-sourced move lists.
- Added `Runner` to solve all levels and emit a moves file, and a `Makefile`
  wrapping build/test/run/validate.
- Replaced the inert `log4j.properties` (log4j 1.x syntax, silently ignored by
  the log4j2 dependency actually in use) with a working `log4j2.properties`,
  defaulting to `warn` and overridable via `-Dblox.log.level=...`.
- Modernized the toolchain: Java 25, Gradle 9.7.1 (wrapper regenerated), the
  unused Kotlin plugin removed, log4j bumped to 2.25.3, and tests migrated
  from JUnit 4 to JUnit 5. `Runner`/`Validator` now log each level's result
  and duration via log4j at `info` instead of `System.out.println`. The
  `Makefile` targets are now thin wrappers around `./gradlew`.

