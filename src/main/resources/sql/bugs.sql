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