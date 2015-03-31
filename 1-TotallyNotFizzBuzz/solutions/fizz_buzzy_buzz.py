#!/opt/local/bin/python

f = ['fizz']+['']*4
b = ['buzz']+['']*4
nums = range(1,101)
fizz = map(lambda x: f[x%3],nums)
buzz = map(lambda x: b[x%5],nums)
fizzbuzz = map(lambda (x,y): ''.join((x,y)), zip(fizz,buzz))

for el in [max([n,f], key=len) for n,f in zip(map(str,nums), fizzbuzz)]:
    print el
