#!/usr/bin/env python   

print '\n'.join('{}{}{}'.format({True: n, False: ''}[not not n % 3 and not not n % 5], {True: 'Fizz', False: ''}[not n % 3],{True: 'Buzz', False: ''}[not n % 5]) for n in range(101))