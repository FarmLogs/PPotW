data FizzBuzz a = Value a | Fizz | Buzz | FizzBuzz

instance Show a => Show (FizzBuzz a)  where
   show Fizz = "Fizz"
   show Buzz = "Buzz"
   show FizzBuzz = "FizzBuzz"
   show (Value x) = show x

fizzbuzz :: Int -> FizzBuzz Int
fizzbuzz x | mod x 3 == 0 = Fizz
           | mod x 5 == 0 = Buzz
           | mod x 15 == 0 = FizzBuzz
           | otherwise = Value x

main = sequence_ $ fmap (print . fizzbuzz) [1..100]
