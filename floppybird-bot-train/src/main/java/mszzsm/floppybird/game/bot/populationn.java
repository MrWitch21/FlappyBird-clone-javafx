package mszzsm.floppybird.game.bot;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class populationn {
    public static List<Bird> players;
    private int generation;
    private List<Species> species;
    private int size;
    public populationn(int size, double width, double height, GraphicsContext ctx) {


        players = new ArrayList<>();
        this.generation = 1;
        this.species = new ArrayList<>();
        this.size = size;
        for (int i = 0; i < size; i++) {
            players.add(new Bird(width, height, ctx));
        }
    }

    public void updateLivePlayers() {
        for (Bird p : players) {
            if (p.IsAlive) {
                p.look();
                p.think();
            }
        }
    }

    public void naturalSelection() {
        System.out.println("SPECIATE");
        this.speciate();
        System.out.println("CALCULATE FITNESS");
        this.calculateFitness();
        System.out.println("KILL EXTINCT");
        this.killExtinctSpecies();
        System.out.println("KILL STALE");
        this.killStaleSpecies();
        System.out.println("SORT BY FITNESS");
        this.sortSpeciesByFitness();
        System.out.println("CHILDREN FOR NEXT GEN");
        this.nextGen();
    }

    public void speciate() {
        for (Species s : this.species) {
            s.setPlayers(new ArrayList<>());
        }
        for (Bird p : this.players) {
            boolean addToSpecies = false;
            for (Species s : this.species) {
                if (s.similarity(p.getBrain())) {
                    s.addToSpecies(p);
                    addToSpecies = true;
                    break;
                }
            }
            if (!addToSpecies) {
                this.species.add(new Species(p));
            }
        }
    }

    public void calculateFitness() {
        for (Bird p : this.players) {
            p.calculateFitness();
        }
        for (Species s : this.species) {
            s.calculateAverageFitness();
        }
    }

    public void killExtinctSpecies() {
        List<Species> speciesBin = new ArrayList<>();
        for (Species s : this.species) {
            if (s.getPlayers().size() == 0) {
                speciesBin.add(s);
            }
        }
        for (Species s : speciesBin) {
            this.species.remove(s);
        }
    }

    public void killStaleSpecies() {
        List<Bird> playerBin = new ArrayList<>();
        List<Species> speciesBin = new ArrayList<>();
        for (Species s : this.species) {
            if (s.getStaleness() >= 8) {
                if (this.species.size() > speciesBin.size() + 1) {
                    speciesBin.add(s);
                    for (Bird p : s.getPlayers()) {
                        playerBin.add(p);
                    }
                } else {
                    s.setStaleness(0);
                }
            }
        }
        for (Bird p : playerBin) {
            this.players.remove(p);
        }
        for (Species s : speciesBin) {
            this.species.remove(s);
        }
    }

    public void sortSpeciesByFitness() {
        for (Species s : this.species) {
            s.sortPlayersByFitness();
        }
        Collections.sort(this.species, Comparator.comparing(Species::getBenchmarkFitness).reversed());
    }

    public void nextGen() {
        List<Bird> children = new ArrayList<>();

        for (Species s : this.species) {
            children.add(s.getChampion());
        }

        int childrenPerSpecies = (int) Math.floor((this.size - this.species.size()) / this.species.size());
        for (Species s : this.species) {
            for (int i = 0; i < childrenPerSpecies; i++) {
                children.add(s.offspring());
            }
        }
        while (children.size() < this.size) {
            children.add(this.species.get(0).offspring());
        }
        this.players = new ArrayList<>();
        for (Bird child : children) {
            this.players.add(child);
        }
        this.generation++;
    }

    public boolean extinct() {
        boolean extinct = true;
        for (Bird p : players) {
            if (p.IsAlive) {
                extinct = false;
                break;
            }
        }
        return extinct;
    }

}
