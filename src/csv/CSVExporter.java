package src.csv;

import src.result.SimilarityResult;
import java.io.*;

public class CSVExporter {

    public static void writeResult(String csvFile, SimilarityResult result) {

        File file = new File(csvFile);
        boolean isNewFile = !file.exists() || file.length() == 0;

        try (FileWriter writer = new FileWriter(file, true)) {
            if (isNewFile) {
                writer.append("File1,File2,Method,Similarity(%),Status,Lines,Words\n");
            }
            writer.append(result.getFile1()).append(",");
            writer.append(result.getFile2()).append(",");
            writer.append(result.getMethod()).append(",");
            writer.append(String.format("%.2f", result.getSimilarity())).append(",");
            writer.append(result.getStatus()).append(",");

            writer.append(result.getPlagiarizedLines() != null ? result.getPlagiarizedLines().toString() : "")
                    .append(",");
            writer.append(result.getPlagiarizedWords() != null ? result.getPlagiarizedWords().toString() : "")
                    .append("\n");

        } catch (IOException e) {
            System.out.println("CSV Write Error: " + e.getMessage());
        }
    }
}