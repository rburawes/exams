Exams
=====

Collection of classes, sqls and files that were used to answer employment and training questions.
Note that, there are other similar answers around the web to the questions listed here and probably
way better and efficient.

Java
====

Sample Java questions commonly asked during exams or interviews.

**Question 1:**

Write an efficient algorithm to check if a string is a palindrome. A string is a palindrome if the string matches the reverse of string.
Example: 1221 is a palindrome but not 1121.

**Answer:**

```java
package com.exam;

/**
 * Used to check if a word or a phrase reads the same backward as forward.
 */
public class Palindrome {

    private static String[] words = new String[] { "civic", "hello", "hannah", "rotator", "decaffeinated", "dish", "madam", "level", "dish" };

    public static void main(String... args) {
        for (String word : words){
            System.out.printf("%s : %b \n", word, isPalindrome(word));
        }
    }

    /**
     * Checks if the word is a palindrome.
     * @param str
     * @return
     */
    private static boolean isPalindrome(String str) {

        int left = 0;
        int right = str.length() - 1;
        int half = right / 2;

        //Reads the character from left and right simultaneously, then compare.
        for (int i = 0; i < half; i++, left++, right--) {

            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
        }

        return true;
    }
}
```


**Question 2:**

Write an efficient algorithm to find K-complementary pairs in a given array of integers. Given Array A, pair (i, j) is K- complementary if K = A[i] + A[j];

**Answer:**

```java
package com.exam;

/**
 * Used to find the K-com.exam.Complimentary for a given array of integers.
 */
public class Complimentary {

    private static int[] numbers = new int[] { 3, 4, 5, 7, 5, 9, 1, 2 };

    public static void main(String... args) {

        int[][] pairs = getComplimentary(5, numbers);
        System.out.println("com.exam.Complimentary pairs :");

        for (int[] pair : pairs) {
            System.out.printf("{%d,%d} \n", pair[0], pair[1]);
        }

    }

    /**
     * Finds the complimentary pair using the sum and numbers provided.
     * @param sum
     * @param intArray
     * @return
     */
    private static int[][] getComplimentary(int sum, int[] intArray) {

        int arraySize = intArray.length;

        if (intArray == null || arraySize < 2) {
            return new int[0][0];
        }

        int[][] pairs = new int[arraySize][2];
        int actualSize = 0;

        //Loop on the numbers in the array and subtract it with the sum to
        //to find one the pair.
        for (int i = 0; i < arraySize; i++) {

            int num = intArray[i];

            if ( num < sum) {

                int diff = sum - num;

                //Check if the difference is in the array.
                for (int m = 0; m < arraySize; m++) {
                    if (diff == intArray[m]) {
                        int[] pair = new int[] { num, diff };
                        pairs[i] = pair;
                        actualSize++;
                    }
                }
            }
        }

        int[][] finalPairs = new int[actualSize][2];
        int ctr = 0;

        //Remove pairs with zero values.
        for (int[] p : pairs) {

            if (p[0] != 0 & p[1] != 0) {
                finalPairs[ctr] = p;
                ctr++;
            }
        }

        return finalPairs;
    }
}
```


**Question 3:**

Given a large file that does not fit in memory (say 10GB), find the top 100000 most frequent phrases. The file has 50 phrases per line separated by a pipe (|). Assume that the phrases do not contain pipe.
Example line may look like: Foobar Candy | Olympics 2012 | PGA | CNET | Microsoft Bing .... The above line has 5 phrases in visible region.

**Answer:**

```java
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
```


MySQL
=====

Sample MySQL/SQL questions or assignments commonly given to developers.


**Question 1:**

Write a query to rank order the following table in MySQL by votes, display the rank as one of the columns.
CREATE TABLE votes ( name CHAR(10), votes INT ); INSERT INTO votes VALUES ('Smith',10), ('Jones',15), ('White',20), ('Black',40), ('Green',50), ('Brown',20);

**Answer:**

```sql
SELECT @voteRank := @voteRank + 1 as rank, name, votes FROM votes v, (SELECT @voteRank := 0 ) vr order by votes desc;
```


**Question 2:**

Write a function to capitalize the first letter of a word in a given string; Example: initcap(UNITED states Of AmERIca ) = United States Of America

**Answer:**

