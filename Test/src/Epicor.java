import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;



public class Epicor {

    // Stopwords (articles, prepositions, pronouns, conjunctions, modal verbs)
    // + common contraction fragments to reduce noise (t, d, ll, re, ve, m)
    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "a","an","the",
        "in","on","at","of","to","from","by","for","with","about","against",
        "between","into","through","during","before","after","above","below","over","under",
        "and","or","but","if","because","as","until","while","although","though",
        "he","she","it","they","them","we","you","i","me","my","mine","your","yours",
        "his","her","hers","its","our","ours","their","theirs","this","that","these","those",
        "who","whom","which",
        "is","was","are","were","be","been","being","am","do","did","does","doing",
        "have","has","had","having","will","would","can","could","shall","should","may","might","must",
        // contraction fragments
        "t","d","ll","re","ve","m"
    ));

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        String urlString = "https://courses.cs.washington.edu/courses/cse390c/22sp/lectures/moby.txt";
        Map<String, Integer> freq = new HashMap<>();
        int totalWords = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // --- FIX 1: normalize apostrophes and remove possessive 's BEFORE tokenizing ---
                String norm = line.toLowerCase(Locale.ROOT)
                                  .replace('’','\'')                          // unify curly apostrophe
                                  .replaceAll("\\b([a-z]+)'s\\b", "$1");     // drop possessive 's (e.g., captain's -> captain)

                // Tokenize on non-letters (spec: words are sequences of letters)
                String[] tokens = norm.split("[^a-z]+");

                for (String word : tokens) {
                    if (word.isEmpty()) continue;

                    // --- FIX 2: ignore single-letter tokens (stops "s" from "it's", also "t" from "don't") ---
                    if (word.length() == 1) continue;

                    // Skip excluded categories
                    if (STOPWORDS.contains(word)) continue;

                    totalWords++;
                    freq.put(word, freq.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading URL: " + e.getMessage());
            return;
        }

        // Sort by frequency descending
        List<Map.Entry<String, Integer>> sorted = freq.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .collect(Collectors.toList());

        TreeSet<String> unique = new TreeSet<>(freq.keySet());

        long seconds = (System.currentTimeMillis() - start) / 1000;

        System.out.println("Total filtered word count: " + totalWords + "\n");
        System.out.println("Top 5 most frequent words:");
        sorted.stream().limit(5).forEach(e ->
            System.out.printf("%s → %d%n", e.getKey(), e.getValue())
        );
        System.out.println("\nFirst 50 unique words (alphabetical):");
        unique.stream().limit(50).forEach(System.out::println);

        System.out.println("\nProcessing time: " + seconds + " seconds");
    }
}

