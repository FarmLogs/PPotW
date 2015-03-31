#!/opt/local/bin/python

from sklearn import preprocessing
from sklearn import tree
import numpy as np

nums = range(1,101)
raw = np.random.choice(nums, 1000)
fb = ['fizz']+['']*4+['buzz']

fizz = map(lambda x: fb[x%3],raw)
buzz = map(lambda x: fb[::-1][x%5],raw)
fizzbuzz = map(lambda (x,y): ''.join((x,y)), zip(fizz,buzz))

_out = [max([n,f], key=len) for n,f in zip(map(str,raw), fizzbuzz)]

mod_3 = map(lambda x: x%3,raw)
mod_5 = map(lambda x: x%5,raw)
mod_15 = map(lambda x: x%15,raw)
X = np.transpose(np.vstack((raw,mod_3,mod_5,mod_15)))
Y = _out
le_st = preprocessing.LabelEncoder()
le_st.fit(Y)
Y_st = le_st.transform(Y)


model = tree.DecisionTreeClassifier(random_state = 1234)
model.fit(X,Y_st)

_in = np.transpose(np.vstack((nums, map(lambda x: x%3,nums), map(lambda x: x%5,nums), map(lambda x: x%15,nums))))
mp = model.predict(_in)

print le_st.inverse_transform(mp)