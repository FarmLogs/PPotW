##THE PROBLEM HAS BEEN ALTERED. PRAY THAT I DO NOT ALTER IT FURTHER
You **DO** need to worry about cases like this: 
```
[('))[')]
```
It is valid. Due to the backlash of "This problem is so simple this morning", the problem has been modified so that you must respect single quotes as strings and therefore must now ignore parens/brackets that are contained within a ' characters.

# Parens and Balance or a simple grammatical parser.
simple stuff again. This segment we want to balance parens. They must not only have equal numbers, but they must be syntactically correct. You should follow the grammar below. Bonus points for being able to balance out ''s []s and {}s along with the standard ()

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
```
The words surrounded by ** are the primitives in the language. the characters ', { ,} , (, ), [ and ] are also primitives in this language.



## Examples
### Sample 1
```
./myfantasticbalancechecker ()()()([')'][])
true
```
### Sample 2
```
./myfantasticbalancechecker '(((()()')))
false
```
### Sample 3
```
./myfantasticbalancechecker '(((((('()([][]']')
true
```
