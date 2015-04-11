type FizzBuzz{x, y} end
FizzBuzz(x) = FizzBuzz{x % 3, x % 5}()

fizzbuzz(::FizzBuzz{0, 0}, x) = "fizzbuzz"
fizzbuzz{_}(::FizzBuzz{0, _}, x) = "fizz"
fizzbuzz{_}(::FizzBuzz{_, 0}, x) = "buzz"
fizzbuzz(::FizzBuzz, x) = x

for i in 1:100
  println(fizzbuzz(FizzBuzz(i), i))
end