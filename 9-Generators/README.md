# Parens and Balance or a simple grammatical *GENERATOR*
Since last we week made validators for a gramar, it's time to put them all against the grind. This week, you again be using the same grammar structure used last week. However, instead of writing validators for this grammar, I want you to generate random *valid* grammar of n length. In order to help you this week, I have re-pasted the grammar from last week into this document.

```
*match* = *match_paren*|*match_sq_paren*|*match_crl_paren*|*match_quo**match*
*match* = *empty*
*match_paren* = (*match*)
*match_sq_paren* = [*match*]
*match_crl_paren* = {*match*}
*match_quo* = '*anything*'
*anything* = all characters except '
*empty* =
NOTE: *match* should be considered empty, or a *(some matching pair thing)**match*. the latter means that you should look for a matching pair, followed by the *match* primitive again. you can think about this construct like (regex|match|with|or)+ since you need at least one match, but the *match* can be empty or more.
The words surrounded by * are the primitives in the language. the characters ', { ,} , (, ), [ and ] are also primitives in this language.
```

rough edge: in the grammar above, there is no valid statement that is only 1 character long. You may ignore this case or simply print some invalid message. The domain for your input should be 0 and anything equal to or greater than 2. no floats or decimal points because that makes no sense.


## Examples
### Sample 1
```
./myfantasticbalancechecker 15
()()()([')'][])
```
### Sample 2
```
./myfantasticbalancechecker 15
'(((()()'((()))
```
### Sample 3
```
./myfantasticbalancechecker 19
'(((((('()([][]']')
```
### Sample 4
```
./myfantasticbalancechecker 1
invalid
```
