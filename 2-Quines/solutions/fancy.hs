{-# LANGUAGE CPP #-}
-- be sure to run this w/ `runghc <filename>.hs`
main :: IO ()
main = readFile __FILE__ >>= putStr
