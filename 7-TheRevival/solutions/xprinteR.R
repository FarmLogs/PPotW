# PPoTW 7
# Author: Ellis Valentiner
# Date: 2015-10-06
#
# xprinterR
#   Function to ascii print NxN size X
#
# Example
# > myfantasticXprinter(5)
# X   X
#  X X 
#   X  
#  X X 
# X   X
#
xprinteR <- function(n=5L){
  # Input must be an odd integer
  stopifnot(n == as.integer(n), n%%2==1)
  # Create bidirectional indicator matrix
  X <- diag(1, n, n) + t(apply(diag(1, n, n), 2, rev))
  # Substitution
  X[X >= 1] <- 'X'
  X[X == 0] <- ' '
  # Print
  write.table(format(X, justify="centre"),
              sep='', row.names=FALSE, col.names=FALSE, quote=FALSE)
}