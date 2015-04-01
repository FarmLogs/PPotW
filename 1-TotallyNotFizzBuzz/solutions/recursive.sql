-- Tested on SQLite 3.8.5

WITH RECURSIVE cnt(n) AS (
                          VALUES(1)
                          UNION ALL
                          SELECT n + 1
                          FROM cnt
                          WHERE n < 100)
SELECT t
FROM
  (SELECT n,
          'FizzBuzz' AS t
   FROM cnt AS fizzbuzz
   WHERE NOT n % 3
     AND NOT n % 5
   UNION SELECT n,
                'Fizz' AS t
   FROM cnt AS fizz
   WHERE NOT n % 3
     AND n % 5
   UNION SELECT n,
                'Buzz' AS t
   FROM cnt AS buzz
   WHERE NOT n % 5
     AND n % 3
   UNION SELECT n,
                n AS t
   FROM cnt AS vals
   WHERE n % 5
     AND n % 3)
ORDER BY n ASC;