package src.result;

import java.util.*;

public class SimilarityResult {

    private static final double HIGH_THRESHOLD = 70.0;
    private static final double MEDIUM_THRESHOLD = 40.0;

    private final String file1;
    private final String file2;
    private final String method;
    private final double similarity;

    private final Set<String> commonWords;
    private final List<Integer> plagiarizedLines;
    private final List<String> plagiarizedWords;
    private final List<LineSimilarityResult> lineResults; // NEW

    public SimilarityResult(String file1, String file2,
                            String method, double similarity) {
        this(file1, file2, method, similarity,
                Collections.emptySet(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
    }

    public SimilarityResult(String file1, String file2,
                            String method, double similarity,
                            Set<String> commonWords) {
        this(file1, file2, method, similarity,
                commonWords,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
    }

    public SimilarityResult(String file1, String file2,
                            String method, double similarity,
                            List<Integer> plagiarizedLines,
                            List<String> plagiarizedWords,
                            List<LineSimilarityResult> lineResults) {
        this(file1, file2, method, similarity,
                Collections.emptySet(),
                plagiarizedLines,
                plagiarizedWords,
                lineResults);
    }

    private SimilarityResult(String file1, String file2,
                             String method, double similarity,
                             Set<String> commonWords,
                             List<Integer> plagiarizedLines,
                             List<String> plagiarizedWords,
                             List<LineSimilarityResult> lineResults) {
        this.file1 = file1;
        this.file2 = file2;
        this.method = method;
        this.similarity = similarity;
        this.commonWords = Collections.unmodifiableSet(new HashSet<>(commonWords));
        this.plagiarizedLines = Collections.unmodifiableList(new ArrayList<>(plagiarizedLines));
        this.plagiarizedWords = Collections.unmodifiableList(new ArrayList<>(plagiarizedWords));
        this.lineResults = Collections.unmodifiableList(new ArrayList<>(lineResults));
    }

    public String getFile1() { return file1; }
    public String getFile2() { return file2; }
    public String getMethod() { return method; }
    public double getSimilarity() { return similarity; }
    public Set<String> getCommonWords() { return commonWords; }
    public List<Integer> getPlagiarizedLines() { return plagiarizedLines; }
    public List<String> getPlagiarizedWords() { return plagiarizedWords; }
    public List<LineSimilarityResult> getLineResults() { return lineResults; }

    public String getStatus() {
        if (similarity >= HIGH_THRESHOLD) return "High Plagiarism";
        else if (similarity >= MEDIUM_THRESHOLD) return "Medium Plagiarism";
        else return "Low Plagiarism";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Files Compared: ").append(file1)
                .append(" vs ").append(file2).append("\n")
                .append("Method: ").append(method).append("\n")
                .append("Similarity: ")
                .append(String.format("%.2f", similarity)).append("%\n")
                .append("Status: ").append(getStatus()).append("\n");

        if (!commonWords.isEmpty())
            sb.append("Common Words: ").append(commonWords).append("\n");

        if (!plagiarizedLines.isEmpty())
            sb.append("Plagiarized Lines: ").append(plagiarizedLines).append("\n");

        if (!plagiarizedWords.isEmpty())
            sb.append("Plagiarized Words: ").append(plagiarizedWords).append("\n");

        if (!lineResults.isEmpty()) {
            sb.append("\nLine Results:\n");
            for (LineSimilarityResult line : lineResults) {
                sb.append(line.toString()).append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimilarityResult)) return false;
        SimilarityResult that = (SimilarityResult) o;
        return Double.compare(that.similarity, similarity) == 0 &&
                Objects.equals(file1, that.file1) &&
                Objects.equals(file2, that.file2) &&
                Objects.equals(method, that.method) &&
                Objects.equals(commonWords, that.commonWords) &&
                Objects.equals(plagiarizedLines, that.plagiarizedLines) &&
                Objects.equals(plagiarizedWords, that.plagiarizedWords) &&
                Objects.equals(lineResults, that.lineResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file1, file2, method, similarity,
                commonWords, plagiarizedLines, plagiarizedWords, lineResults);
    }
}