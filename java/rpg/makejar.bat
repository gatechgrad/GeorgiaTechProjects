copy *.java src
copy map\*.java src\map
copy monster\*.java src\monster
copy constants\*.java src\constants
copy textures\*.java src\texture

jar cfmv RPGGenerator.jar manifest.mf *.class src
