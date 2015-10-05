#print an x.
n= 5

print '\nX shape'
for y in range(n):
    for x in range(n):
        if x==y or y+x==n-1:
            print 'x',
        else:
            print ' ',
    print ''

print '\nDiamond'
for y in range(n):
    for x in range(n):
        if (x+y) == n/2 or (x+y+1) == n*3/2 or\
                (y-x)**2 == (n/2)**2:
            print 'x',
        else:
            print ' ',
    print ''

