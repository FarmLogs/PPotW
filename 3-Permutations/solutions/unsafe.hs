{-# LANGUAGE FlexibleInstances, OverlappingInstances #-}
import Data.List (permutations, (\\))

-- cat data.json | runghc unsafe.hs

permutations' :: Eq a => Int -> [a] -> [[a]]
permutations' limit list =
  if length list <= limit
  then permutations list
  else permutations'' [] limit list
  where permutations'' soFar limit list =
          if length soFar == limit then [soFar]
          else do
            x <- list
            tail <- permutations'' (x:soFar) limit (list \\ [x])
            [x:tail]

checkPerm :: Integer -> Bool
checkPerm num = mod num 8 == 0 

check :: String -> (Integer, Bool)
check numstr = 
  let perms = fmap read $ permutations' 3 numstr
  in (read numstr, any checkPerm perms)

instance Show (Integer, Bool) where
  show (num, valid) =
    show num ++ " is " ++
    if valid then "Valid" else "Invalid"

main :: IO ()
main = do 
  numbers <- fmap read getContents
  mapM_ (print . check) numbers