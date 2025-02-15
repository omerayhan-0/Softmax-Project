//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Arrays;

public class Softmax {

    // Softmax fonksiyonu
    public static double[][] softmax(double[][] scores) {
        int rows = scores.length;
        int cols = scores[0].length;

        // Her satırda en büyük değeri çıkararak stabilite sağla
        for (int i = 0; i < rows; i++) {
            double max = Arrays.stream(scores[i]).max().getAsDouble();
            for (int j = 0; j < cols; j++) {
                scores[i][j] -= max;  // Numerik stabilite sağlamak için max çıkarma
            }
        }

        // Softmax hesaplama
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            double sum = 0.0;
            for (int j = 0; j < cols; j++) {
                result[i][j] = Math.exp(scores[i][j]);
                sum += result[i][j];
            }

            for (int j = 0; j < cols; j++) {
                result[i][j] /= sum;
            }
        }
        return result;
    }

    // Ana metot: programın başlama noktası
    public static void main(String[] args) {
        // 3 bölge için veri
        String[] bolgeler = {"A", "B", "C"};
        String[] kriterler = {"Nüfus", "Ulaşım", "Maliyet", "Çevre", "Fayda"};

        double[][] data = {
                {5000, 7, 200000, 300, 80},  // A
                {10000, 5, 250000, 400, 90}, // B
                {15000, 8, 220000, 350, 85}  // C
        };

        // Maliyeti ve çevre etkisini ters çevir
        for (int i = 0; i < 3; i++) {
            data[i][2] = -data[i][2];  // Maliyet
            data[i][3] = -data[i][3];  // Çevre
        }

        // Verileri 100 ile çarpalım (daha belirgin farklar için)
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] *= 100;
            }
        }
        // Softmax işlemi yapalım
        double[][] normalizedScores = softmax(data);

        // En iyi bölgeyi bulalım
        int bestRegionIndex = 0;
        double maxSum = -Double.MAX_VALUE;
        for (int i = 0; i < normalizedScores.length; i++) {
            double sum = 0.0;
            for (int j = 0; j < normalizedScores[i].length; j++) {
                sum += normalizedScores[i][j];
            }
            if (sum > maxSum) {
                maxSum = sum;
                bestRegionIndex = i;
            }
            System.out.println(bolgeler[i] + ": " + sum);
        }

        System.out.println("\nEn iyi bölge: " + bolgeler[bestRegionIndex]);
    }
}
