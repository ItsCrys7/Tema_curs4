import java.io.*;
import java.util.*;

class Interval {
    private Double lowerLimit;
    private Double upperLimit;
    private int totalNumbersTested;
    private int numbersContained;

    public Interval(Double lowerLimit, Double upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.totalNumbersTested = 0;
        this.numbersContained = 0;
    }

    public void testNumber(Double number) {
        totalNumbersTested++;
        if (number >= lowerLimit && number <= upperLimit) {
            numbersContained++;
        }
    }

    public void writeResults(PrintWriter writer) {
        double percentage = (double) numbersContained / totalNumbersTested * 100;
        writer.println("Interval: [" + lowerLimit + ", " + upperLimit + "], Percentage: " + String.format("%.2f", percentage) + "%");
    }
}

public class IntervalStatistics {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduceți calea către fișierul 'intervale.dat': ");
            String intervalsFilePath = scanner.nextLine();
            System.out.print("Introduceți calea către fișierul 'numere.dat': ");
            String numbersFilePath = scanner.nextLine();

            Set<Double> uniqueNumbers = new HashSet<>();
            try (BufferedReader numbersReader = new BufferedReader(new FileReader(numbersFilePath))) {
                String line;
                while ((line = numbersReader.readLine()) != null) {
                    String[] numberStrings = line.split("\\s+");
                    for (String numberString : numberStrings) {
                        uniqueNumbers.add(Double.parseDouble(numberString));
                    }
                }
            }

            try (BufferedReader intervalsReader = new BufferedReader(new FileReader(intervalsFilePath))) {
                String line;
                while ((line = intervalsReader.readLine()) != null) {
                    String[] intervalStrings = line.split("[\\[\\],]");
                    double lowerBound = Double.parseDouble(intervalStrings[1]);
                    double upperBound = Double.parseDouble(intervalStrings[2]);
                    Interval interval = new Interval(lowerBound, upperBound);
                    for (Double number : uniqueNumbers) {
                        interval.testNumber(number);
                    }
                    interval.writeResults(new PrintWriter(new FileWriter("statistica.dat", true)));
                }
            }
            System.out.println("Statistici scrise în fișierul 'statistica.dat'.");
        } catch (IOException e) {
            System.out.println("Eroare la citirea sau scrierea fișierelor: " + e.getMessage());
        }
    }
}
