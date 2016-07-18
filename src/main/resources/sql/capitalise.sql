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