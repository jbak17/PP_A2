Assignment 2:

GOAL:

Find the highest peak on a 2d surface with each (x,y) on the cartesian plane having a height (H).

APPROACH:
The program is run through the file Main.scala, which includes all of the code to run the GUI. The swarm is implemented as a mutable sequence of 'Insect' objects. The size of the swarm and the duration which they search can be adjusted in the Main.scala file. Insects keep track of the highest position and height that they have seen to date, and have all of the functionality necessary for their operation. Insects are defined in Insect.scala. All of the necessary code to manipulate a collection of Insects is included in the Simulator.scala file. This includes output information to the console, updating positions, etc. 

The code was developed on my local machine running Ubuntu 16.04, Scala 2.12.1. The code is also available from github (https://github.com/jbak17/PP_A2)

The most challenging part of the project was the GUI, and I remain unable to get the JavaFx chart to work correctly. 


CODE EXPLANATION:

Main
  Includes controls the size of swarm and number of iterations.
  Contains functionality for GUI

Insect
  Includes functions necessary to create and update insect objects

Simulator
  Includes functions for updating multiple insects (a swarm) and providing telemetry
  on that swarm.

METHOD:

Particle swarm optimisation (PSO). PSOs attempt to mimic the way swarms of animals can solve problems. Imagine a thousand ants crawling over the terrain, each exploring for the highest point in its vicinity.


We create some number (say, 1000) particles, at random locations on the terrain. Each particle has a position and a velocity. And then we run a loop -- at each step of the loop:

   - each particle's velocity is updated based on an equation that takes into account the 	highest point it has seen, and the highest point the whole system has seen so far.
   - each particle's position is updated based on its velocity

Then at each step, we will update each particle's velocity to be

    vx ← ( 0.5 vx ) + 2 r1 ( lx - x ) + 2 r2 ( gx - x )
    vy ← ( 0.5 vy ) + 2 r3 ( ly - y ) + 2 r4 ( gy - y )

So that the velocities involved don’t become too great, we will “clamp” vx and vy to the range −1 to 1. By this we mean that if either vx or vy is below −1 then we set it to −1, and if either is larger than 1 then we set it to 1, otherwise we leave them as is.

Next, the position of each particle is updated by the rule

    x ← x + vx
    y ← y + vy

After each iteration the highest seen value, and its location, for each particle and for the whole particle system are updated, if needed.

Let the loop run for a large number of iterations, and chart where the particles are, and how the "highest points seen" for the system and for each particle change from iteration to iteration. You will need to keep some additional data structures (probably mutable Buffers) to keep the history of the "highest points seen" for charting.


Your program needs to:

    Implement the particle swarm optimisation
    Run a simulation with a configurable number of particles
    Log to standard out at each step of the loop:
        the best location seen so far
        the location of the five highest particles
    Chart in the UI
        the height of the particles
        the positions of the particles, marking the highest known point in red

Please note, you will be given a little JavaFX UI code to draw the squares. You can call Java code from Scala. On Turing, you would need to ensure you are using the Oracle JDK in order to run JavaFx. Instructions will be given closer to time for this.



PROVIDED BY ASSESSOR:

Method for height: def height(x:Double, y:Double):Double = sin(10πx)sin(10πy)−10((x−0.5)^2 +(y−0.5)^2)

GUI

Equations:
	- x and y represent the particle's current position;
	- vx and vy represent the x and y components of the particle's velocity
	- r1 , r2 , r3 , etc represent random numbers between 0 and 1
	- lx and ly represent the location of the highest point seen by this particle (l for local)
	- gx and gy represent the location of the highest point seen by the entire system (g for global)
