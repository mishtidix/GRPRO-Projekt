import Game.*;
import itumulator.executable.*;

import itumulator.world.*;

import java.io.*;
import java.util.Random;

public class inputReader {
    String file;
    World world;
    Program p;

    public inputReader(String file) throws IOException {
        if (file==null)throw new IllegalArgumentException("file cannot be null");
        this.file=file;
       makeProgram();
    }

    public void readInput() throws IOException {
        File file = new File(this.file);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        int lineCount = 0;
        while ((input = br.readLine()) != null) {
            lineCount++;
            if (lineCount==1){
                int size = Integer.parseInt(input);//tørrelsen af vores 'map' (dette er altid kvadratisk)
                int delay = 200; // forsinkelsen mellem hver skridt af simulationen (i ms)
                int display_size = 800; // skærm oplysningen (i px)


                this.p = new Program(size, display_size, delay); // opret et nyt program
                this.world = p.getWorld();
            } else if (lineCount>=2){
                processInputLine(input,this.world);
            }

        }
    }
    private void makeProgram() throws IOException {
        this.world = new World(1);
        this.p = new Program(1, 800, 100);
        File file = new File(this.file);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        int lineCount = 0;
        for (int i = 0 ; i<1; i++) {
            input= br.readLine();
                int size = Integer.parseInt(input);//tørrelsen af vores 'map' (dette er altid kvadratisk)
                int delay = 200; // forsinkelsen mellem hver skridt af simulationen (i ms)
                int display_size = 800; // skærm oplysningen (i px)


                this.p = new Program(size, display_size, delay); // opret et nyt program
                this.world = p.getWorld();

        }
    }
    private static void processInputLine(String input, World world) {
        Random r = new Random();
        String[] parts = input.split(" ");
        String type = parts[0];
        String[] quantity = parts[1].contains("fungi") ? parts[parts.length - 1].split("-") : parts[1].split("-");

//            parts[1].split("-");
        int min = Integer.parseInt(quantity[0]);
        int max = quantity.length > 1 ? Integer.parseInt(quantity[1]) : min;

        int count = min + r.nextInt(max - min + 1);

        for(int i = 0 ; i < count ; i++){
            switch (type){
                case "carcass":
                    boolean isInfected = parts.length > 2 && parts[1].equals("fungi");
                    Carcass carcass = new Carcass(world, isInfected);
                    carcass.randomSpawn(carcass,world);
                    break;
                case "rabbit":
                    Rabbit rabbit = new Rabbit(world);
                    rabbit.randomSpawn(rabbit, world);
                    break;
                case "grass":
                    Grass grass = new Grass(world);
                    grass.randomSpawnNonBlocking(grass, world);
                    break;
                case "burrow":
                    Burrow burrow = new Burrow(world);
                    burrow.randomSpawnNonBlocking(burrow, world);
                    break;
                case "wolf":
                    Wolf wolf = new Wolf(world);
                    wolf.randomSpawn(wolf, world);
                    break;
                case "bear":
                    if (parts.length>=3) {
                        String territory[] = parts[parts.length-1].split("");
                         int x  = Integer.parseInt(territory[1]);
                         int y = Integer.parseInt(territory[3]);
                        Location location = new Location(x,y);
                        world.setTile(location, new Bear(world,location));
                    }else {
                        Bear bear = new Bear(world);
                        bear.randomSpawn(bear,world);
                    }
                    break;
                case "berry":
                    Berry berry = new Berry(world);
                    berry.randomSpawnNonBlocking(berry,world);
                    break;
                case "fungi":
                    Fungi fungi = new Fungi(world);
                    fungi.randomSpawn(fungi,world);
            }
        }

    }

    public Program getP() {
        System.out.println("succes");
        return p;
    }
}
