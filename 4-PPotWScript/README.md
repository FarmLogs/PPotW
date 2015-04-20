# PPotWScript

For this week's challenge, you'll implement an interpreter for an extremely simple language, PPotWScript. PPotWScript only has 6 operators 
* `=` for variable assignment
* `+`,`-`,`*`,`/` for arithmetic 
* `out` for printing to stdout, automatic newline

There are no conditionals, loops, or any other control statements.

There are no types other than numbers

## Examples
### Example 1
```
x = 5
x = x + 1
y = x - 4
out x
out y
```
outputs
```
6
2
```
### Example 2
```
x = 5 - 6
foo = 123
bar = x + foo
out 10 + 32
out bar
```
outputs
```
42
122
```
### Example 3
```
1 + 2
456 - 32
x = 5
out x
```
outputs
```
5
```

## Slightly more Formal Spec

PPotWScript separates statments by line, and a valid line will look like one of these

`<expr>`, an expression, in one of these forms
* `<number>`,  a number, can be int, float, whatever. We don't really care for purposes of this problem. Examples:
   * `3`
   * `42.0`
   * `555`
* `<var>`,  a variable, assigned with `=`. Valid identifiers [A-Za-z]+. Examples:
   * `x`
   * `foo`
   * `bar`
* `(<number>|<var>) <op> (<number>|<var>)`, where `<op>` is one of `+`,`-`,`*`,`/`. Examples:
   * `1 + 3`
   * `foo * bar`
   * `5 + x`
   * `45 / 2`
   * `y - 8`
   * note this specifies a single `<op>`, so you **don't** have to worry about anything like `2 + 5 / 9` 

This^ is pretty useless without variable assigment. If your interpreter encounters an `<expr>` without a `=`, (like in the third example in the example section), you can just skip it

`<var> = <expr>`, variable assignment, such as
 * `foo = 3`
 * `bar = 3 + 4`
 * `x = foo - 1`
 * `y = foo`

`out <expr>` print the result of the given expression, with a newline. Examples
* `out 6 + 4` prints `10`
* `out foo` prints whatever `foo` is

## Errors
If your interpreter encounters any unfamiliar syntax, feel free to just break without giving any useful output at all.