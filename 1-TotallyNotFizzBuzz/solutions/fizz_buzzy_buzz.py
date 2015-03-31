#!/opt/local/bin/python

fb = ['fizz']+['']*4+['buzz']
nums = range(1,101)
fizz = map(lambda x: fb[x%3],nums)
buzz = map(lambda x: fb[::-1][x%5],nums)
fizzbuzz = map(lambda (x,y): ''.join((x,y)), zip(fizz,buzz))

for el in [max([n,f], key=len) for n,f in zip(map(str,nums), fizzbuzz)]:
    print el
