# 12 - Free Throws

This problem was adapted from Andrew Gelman's Stan Puzzle 1. I've generated new data that may or may not use a different value for $\theta$.

> Suppose a player is shooting free throws, but rather than recording for each attempt whether it was successful, she or he instead reports the length of her or his streaks of consecutive successes. For the sake of this puzzle, assume the the player makes a sequence of free throw attempts, $z = (z_1, z_2, \ldots)$, assumed to be i.i.d. Bernoulli trials with chance of success $\theta$, until $N$ streaks are recorded. The data recorded is only the length of the streaks, $y = (y_1, \ldots, y_N)$.

> *Puzzle:* Write a ~~Stan~~ program to estimate $p(\theta \| y)$.

> Example:   Suppose a player sets out to record 9 streaks and makes shots

> ~~$z = (0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0)$.~~

$z = (0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0)$.

> This produces the observed data

> ~~$N = 4$~~

$N = 9$

> ~~$y = (3, 1, 2, 5)$.~~

$y = (8, 3, 3, 5, 4, 6, 1, 8, 1)$.

> *Hint:*   Feel free to assume a uniform prior $p(\theta)$. The trick is working out the likelihood $p(y \| \theta, N)$, after which it is trivial to use Stan to compute $p(\theta \| y)$ via sampling.

> *Another Hint:*   Marginalize the unobserved failures (0 values) out of $z$. This is non-trivial because we don’t know the length of $z$.