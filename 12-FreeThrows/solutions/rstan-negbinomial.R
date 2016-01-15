# PPoTW 12
# Author: Ellis Valentiner
# Date: 2016-01-15
#
# Estimates theta
#
# Data:
#   z: Raw "unobserved" data
#   x: Run-length encoded data
#   y: Streak lengths
#
# Returns:
#   Interfence for the model parameters, including theta
library(rstan)
rstan_options(auto_write = TRUE)
options(mc.cores = parallel::detectCores())

model = "
data {
  int n;
  int y[n];
}
parameters {
  real<lower=0,upper=1> rho;
}
model {
  rho ~ beta(1, 1);
  sum(y) ~ neg_binomial(1, rho);
}
generated quantities {
  real theta;
  theta <- 1 - rho;
}"

z = c(0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1,
      1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0)
x = rle(z)
y = x$lengths[x$value == 1] - 1
n = length(y)
data = list("n"=n, "y"=y)

fit = stan(model_code = model, data = data, iter = 5000)
fit
# Inference for Stan model: 0c0e4767539bd9ac43b32266e813ef68.
# 4 chains, each with iter=5000; warmup=2500; thin=1; 
# post-warmup draws per chain=2500, total post-warmup draws=10000.

#        mean se_mean   sd   2.5%   25%   50%   75% 97.5% n_eff Rhat
# rho    0.07    0.00 0.05   0.01  0.03  0.06  0.10  0.21  4120    1
# theta  0.93    0.00 0.05   0.79  0.90  0.94  0.97  0.99  4120    1
# lp__  -8.02    0.02 0.77 -10.14 -8.18 -7.73 -7.54 -7.49  2468    1

# Samples were drawn using NUTS(diag_e) at Fri Jan 15 14:39:54 2016.
# For each parameter, n_eff is a crude measure of effective sample size,
# and Rhat is the potential scale reduction factor on split chains (at 
# convergence, Rhat=1).
