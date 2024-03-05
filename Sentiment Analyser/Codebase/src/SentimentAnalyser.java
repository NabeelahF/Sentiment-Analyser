import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SentimentAnalyser {

    private static MyHashMap<String, Integer> wordSentimentMap = new MyHashMap<>();

    private static String cleanText(String text) {
        return text.replaceAll("[^\\w\\s]", "").toLowerCase();
    }

    private static void buildSentimentMap(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                String text = parts[0];
                String sentiment = parts[1];

                text = cleanText(text);
                String[] words = text.split(" ");

                for (String word : words) {
                    wordSentimentMap.put(word, sentimentToValue(sentiment));
                }
            }
        }
    }

    private static int sentimentToValue(String sentiment) {
        if ("positive".equals(sentiment)) {
            return 1;
        } else if ("negative".equals(sentiment)) {
            return -1;
        } else {
            return 0; // Neutral
        }
    }

    private static int classifySentiment(String inputText) {
        inputText = cleanText(inputText);
        String[] words = inputText.split(" ");

        int overallSentiment = 0;

        for (String word : words) {
            Integer sentimentValue = wordSentimentMap.get(word);
            if (sentimentValue != null) {
                overallSentiment += sentimentValue;
            }
        }

        return overallSentiment;
    }

    public static void main(String[] args) {
        String filePath = "C:\\test\\sentiment_dataset.txt";
        try {
            buildSentimentMap(filePath);

            // Take input text from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a sentence for sentiment analysis: ");
            String inputText = scanner.nextLine();

            // Classify sentiment for the user input
            int sentiment = classifySentiment(inputText);

            if (sentiment > 0) {
                System.out.println("Predicted sentiment: positive");
            } else if (sentiment < 0) {
                System.out.println("Predicted sentiment: negative");
            } else {
                System.out.println("Predicted sentiment: neutral");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
