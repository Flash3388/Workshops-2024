package question3;

public class Question3 {

    // this program implements finding the largest word in a sentence.
    // it is made of two parts
    // - the first part takes the sentence and splits it into words. each word has a whitespace between it
    //      and the next word, that is how it splits the words.
    // - the second part goes over all the words and finds the largest one
    //
    // main has an example of running it on a sentence. But there are problems

    public static void main(String[] args) {
        String sentence = "Hey kid do I have your attention I know the way you've been living";

        String[] words = splitIntoWords(sentence);
        String largestWord = findLargestWord(words);

        System.out.println(largestWord);
    }

    private static String findLargestWord(String[] words) {
        String largest = null;
        int largestLength = 0;

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word == null) {
                break;
            }

            int len = word.length();

            if (len > largestLength) {
                largest = word;
                largestLength = len;
            }
        }

        return largest;
    }

    private static String[] splitIntoWords(String sentence) {
        String[] words = new String[100];

        int wordStartIndex = 0;
        int nextWordIndex = 0;

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);

            if (ch == ' ') {
                // end of a word is whitespace
                // collect the entire word from start to here
                String word = sentence.substring(wordStartIndex, i);
                words[nextWordIndex] = word;
                nextWordIndex++;

                wordStartIndex = i + 1;
            }
        }

        String word = sentence.substring(wordStartIndex);
        words[nextWordIndex] = word;

        return words;
    }
}
