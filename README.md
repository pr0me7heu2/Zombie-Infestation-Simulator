# Brrrrraiiiins
>
## Overview
This simulates the results of a Zombie outbreak in a small downtown area.  

At the beginning, there are NUM_HUMANS generated in random locations throughout the map in addition to one zombie and a number of ghosts, survivors, and dogs.

You can add more humans by clicking on the map.

## Rules

### Humans

Humans move one unit at a time and have a 10% chance of changing direction each time they move.

If they see a zombie, however, humans will turn around and move two units.

### Zombies

Zombies move one unit at a time and have a 20% chance of changing direction each time they move.

If they see a human, however, they don't change direction and keep moving toward them so long as they can still see them.

If a zombie gets adjacent to a human, they infect the nearby human.

### Ghosts

Ghosts are scary.

Ghosts move two units at a time and have a 45% chance of changing  directions each time they move.

If a human see a ghost, they will turn around and move two units away.  If a dog sees a ghost, they will turn around and move one unit away.

### Dogs

Dogs are vicious.

Dogs move two units at a time and have a 15% chance of changing direction each time they move.

If they see a zombie, however, they don't change direction and keep moving toward them so long as they can still see them.

If a dog gets adjacent to a zombie, they kill the nearby zombie.

### Survivors

Survivors are bad ass.

Survivors move just like humans except when the zombies get close enough they fight back and win.

## Design Diagram
>![UML Image](UML%20Diagram.png)


