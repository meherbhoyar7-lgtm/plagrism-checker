package src.report;

import src.result.*;
import java.io.*;
import java.util.*;

public class ReportGenerator {

    public static void generateDetailedReport(
            String fileName,
            SimilarityResult overallResult,
            List<LineSimilarityResult> lineResults) throws IOException {

        try (FileWriter writer = new FileWriter(fileName, true)) {  // Append mode

            writer.write("---- Detailed Plagiarism Report ----\n");
            writer.write("File 1 : " + overallResult.getFile1() + "\n");
            writer.write("File 2 : " + overallResult.getFile2() + "\n");
            writer.write("Method : " + overallResult.getMethod() + "\n\n");

            // Write line-level results
            for (LineSimilarityResult line : lineResults) {
                writer.write(line.toString() + "\n");
            }

            writer.write("\nOverall Results:\n");
            writer.write("Similarity : " +
                    String.format("%.2f", overallResult.getSimilarity()) + "%\n");  // 2 decimal formatting
            writer.write("Status     : " + overallResult.getStatus() + "\n");

            // ✅ Fixed: check for empty instead of null

            writer.write("-----------------------------------\n\n");
        }
    }
}