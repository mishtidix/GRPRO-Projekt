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

inputReader in = new inputReader("data/t3-2ab.txt");
in.readInput();
        Program p = in.getP();

        // hiv verdenen ud, som er der hvor vi skal tilfø½je ting!





        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();



        } // kører 200 runder, altå kaldes 'act' 200 gange for alle placerede aktører

  }

    // Method to process each line of input

    }

