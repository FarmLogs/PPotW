#!/usr/bin/env python3

from json import load
from itertools import permutations as p

print('\n'.join('{} is {}'.format(n, 'Valid' if any(not int(''.join(i)) % 8 for i in p(n, 3)) else 'Invalid') for n in load(open('data.json', 'r'))))