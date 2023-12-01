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
        int size = 5; // størrelsen af vores 'map' (dette er altid kvadratisk)
        int delay = 1000; // forsinkelsen mellem hver skridt af simulationen (i ms)
        int display_size = 800; // skærm oplysningen (i px)
        int amount = 10;
        Random r = new Random();
        Program p = new Program(size, display_size, delay); // opret et nyt program
        World world = p.getWorld(); // hiv verdenen ud, som er der hvor vi skal tilfø½je ting!

        Animal animal = new Animal();

        Location place2 = new Location(0, 2);

        world.setTile(place2, animal);
        DisplayInformation sd = new DisplayInformation(Color.GRAY, "grass");
        DisplayInformation id = new DisplayInformation(Color.GRAY, "rabbit-small");
        //DisplayInformation ad = new DisplayInformation(Color.GRAY, "hole-small");

        p.setDisplayInformation(Person.class, sd);
        p.setDisplayInformation(Animal.class, id);
        //p.setDisplayInformation(Burrow.class, ad);

        Burrow burrow = new Burrow(world);
        burrow.randomSpawn(burrow, world);
        for (int i = 0; i < amount; i++) {
            int x = r.nextInt(5);
            int y = r.nextInt(5);
            Location l = new Location(x, y);

            while (!world.isTileEmpty(l)) {
                x = r.nextInt(5);
                y = r.nextInt(5);
                l = new Location(x, y);
            }


            world.setTile(l, new Person());
        }
        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();


        } // kører 200 runder, altå kaldes 'act' 200 gange for alle placerede aktører

        String folderPath = "data/";

        // List of input files
        String[] inputFileNames = {
                "t1-1a.txt", "t1-1b.txt", "t1-1c.txt", "t1-1d.txt",
                "t1-2a.txt", "t1-2b.txt", "t1-2cde.txt", "t1-2fg.txt",
                "t1-3a.txt", "t1-3b.txt", "tf1-1.txt"
        };

        // Process each input file
        for (String fileName : inputFileNames) {
            String filePath = folderPath + fileName;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Read and process each line of the file
                String line;
                while ((line = reader.readLine()) != null) {
                    processInputLine(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to process each line of input
    private static void processInputLine(String line) {
        // Parse the line and implement your logic here
        // Assuming the line has the format: N type quantity or N type min-max
        // Example: "10 rabbit 5" or "20 fox 2-5"
        String[] parts = line.split("\\s+");
        int N = Integer.parseInt(parts[0]);
        String type = parts[1];
        // Handle quantity or min-max accordingly
        // Your logic here...
    }
    }

