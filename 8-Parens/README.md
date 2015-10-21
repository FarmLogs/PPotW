# Parens and Balance or a simple grammatical parser.
simple stuff again. This segment we want to balance parens. They must not only have equal numbers, but they must be syntactically correct. You should follow the grammar below. Bonus points for being able to balance out ''s []s and {}s along with the standard ()

```
*match* = "*match_paren*|*match_crl_paren*|*match_sq_paren*|*match_quo**match*"
*match* = "*empty*"
*match_paren* = "(*match*)"
*match_sq_paren* = "[*match*]"
*match_crl_paren* = "{*match*}"
*match_quo* = "'*match*'"
*empty* = ""
```

The words in italics are the primitives in the language. the characters ', { ,} , (, ), [ and ] are also primitives in this language.
you do not need to worry about cases like this [(')[']]


## Examples
### Sample 1
```
./myfantasticbalancechecker "()()()([''][])
true
```
### Sample 2
```
./myfantasticbalancechecker "(((()()'')))"
true
```
### Sample 3
```
./myfantasticbalancechecker "((((()()')')))"
false
```
