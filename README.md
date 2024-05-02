# Anti-Creeper

## Protection from creepers - in many variations

Creeper explosions can be incredibly frustrating. They destroy nice landscapes or even expensive and time consuming builds. This mod provides a solution to that. In fact, it provides multiple solutions. 
1. If you want to, you can prevent all creeper explosions from destroying blocks. This is the simplest solution to the problem.
2. However, maybe you want to only protect some areas from creeper explosions. You can do that too by choosing the two outer most blocks of the area and letting the mod create either a circle or a rectangle to protect. You can define as many areas as you like and pause this feature at any time. 

## Configuration

The mod comes with certain built in commands. 
1. /anticreeper load: This command reloads the configuration file, in case you have mande any changes to the file while the game is running.
2. /anticreeper setDestroy <value>: Choose 0 to prevent creepers from destroying blocks, choose 1 (or any other integer) to allow creepers to destroy blocks.
3. /anticreeper setProtected <value>: Choose 0 to turn area protection off, choose 1 (or any other integer) to turn area protection on.

Unfortunately, there is not yet a command to set up or delete protected areas.

To do so, open your minecraft folder and go to /config. Open anti-creeper.properties. Here you see all mod settings. Next to areas= you can add areas to protect. 

To add an area, write: \<x1>,\<z1>,\<x2>,\<z2>,\<type>;

without any spaces. \<x1> and \<z1> should be replaced with the x and z coordinates of one of the blocks you chose to define the protected area. \<x2> and \<z2> should be replaced with the x and z coordinates of the other block. \<type> should be replaced with either circle or rectangle, depending on the shape you want to protect. 

An example would be: areas=0,0,8,8,rectangle;0,0,-8,-8,circle;

Once youre done modifying the file, save it and run /anticreeper load (or restart the game).
