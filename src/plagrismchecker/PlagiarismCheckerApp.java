package src.plagrismchecker;
import java.io.*;
import java.util.*;
import src.result.LineSimilarityResult;
import src.result.SimilarityResult;
import src.csv.CSVExporter;
import src.report.ReportGenerator;

class FileHandler {

    public static String readFile(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(" ");
            }
        }
        return sb.toString().toLowerCase();
    }
    public static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.toLowerCase());
            }
        }
        return lines;
    }
}

abstract class SimilarityChecker {
    public abstract double checkSimilarity(String text1, String text2);

    public static Set<String> getCommonWords(String text1, String text2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(text1.split("\\s+")));
        Set<String> set2 = new HashSet<>(Arrays.asList(text2.split("\\s+")));
        set1.retainAll(set2);
        return set1;
    }
}


class WordOverlapChecker extends SimilarityChecker {
    @Override
    public double checkSimilarity(String text1, String text2) {
        Set<String> words1 = new HashSet<>(Arrays.asList(text1.split("\\s+")));
        Set<String> words2 = new HashSet<>(Arrays.asList(text2.split("\\s+")));

        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        return (double) intersection.size() / Math.max(words1.size(), words2.size()) * 100;
    }
    public static LineSimilarityResult compareLine(
            String line1, String line2, int lineNumber) {

        Set<String> words1 =
                new HashSet<>(Arrays.asList(line1.split("\\s+")));
        Set<String> words2 =
                new HashSet<>(Arrays.asList(line2.split("\\s+")));

        Set<String> common = new HashSet<>(words1);
        common.retainAll(words2);

        double similarity =
                (double) common.size() /
                        Math.max(words1.size(), words2.size()) * 100;

        return new LineSimilarityResult(
                lineNumber,
                words1.size(),
                words2.size(),
                common.size(),
                similarity
        );
    }

}

class CosineSimilarityChecker extends SimilarityChecker {
    @Override
    public double checkSimilarity(String text1, String text2) {
        Map<String, Integer> freq1 = getWordFrequency(text1);
        Map<String, Integer> freq2 = getWordFrequency(text2);

        Set<String> allWords = new HashSet<>();
        allWords.addAll(freq1.keySet());
        allWords.addAll(freq2.keySet());

        double dot = 0.0, mag1 = 0.0, mag2 = 0.0;
        for (String word : allWords) {
            int v1 = freq1.getOrDefault(word, 0);
            int v2 = freq2.getOrDefault(word, 0);
            dot += v1 * v2;
            mag1 += v1 * v1;
            mag2 += v2 * v2;
        }

        return dot / (Math.sqrt(mag1) * Math.sqrt(mag2)) * 100;
    }

    private Map<String, Integer> getWordFrequency(String text) {
        Map<String, Integer> freq = new HashMap<>();
        for (String word : text.split("\\s+")) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        return freq;
    }
}

public class PlagiarismCheckerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Plagiarism Checker Menu ---");
            System.out.println("1. Compare two files (Word Overlap)");
            System.out.println("2. Compare two files (Cosine Similarity)");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1 || choice == 2) {
                try {
                    System.out.print("Enter first file name: ");
                    String file1 = sc.nextLine();
                    System.out.print("Enter second file name: ");
                    String file2 = sc.nextLine();

                    String text1 = FileHandler.readFile(file1);
                    String text2 = FileHandler.readFile(file2);

                    SimilarityChecker checker = (choice == 1) ? new WordOverlapChecker() : new CosineSimilarityChecker();
                    double similarity = checker.checkSimilarity(text1, text2);

                    List<String> lines1 = FileHandler.readLines(file1);
                    List<String> lines2 = FileHandler.readLines(file2);

                    int totalLines = Math.min(lines1.size(), lines2.size());
                    List<LineSimilarityResult> lineResults = new ArrayList<>();

                    for (int i = 0; i < totalLines; i++) {
                        lineResults.add(
                                WordOverlapChecker.compareLine(
                                        lines1.get(i), lines2.get(i), i + 1
                                )
                        );
                    }
                    SimilarityResult result = new SimilarityResult(
                            file1,
                            file2,
                            (choice == 1 ? "Word Overlap" : "Cosine Similarity"),
                            similarity
                    );

                    CSVExporter.writeResult("results.csv", result);
                    SimilarityResult result1 = new SimilarityResult(
                            file1,
                            file2,
                            (choice == 1 ? "Word Overlap" : "Cosine Similarity"),
                            similarity
                    );

                    ReportGenerator.generateDetailedReport(
                            "report.txt",
                            result,
                            lineResults
                    );
                    System.out.println(result1);
                }
                catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
}