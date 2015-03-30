
data FizzBuzz a = Value a | Fizz | Buzz | Fizzbuzz

fizzbuzz :: Int -> FizzBuzz Int
fizzbuzz x | mod x 3 == 0 = Fizz
           | mod x 5 == 0 = Buzz
           | mod x 15 == 0 = Fizzbuzz
           | otherwise = Value x

instance Show a => Show (FizzBuzz a)  where
   show Fizz = "Fizz"
   show Buzz = "Buzz"
   show Fizzbuzz = "FizzBuzz"
   show (Value x) = show x

main = sequence_ $ fmap (print . fizzbuzz) [1..100]