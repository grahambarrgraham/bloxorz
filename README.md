# bloxorz
This is an attempt to (optimally) solve the bloxorz puzzle game using an algorithm. The solver currently solves all levels in 2002
moves. The code was written in 2009 for the game as it was then (with 33 levels). A solution has been posted with 1999 moves, so the solver needs a bit more work!

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
