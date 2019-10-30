import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private static List<Test> tests = new ArrayList<>();
    private static Map<Double, Training> map;

    private String answer, assumption;
    private List<Double> coords = new ArrayList<>();

    public Test(String answer, List<Double> coords) {
        this.answer = answer;
        this.coords = coords;
    }

    public Test(List<Double> coords) {
        this.coords = coords;
        answer = null;
    }

    public static void classifyAll() {
        for (Test test : tests) {
            test.classify();
        }
        printResults();
    }

    public void classify() {
        List<Tuple> tuples = new ArrayList<>();
        for (Training t : Training.getTrainings()) {
            tuples.add(new Tuple(t.calcDistance(this.coords), t));
            Collections.sort(tuples);
        }
        this.assumption = findMode(tuples.stream().limit(Training.getK()).map(tuple -> tuple.getTraining().getName()).collect(Collectors.toList()));
    }

    public static void fillTests(String file) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split(",");
                List<Double> list = new ArrayList<>();
                for (int i = 0; i < strings.length - 1; i++) {
                    list.add(Double.parseDouble(strings[i]));
                }
                try {
                    double coordinate = Double.parseDouble(strings[strings.length - 1]);
                    list.add(coordinate);
                    tests.add(new Test(list));
                } catch (NumberFormatException e) {
                    tests.add(new Test(strings[strings.length - 1], list));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String findMode(List<String> list) {
        int maxReps = 0, reps = 0;
        String mode = null;
        for (String i : list) {
            for (String j : list) {
                if (i.equals(j)) {
                    ++reps;
                }
            }
            if (reps > maxReps) {
                mode = i;
                maxReps = reps;
                reps = 0;
            } else {
                reps = 0;
            }
        }
        return mode;
    }

    private static void printResults() {
        double correct = 0;
        int all = 0;
        for (Test test : tests) {
            ++all;
            if (test.answer == null) {
                --all;
                System.out.println(test);
                continue;
            } else if (test.answer.equals(test.assumption)) {
                ++correct;
            }
            System.out.println(test);
        }
        System.out.println("The accuracy is " + correct / all * 100);
    }

    public String getAssumption() {
        return assumption;
    }

    @Override
    public String toString() {
        return String.format("%s Answer: %s Assumption %s%n", coords, answer, assumption);
    }

}
