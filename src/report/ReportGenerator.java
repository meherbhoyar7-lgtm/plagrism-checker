package src.report;

import src.result.SimilarityResult;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    public static void generateReport(String fileName, SimilarityResult result)
            throws IOException {

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("---- Plagiarism Report ----\n");
            writer.write("File 1     : " + result.getFile1() + "\n");
            writer.write("File 2     : " + result.getFile2() + "\n");
            writer.write("Method     : " + result.getMethod() + "\n");
            writer.write("Similarity : " +
                    String.format("%.2f", result.getSimilarity()) + "%\n");
            writer.write("Status     : " + result.getStatus() + "\n");
            writer.write("----------------------------\n\n");
        }
    }
}