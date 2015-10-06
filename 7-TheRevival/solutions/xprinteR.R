# PPoTW 7
# Author: Ellis Valentiner
# Date: 2015-10-06

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

# dprinterR
#   Function to ascii print NxN size diamond
#
# Example
# > myfantasticXprinter(5)
#   X  
#  X X 
# X   X
#  X X 
#   X  
#
dprinteR <- function(n=5L){
  # Input must be an odd integer
  stopifnot(n == as.integer(n), n%%2==1)
  p <- (n - 1)/2
  # Create bidirectional indicator matrix
  A <- diag(1, p, p)
  B <- t(apply(A, 2, rev))
  X <- rbind(cbind(B, A[,-1]), cbind(A, B[,-1])[-1,])
  # Substitution
  X[X >= 1] <- 'X'
  X[X == 0] <- ' '
  # Print
  write.table(format(X, justify="centre"),
              sep='', row.names=FALSE, col.names=FALSE, quote=FALSE)
}
