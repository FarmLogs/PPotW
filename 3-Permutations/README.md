Welcome to Round 3.

Say that I have a number represented as a string: "1337" for example. I want you to write me a function that takes this as an input and tells me if there are any permutations of that number that are divisible by 8. For example "1337" can translate to "7313", "7331", "1373" ... and so on. You can imagine this permutation list can get quite extensive. Assume the input can be of arbitrary length n. For this example or "1337", the answer is no. However, "1023812387913127030423894783214023901432" is a Yes.

Since I'm not going to write a test-checker, and I *think* my code is correct, you can attempt to reproduce the output below. There is data.json file that has the data points. Feel free to create a different file type for your needs or just hard code it.

gl;hf

====

e.g.
```
➜  solutions git:(master) ✗ ruby perm8.rb
108 is Invalid
333 is Invalid
1337 is Invalid
11333 is Invalid
894103 is Valid
2001238 is Valid
8930134713 is Valid
10341231237891738213012 is Valid
73331111111131313133331333 is Invalid
1023812387913127030423894783214023901432 is Valid
33333333333333333331111111131313133331333 is Invalid
```