```sql
DROP TABLE IF EXISTS country; 
CREATE TABLE country (name VARCHAR(250));
INSERT INTO country (name) VALUES ("UNITED states Of AmERIca");
INSERT INTO country (name) VALUES ("repuBliC of THE phIlippINES");
INSERT INTO country (name) VALUES ("RepUBLIC oF KOREA");

-- Capitalises the first letter of every word.
DROP FUNCTION IF EXISTS CAP_FIRST_CHAR;

DELIMITER $$

CREATE FUNCTION CAP_FIRST_CHAR(input VARCHAR(250))
  RETURNS VARCHAR(250) deterministic

BEGIN

  DECLARE output VARCHAR(250); 	-- Holds the final capitalised value.
  DECLARE prevChar VARCHAR(1); 	-- Holds the previous character in the loop.
  DECLARE curChar VARCHAR(1);  	-- Holds the current character in the loop.
  DECLARE ctr INT;				-- The index of the character in the loop.
  
  SET output = UCASE(LEFT(input, 1));
  SET ctr=1;
  SET prevChar = SUBSTRING(input, ctr, 1);
  SET curChar = "";
  
  elementLoop: LOOP
	SET ctr = ctr + 1;
    SET curChar = SUBSTRING(input, ctr, 1);
    
    IF prevChar = " " THEN
      SET output = CONCAT(output,UCASE(curChar));
    ELSE
      SET output = CONCAT(output,LCASE(curChar));
    END IF;
    
    SET prevChar = curChar;

    IF (ctr < LENGTH(input)) THEN
      ITERATE elementLoop;
    END IF;

    LEAVE elementLoop;
    
  END LOOP elementLoop;
  
  RETURN output;
END;
$$
DELIMITER ;

SELECT CAP_FIRST_CHAR(name) FROM country;
```


**Question 3:**

Write a procedure in MySQL to split a column into rows using a delimiter.
CREATE TABLE sometbl ( ID INT, NAME VARCHAR(50) );
INSERT INTO sometbl VALUES (1, 'Smith'), (2, 'Julio|Jones|Falcons'), (3, 'White|Snow'), (4, 'Paint|It|Red'), (5, 'Green|Lantern'), (6, 'Brown|bag'); For (2), example rows would look like >> “3, white”, “3, Snow” ...

**Answer:**

```sql
DROP TABLE IF EXISTS sometbl;
CREATE TABLE sometbl ( id INT, name VARCHAR(50) );
INSERT INTO sometbl VALUES (1, 'Smith'), (2, 'Julio|Jones|Falcons'), (3, 'White|Snow'), (4, 'Paint|It|Red'), (5, 'Green|Lantern'), (6, 'Brown|bag');

DROP PROCEDURE IF EXISTS splitted_table;

DELIMITER $$

-- Splits column value and add each splitted element as new
-- a new record to the table.
CREATE PROCEDURE splitted_table(delimeter VARCHAR(255))

BEGIN

	DECLARE id INT DEFAULT 0;
    DECLARE name VARCHAR(250);
    DECLARE occur INT DEFAULT 0;
	DECLARE i INT DEFAULT 0;
    DECLARE pipe INT DEFAULT 0;
    DECLARE splitted_name VARCHAR(50);
    DECLARE done INT DEFAULT 0;
	DECLARE sourceTable CURSOR FOR SELECT sometbl.id, sometbl.name FROM sometbl WHERE sometbl.name != '';
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
    -- Table that will handle the records for the splitted columns.
    DROP TABLE IF EXISTS new_sometbl;
    CREATE TABLE new_sometbl(id INT, name VARCHAR(250));

    OPEN sourceTable;
      
      read_loop: LOOP -- Read all the records from sometbl and split columns with pipes.
        
        FETCH sourceTable INTO id, name;
        
        IF done THEN
			LEAVE read_loop;
        END IF;
		
        SET pipe = LOCATE(delimeter, name);
        
        IF pipe > 0 THEN -- If the name contains pipe '|' then split and add as a new record.
			
            SET occur = (SELECT LENGTH(name) - LENGTH(REPLACE(name, delimeter, '')) + 1 );
            SET i = 1;
        
			WHILE i <= occur DO
				
				SET splitted_name = (SELECT REPLACE(SUBSTRING(SUBSTRING_INDEX(name, delimeter, i), LENGTH(SUBSTRING_INDEX(name, delimeter, i - 1)) + 1), delimeter, ''));
				INSERT INTO new_sometbl(id, name) VALUES (id, splitted_name);
                SET i = i + 1;
                
			END WHILE;
        ELSE -- The name has no piple so add it directly to the new table.
			INSERT INTO new_sometbl VALUES (id, name);
		END IF;
                
      END LOOP;

      SELECT * FROM new_sometbl;
      
	CLOSE sourceTable;
END;
$$

DELIMITER ;

CALL splitted_table('|');
```


