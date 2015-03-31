#!/usr/bin/env python

print '\n'.join('{}{}{}'.format(n if n % 3 and n % 5 else '', 'Fizz' if not n % 3 else '', 'Buzz' if not n % 5 else '') for n in range(101))