package com.exam;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Used to find the most used phrases from the input {@link File}.
 */
public class Phrases {

    private static final Logger logger = Logger.getLogger(Phrases.class.getName());

    /**
     * The maximum number of phrases to be utilised.
     * Will be used if the limit is not provided
     */
    private static final int defaultLimit = 100000;

    public static void main(String... args) {

        InputStream inputStream = Phrases.class.getResourceAsStream("phrases.txt");
        System.out.println("Top phrases from the file: " + getTopPhrases(inputStream, defaultLimit));

    }

    /**
     * Retunrs the collection of phrases the will be utilised.
     * Phrase selection will be based on the number of times the
     * occurred in the file.
     * @param inputStream
     * @return
     */
    private static Map<String, Integer> getTopPhrases(InputStream inputStream, int limit) {

        //Create a map, where the key is the phrase,
        //and the value is the number of times phrase occurred in the file.
        Map<String, Integer> topPhrases = new LinkedHashMap<>();

        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            //Read every line of the file.
            while( (line=bufferedReader.readLine()) != null ) {

                //Split the line to get the phrases.
                String[] linePhrases = line.split("\\|");

                //Read every phrase.
                for (String phrase : linePhrases){

                    //Check if the phrase is already in the collection.
                    //If true, then increment the value by 1. Else add the phrase
                    //as the new entry to the collection.
                    if (topPhrases.containsKey(phrase)) {
                        topPhrases.put(phrase, topPhrases.get(phrase).intValue() + 1);
                    } else {
                        topPhrases.put(phrase, 1);
                    }
                }
            }

            //Sort the collection by Map value.
            //Limit the collection to 100000.
            topPhrases = topPhrases.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1,v2)->v1, LinkedHashMap::new));

        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }

        return topPhrases;
    }
}