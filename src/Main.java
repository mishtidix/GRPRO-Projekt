import java.awt.Color;
import java.io.*;

import Game.Burrow;
import Game.*;
import itumulator.executable.*;

import itumulator.world.*;

import itumulator.simulator.*;
import itumulator.display.utility.ImageResourceCache;
import java.awt.image.BufferedImage;

import java.awt.*;

import javax.swing.*;

import java.util.*;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
        int size = 5; // størrelsen af vores 'map' (dette er altid kvadratisk)
        int delay = 300; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // skærm opløsningen (i px)

        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilføje ting!


        Rabbit rabbit = new Rabbit(world);
        rabbit.randomSpawn(rabbit, world);

        Grass grass = new Grass(world);
        grass.randomSpawnNonBlocking(grass, world);


        Burrow burrow = new Burrow(world);
        burrow.randomSpawn(burrow, world);




        p.show(); // viser selve simulationen




        for (int i = 0; i < 200; i++) {
        p.simulate();
        } // kører 200 runder, altså kaldes 'act' 200 gange for alle placerede aktører
        */

        World world = new World(1);
        Program p = new Program(1,100,800);

        File file = new File("data/t1-2cde.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        int lineCount = 0;
        while ((input = br.readLine()) != null) {
            lineCount++;
            if (lineCount==1){
                int size = Integer.parseInt(input);//tørrelsen af vores 'map' (dette er altid kvadratisk)
                int delay = 200; // forsinkelsen mellem hver skridt af simulationen (i ms)
                int display_size = 800; // skærm oplysningen (i px)


                p = new Program(size, display_size, delay); // opret et nyt program
                world = p.getWorld();
            } else if (lineCount>=2){
                processInputLine(input,world);
            }

        }
        // hiv verdenen ud, som er der hvor vi skal tilfø½je ting!





        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();



        } // kører 200 runder, altå kaldes 'act' 200 gange for alle placerede aktører

    }

    // Method to process each line of input
    private static void processInputLine(String input, World world) {
        Random r = new Random();
        String[] parts = input.split(" ");
        String type = parts[0];
        String[] quantity = parts[parts.length - 1].split("-");


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
            }
        }

    }
}

