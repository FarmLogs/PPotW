{-# LANGUAGE OverlappingInstances #-}
import qualified Data.Map as M

newtype Number = Number Float

instance Show Number where
         show (Number num) =
              if roundDown num == num
              then show $ floor num
              else show num
              where roundDown = fromIntegral . floor               

type Vars = M.Map String String 
type State = (Vars, String)

interpret :: State -> [String] -> State
interpret (m, s) ("out":expr) = (m, s ++ eval m expr ++ "\n")
interpret (m, s) (var:"=":expr) = (M.insert var (eval m expr) m, s)
interpret (m, s) expr = (m, s)


eval :: Vars -> [String] -> String
eval m [val] = M.findWithDefault val val m
eval m [v1, op, v2] = show $ operation (rd $ eval m [v1]) op (rd $ eval m [v2])
                  where rd = read :: String -> Float

operation :: Float -> String -> Float -> Number
operation v1 op v2 = Number $ operation' v1 op v2
          where operation' v1 "+" v2 = v1 + v2
                operation' v1 "-" v2 = v1 - v2
                operation' v1 "*" v2 = v1 * v2
                operation' v1 "/" v2 = v1 / v2

main :: IO ()
main = do
     program <- fmap lines getContents
     let statements = fmap words program
         result = foldl interpret (M.empty, "") statements
     putStr $ snd result