package src.result;

import java.util.Objects;

public class LineSimilarityResult {

    private final int lineNumber;
    private final int wordsFile1;
    private final int wordsFile2;
    private final int commonWords;
    private final double similarity;

    public LineSimilarityResult(int lineNumber, int wordsFile1,
                                int wordsFile2, int commonWords,
                                double similarity) {
        this.lineNumber = lineNumber;
        this.wordsFile1 = wordsFile1;
        this.wordsFile2 = wordsFile2;
        this.commonWords = commonWords;
        this.similarity = similarity;
    }

    public int getLineNumber() { return lineNumber; }
    public int getWordsFile1() { return wordsFile1; }
    public int getWordsFile2() { return wordsFile2; }
    public int getCommonWords() { return commonWords; }
    public double getSimilarity() { return similarity; }

    public String getStatus() {
        if (similarity >= 70.0) return "High Similarity";
        else if (similarity >= 40.0) return "Medium Similarity";
        else return "Low Similarity";
    }

    @Override
    public String toString() {
        return String.format(
                "Line %d:%nWords in File1 : %d%nWords in File2 : %d%nCommon Words  : %d%nSimilarity    : %.2f%%%nStatus        : %s%n",
                lineNumber, wordsFile1, wordsFile2, commonWords, similarity, getStatus()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineSimilarityResult)) return false;
        LineSimilarityResult that = (LineSimilarityResult) o;
        return lineNumber == that.lineNumber &&
                wordsFile1 == that.wordsFile1 &&
                wordsFile2 == that.wordsFile2 &&
                commonWords == that.commonWords &&
                Double.compare(that.similarity, similarity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, wordsFile1, wordsFile2, commonWords, similarity);
    }
}