**Question 4:**

I have a table for bugs from a bug tracking software; let’s call the table “bugs”. The table has four columns (id, open_date, close_date, severity). 
On any given day a bug is open if the open_date is on or before that day and close_date is after that day. 
For example, a bug is open on “2012-01-01”, if it’s created on or before “2012-01-01” and closed on or after “2012-01-02”. I want a SQL to show number of bugs open for a range of dates.

**Answer:**

```sql
DROP TABLE IF EXISTS bugs;
CREATE TABLE bugs (id INT, severity INT, open_date DATE, close_date DATE);

INSERT INTO bugs VALUES(1, 1, STR_TO_DATE('2014-11-29', '%Y-%m-%d'), STR_TO_DATE('2014-11-30', '%Y-%m-%d'));
INSERT INTO bugs VALUES(2, 1, STR_TO_DATE('2015-11-10', '%Y-%m-%d'), STR_TO_DATE('2015-11-11', '%Y-%m-%d'));
INSERT INTO bugs VALUES(3, 1, STR_TO_DATE('2015-11-13', '%Y-%m-%d'), STR_TO_DATE('2015-11-13', '%Y-%m-%d'));
INSERT INTO bugs VALUES(4, 1, STR_TO_DATE('2015-11-14', '%Y-%m-%d'), STR_TO_DATE('2015-11-15', '%Y-%m-%d'));
INSERT INTO bugs VALUES(5, 1, STR_TO_DATE('2015-11-15', '%Y-%m-%d'), STR_TO_DATE('2015-11-16', '%Y-%m-%d'));
INSERT INTO bugs VALUES(5, 1, STR_TO_DATE('2015-11-16', '%Y-%m-%d'), STR_TO_DATE('2015-11-16', '%Y-%m-%d'));
  
DROP PROCEDURE IF EXISTS search_bugs;
DELIMITER $$

CREATE PROCEDURE search_bugs(startDate DATE, endDate DATE)

BEGIN

  DECLARE output VARCHAR(750);
  DECLARE targetDate DATE;
  
  SET output = '';
  SET targetDate = startDate;
  
  iterLoop: LOOP
  
     IF targetDate = startDate THEN
     
       SET output = CONCAT(output, ' SELECT * FROM bugs WHERE open_date <= DATE(\'',targetDate,'\') AND close_date = DATE(\'',targetDate,'\') UNION ALL');
     
     ELSEIF targetDate = endDate THEN
     
       SET output = CONCAT(output, ' SELECT * FROM bugs WHERE open_date <= DATE(\'',targetDate,'\') AND close_date >= DATE(\'',targetDate,'\') UNION ALL');
     
     ELSE
     
       SET output = CONCAT(output, ' SELECT * FROM bugs WHERE open_date <= DATE(\'',targetDate,'\') AND close_date = DATE(\'',targetDate,'\') UNION ALL');
     
     END IF;

     SET targetDate = DATE_ADD(targetDate, INTERVAL 1 DAY);
     
     IF targetDate <= endDate THEN
     
        ITERATE iterLoop;
     
     END IF;
     
     LEAVE iterLoop;
   
   END LOOP iterLoop;

   SET output = LEFT(output, LENGTH(output)-LENGTH('UNION ALL'));

   PREPARE  statement FROM @output;
   EXECUTE statement;
   
END;
$$

DELIMITER ;

CALL search_bugs(STR_TO_DATE('2015-11-10', '%Y-%m-%d'), STR_TO_DATE('2015-11-03', '%Y-%m-%d'));
```

<b>Note(s):</b>
* Pieces of codes are based from different tutorial sites.
 
<b>File locations:</b>
* Java location: /src/main/java
* SQL files: /src/main/resources/sql
