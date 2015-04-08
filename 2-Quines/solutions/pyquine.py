#!/usr/bin/env python3
def lol():
  from inspect import getsource
  print(''.join(('#!/usr/bin/env python3\n', getsource(lol), 'lol()')))
lol()