import qualified Data.Map as M
import Data.Maybe (catMaybes)
import Control.Applicative ((<|>))

data FizzBuzz a = Value a | Fizz | Buzz | FizzBuzz

instance Show a => Show (FizzBuzz a)  where
   show Fizz = "Fizz"
   show Buzz = "Buzz"
   show FizzBuzz = "FizzBuzz"
   show (Value x) = show x

fbMap :: Int -> (FizzBuzz Int) -> M.Map Int (FizzBuzz Int)
fbMap step = M.fromList . zip [step, (2 * step)..100] . repeat

fizzes = fbMap 3 Fizz
buzzes = fbMap 5 Buzz
fizzbuzzes = fbMap 15 FizzBuzz

fizzbuzz :: Int -> Maybe (FizzBuzz Int)
fizzbuzz num =
         let get = M.lookup num
         in get fizzes <|> get buzzes <|>
         get fizzbuzzes <|> Just (Value num)

main = (sequence_ . fmap print) $ catMaybes (fmap fizzbuzz [1..100])
