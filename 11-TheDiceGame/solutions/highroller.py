import argparse
import itertools
import random
from collections import defaultdict
from string import ascii_lowercase

parser = argparse.ArgumentParser()
parser.add_argument('-n', '--numdice', help='number of dice', default=4)
parser.add_argument('-s', '--sides', help='sides per die', default=3)
parser.add_argument('-d', '--dictionary', help='dictionary file', default='words.txt')
parser.add_argument('-r', '--rolls', help='test rolls', type=int, default=1000)
args = parser.parse_known_args()[0]

with open(args.dictionary, 'r') as fh:
    words = filter(lambda x: len(x) == args.numdice, map(lambda x: x.rstrip().lower(), fh))

chars_to_words = defaultdict(list)
for word in words:
    chars_to_words[''.join(sorted(word))].append(word)


def child_count(stem):
    n = 0
    for word in chars_to_words:
        wordchars = list(word)
        for char in stem:
            if char in wordchars:
                wordchars.remove(char)
            else:
                break
        else:
            n += 1
    return n

dice = []
for die in xrange(1, args.numdice + 1):
    char_to_child_count = defaultdict(int)
    for c in ascii_lowercase:
        char_to_child_count[c] = sum(child_count(stem + (c,)) for stem in itertools.product(*dice))
    dice.append(sorted(char_to_child_count, key=lambda c: char_to_child_count[c], reverse=True)[:args.sides])

n = 0
for roll in xrange(args.rolls):
    chars = ''.join(sorted(random.choice(die) for die in dice))
    if chars in chars_to_words:
        #  print chars, chars_to_words[chars]
        n += 1

print 'Scored on {} of {} rolls ({:1.2f}%)'.format(n, args.rolls, float(n) / args.rolls * 100)
print 'Die faces: {}'.format(dice)
