package mszzsm.floppybird.game.bot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Species {
    private List<Bird> players;
    private double average_fitness;
    private double threshold;
    private Bird benchmark_player;
    private Brain benchmark_brain;
    private Bird champion;
    private int staleness;

    public Species(Bird player) {
        this.players = new ArrayList<>();
        this.average_fitness = 0;
        this.threshold = 1.2;
        this.players.add(player);
        this.benchmark_player = player;
        this.benchmark_brain = player.getBrain().clone();
        this.champion = player.clone();
        this.staleness = 0;
    }

    public boolean similarity(Brain brain) {
        double similarity = weightDifference(this.benchmark_brain, brain);
        return this.threshold > similarity;
    }

    private static double weightDifference(Brain brain1, Brain brain2) {
        double total_weight_difference = 0;
        for (int i = 0; i < brain1.getConnections().size(); i++) {
            for (int j = 0; j < brain2.getConnections().size(); j++) {
                if (i == j) {
                    total_weight_difference += Math.abs(brain1.getConnections().get(i).getWeight() -
                            brain2.getConnections().get(j).getWeight());
                }
            }
        }
        return total_weight_difference;
    }

    public void addToSpecies(Bird player) {
        this.players.add(player);
    }

    public void sortPlayersByFitness() {
        this.players.sort(Comparator.comparing(Bird::getFitness).reversed());
        if (this.players.get(0).getFitness() > this.benchmark_player.getFitness()) {
            this.staleness = 0;
            this.benchmark_player = this.players.get(0);
            this.benchmark_brain = this.players.get(0).getBrain().clone();
            this.champion = this.players.get(0).clone();
        } else {
            this.staleness += 1;
        }
    }

    public void calculateAverageFitness() {
        double total_fitness = 0;
        for (Bird p : this.players) {
            total_fitness += p.getFitness();
        }
        if (!this.players.isEmpty()) {
            this.average_fitness = total_fitness / this.players.size();
        } else {
            this.average_fitness = 0;
        }
    }

    public Bird offspring() {
        Random random = new Random();
        Bird baby = this.players.get(random.nextInt(this.players.size())).clone();
        baby.getBrain().mutate();
        return baby;
    }
    public int getStaleness()
    {
        return staleness;
    }
    public void setStaleness(int staleness){
        this.staleness = staleness;
    }
    public Bird getChampion(){
        return champion;
    }
    public List<Bird> getPlayers(){
        return players;
    }
    public void setPlayers(List<Bird> list){
        players = list;
    }
    public double getBenchmarkFitness(){
        return benchmark_player.getFitness();
    }
}
