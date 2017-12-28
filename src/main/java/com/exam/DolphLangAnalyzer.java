package com.exam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyses dolphin words and convert them to english.
 * Accepts input using the following format:
 * "eeeehk! ehhhk ekhhh ehhhk" "ehhhhk#@#$%thisisawonderfulworld"
 * e.g.
 *
 * java DolphLangAnalyzer "eeeehk! ehhhk ekhhh ehhhk" "ehhhhk#@#$%thisisawonderfulworld"
 * java com/exam/DolphLangAnalyzer
 *
 * Note: You might need to run the the java command under 'src/main/java' directory.
 *
 * @author rburawes
 */
public class DolphLangAnalyzer {


    public static void main(String... args) {
        DolphLangAnalyzer dl = new DolphLangAnalyzer();
        if (args.length > 0) {
            int cnt = 1;
            for (String input : args) {
                String translated = dl.findAndStrip(input);
                if (translated.equals(input) || translated.isEmpty()) {
                    System.out.println(String.format("Unable to decode Dolphin's message #%s.", cnt));
                } else {
                	System.out.println(String.format("Message[%s] : %s", cnt, translated));
                }
                cnt++;
            }
        } else {
            System.out.println("There is nothing to translate.");
        }
    }

    /**
     * Loads the collection of known Dolphin words
     * and their english equivalent.
     */
    private Map<String, String> loadDolphinWordRecords() {
        Map<String, String> soundMeaning = new HashMap<String, String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("com/exam/dolphin-file.txt"));
            String line = br.readLine();
            while (line != null) {
                line = br.readLine();
                if (line != null && line.length() > 0) {
                    String[] arr = line.split(",");
                    soundMeaning.put(arr[0], arr[1]);
                }
            }
        } catch (IOException e) {
            System.out.print("Unable to load dolphin words data.");
        }
        return soundMeaning;
    }

    /**
     * Find the english meaning of the read Dolphin word using the
     * the loaded collection.
     */
    public String findAndStrip(String input) {
        if (input == null || input.isEmpty()){
            return "";
        }
        Pattern pattern = null;
        Matcher matcher = null;
        Map<Integer, String> relevantWords = new TreeMap<>();
        StringBuffer translation = new StringBuffer("");
        Map<String, String> wordRecords = loadDolphinWordRecords();
        if (wordRecords != null && wordRecords.size() > 0) {
            for (Map.Entry<String, String> entry : loadDolphinWordRecords().entrySet()) {
                String regex = entry.getKey();
                pattern = pattern.compile(regex);
                matcher = pattern.matcher(input);
                while (matcher.find()) {
                    relevantWords.put(matcher.start(), entry.getValue());
                }

            }
            for (Map.Entry<Integer, String> data : relevantWords.entrySet()) {
                translation.append(data.getValue()).append(" ");
            }
        }
        return translation.toString().trim();
    }

    
}
