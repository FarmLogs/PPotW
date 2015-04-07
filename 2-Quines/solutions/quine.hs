import Text.Printf
quote = toEnum 34 :: Char
newline = toEnum 10 :: Char
program = "import Text.Printf%cquote = toEnum 34 :: Char%cnewline = toEnum 10 :: Char%cprogram = %c%s%c%cmain = printf program newline newline newline quote program quote newline newline%c"
main = printf program newline newline newline quote program quote newline newline
