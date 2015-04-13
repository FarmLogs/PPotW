fizzBuzz = function(i, x=3, y=5){
	paste("fizz"[i%%x==0], "buzz"[i%%y==0], i[i%%x!=0 & i%%y!=0], sep="")
}

sapply(1:100, fizzBuzz)