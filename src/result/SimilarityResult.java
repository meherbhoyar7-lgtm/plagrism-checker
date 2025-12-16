package src.result;

public class SimilarityResult {

    private String file1;
    private String file2;
    private String method;
    private double similarity;

    public SimilarityResult(String file1, String file2, String method, double similarity) {
        this.file1 = file1;
        this.file2 = file2;
        this.method = method;
        this.similarity = similarity;
    }

    public String getFile1() {
        return file1;
    }

    public String getFile2() {
        return file2;
    }

    public String getMethod() {
        return method;
    }

    public double getSimilarity() {
        return similarity;
    }
    public String getStatus() {
        if (similarity >= 70)
            return "High Plagiarism";
        else if (similarity >= 40)
            return "Medium Plagiarism";
        else
            return "Low Plagiarism";}

    @Override
    public String toString() {
        return "File1: " + file1 + "\n" +
                ", File2: " + file2 +  "\n" +
                ", Method: " + method +  "\n" +
                ", Similarity: " + String.format("%.2f", similarity) + "%" + "\n" +
                ", Status : " + getStatus();
    }
}