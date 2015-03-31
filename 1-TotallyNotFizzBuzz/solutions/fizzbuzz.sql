-- Postgres 9.3

CREATE OR REPLACE VIEW fizzbuzz AS
    SELECT
        s.n AS n,
        GREATEST(
            s.n::text,
            TRIM('0' FROM (SUBSTRING(((s.n % 3 * 10000)::text || 'Fizz') FROM 2 FOR 4) || SUBSTRING(((s.n % 5 * 10000)::text || 'Buzz') FROM 2 FOR 4)
        ))) AS result
    FROM
        generate_series(1, 100) AS s(n);

-- SELECT * FROM fizzbuzz;
-- SELECT * FROM fizzbuzz WHERE n % 3 = 0;
