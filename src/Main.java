import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    public static void main(String[] args) {
        int size = 15;//tørrelsen af vores 'map' (dette er altid kvadratisk)
        int delay = 100; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // skærm oplysningen (i px)


        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilfø½je ting!


Grass grass1 = new Grass(world);
grass1.randomSpawn(grass1,world);
Burrow burrow1 = new Burrow(world);
burrow1.randomSpawn(burrow1, world);

String input = "rabbit 1";
        processInputLine(input,world);
//        Strivg[] parts = input.split(" ");
//        String type = parts[0];
//        String[] quantity = parts[parts.length - 1].split("-");
//
//
//        int min = Integer.parseInt(quantity[0]);
//        int max = quantity.length > 1 ? Integer.parseInt(quantity[1]) : min;
//
//        int count = min + r.nextInt(max - min + 1);
//
//        for(int i = 0 ; i < count ; i++){
//            switch (type){
//                case "carcass":
//                    boolean isInfected = parts.length > 2 && parts[1].equals("fungi");
//                    Carcass carcass = new Carcass(world, isInfected);
//                    carcass.randomSpawn(carcass,world);
//                    break;
//                case "rabbit":
//                    Rabbit rabbit = new Rabbit(world);
//                    rabbit.randomSpawn(rabbit, world);
//                    break;
//                case "grass":
//                    Grass grass = new Grass(world);
//                    grass.randomSpawn(grass, world);
//                    break;
//            }
//        }

        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();



        } // kører 200 runder, altå kaldes 'act' 200 gange for alle placerede aktører

//        String folderPath = "data/";
//
//        // List of input files
//        String[] inputFileNames = {
//                "t1-1a.txt", "t1-1b.txt", "t1-1c.txt", "t1-1d.txt",
//                "t1-2a.txt", "t1-2b.txt", "t1-2cde.txt", "t1-2fg.txt",
//                "t1-3a.txt", "t1-3b.txt", "tf1-1.txt"
//        };
//
//        // Process each input file
//        for (String fileName : inputFileNames) {
//            String filePath = folderPath + fileName;
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//                // Read and process each line of the file
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    processInputLine(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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
                    grass.randomSpawn(grass, world);
                    break;
            }
        }

    }
    }

