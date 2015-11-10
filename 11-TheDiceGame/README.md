# A Simple Dice Game
Lets say we have 4 3 sided dice like so.

![Image of illusive 3-sided die](/11-TheDiceGame/d3.JPG)

Now lets put alphabetic letters on each dice. 1 letter per side.

 - Die 1: A, B, C
 - Die 2: F, O, P
 - Die 3: F, C, K
 - Die 4: O, R, D

Now let's roll all of these dice together at once. Say we get 1:B, 2:O, 3:K, and 4:O. Using these letters, we can spell out B-O-O-K. Let's roll the dice again. We get 1:C, 2:P, 3,K, and 4:R. These are all consonants and cannot spell out a 4 letter word. The challenge for this week is to design 4 of these 3 sided dice so that you can maximize your chances at spelling out a dictionary word when you roll all of them at once..

Let's lay down a couple quick rules

1. You cannot just label more than one side of a die with the same letter. None of this 1:D,D,D - 2:U,U,U - 3:C,C,C - 4:K,K,K business.
2. You may however reuse letters on other dice like the sample shows die 2 and die 4 using the letter O.
3. Output of your program should include your dice set, total number of four-letter word combinations and probability of rolling a 4 letter dictionary word.
