public class Tuple implements Comparable<Tuple>{
    private double distance;
    private Training training;

    public Tuple(double distance, Training training) {
        this.distance = distance;
        this.training = training;
    }

    public double getDistance() {
        return distance;
    }

    public Training getTraining() {
        return training;
    }

    @Override
    public int compareTo(Tuple o) {
        return Double.compare(this.distance, o.distance);
    }

    @Override
    public String toString() {
        return String.format("Tuple %f%n", distance);
    }
}
