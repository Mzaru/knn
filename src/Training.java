import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Training {

    private static int k;
    private static boolean input;
    private static List<Training> trainings = new ArrayList<>();

    private String name;
    private List<Double> coords = new ArrayList<>();

    public static void main(String[] args) {
        //4.7, 3.2, 1.6, 0.2
        takeInput();
        if (!input) {
            Test.classifyAll();
        }
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        rand.nextDouble(1, 100);
    }

    public Training(String name, List<Double> coords) {
        this.name = name;
        this.coords = coords;
    }

    public double calcDistance(List<Double> list) {
        if (list.size() != coords.size()) {
            throw new InvalidNumberOfCoordinatesException("The number of coordinates in the input data should be " + coords.size());
        }
        double distance = 0;
        for (int i = 0; i < coords.size(); i++) {
            distance += Math.pow((coords.get(i) - list.get(i)), 2);
        }
        return distance = Math.sqrt(distance);
    }

    public static void takeInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter K");
        k = in.nextInt();
        in.nextLine();
        while (true) {
            System.out.println("Would you like to enter input manually? Y/N ");
            String line = in.nextLine();
            if (line.equalsIgnoreCase("y")) {
                input = true;
                break;
            } else if (line.equalsIgnoreCase("n")) {
                break;
            }
        }
        fillTrainings("train.txt");
        if (!input) {
            Test.fillTests("test.txt");
        } else {
            int values = trainings.get(0).coords.size();
            System.out.printf("Please enter test data one by one(%d values)%n", values);
            List<Double> input = new ArrayList<>();
            for (int i = 0; i < values; i++) {
                input.add(in.nextDouble());
            }
            Test test = new Test(input);
            test.classify();
            System.out.println("The result is " + test.getAssumption());
        }
    }

    public static void fillTrainings(String file) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(",");
                List<Double> list = new ArrayList<>();
                for (int i = 0; i < strings.length - 1; i++) {
                    list.add(Double.parseDouble(strings[i]));
                }
                trainings.add(new Training(strings[strings.length - 1], list));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of trainings is " + trainings.size());
    }

    public static List<Training> getTrainings() {
        return trainings;
    }

    public static int getK() {
        return k;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s %s%n", name, coords);
    }
}
