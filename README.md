# Programming-Languages
This class introduces programming language concepts including design, syntax, semantics, pragmatics, implementation, and evaluation. Presentation of one or more languages from categories including procedural, functional, object-oriented, logic, and concurrent programming paradigms.

C Assignemnt:-

Microproject
Write a C program which contains the following: a function called invert_case, taking a string pointer argument, which inverts the case of each letter in the provided string; a main function which asks the user for an input string, calls invert_case using a pointer to that string, and prints the output.

Main Project
In this assignment you will write a C program which produces auto-complete suggestions, similar to what Bash does when beginning to type a command and pressing . Your program will read all of the file names in a user-provided directory and store them in a data structure made up of an (at least) 26-element array of linked lists. You will need to implement a linked list in C. You will have one linked list for each letter of the alphabet, within which you will store the filenames which begin with that letter in alphabetical order. After reading the filenames into the array of linked lists, the user will be given a “> ” prompt, at which they will enter the beginning of a filename they are interested in. When the user presses enter, all filenames which match that prefix will be printed (again, alphabetically). The program will continue to prompt for beginnings of filenames until an empty string is entered.

You may use any built-in C library functions in your program, but you must implement the linked list yourself. You may not use code found on the internet.

If you are using Windows, be sure your project also works on the CS Linux machines (e.g., pi.cs.oswego.edu). Windows automatically sorts its filenames, but Unix-based systems do not.


Clojure Assginment:-

Microproject
Examine the below main project. Write one or more Clojure functions which perform symbolic simplification on (un-nested) expressions using the and logical connective. Be sure to generalize to all possible variables and valid numbers of arguments to and. For example: 

mp=> (and-simplify '(and true))
true
mp=> (and-simplify '(and x true))
x
mp=> (and-simplify '(and true false x))
false
mp=> (and-simplify '(and x y z true))
(and x y z)
1
2
3
4
5
6
7
8
mp=> (and-simplify '(and true))
true
mp=> (and-simplify '(and x true))
x
mp=> (and-simplify '(and true false x))
false
mp=> (and-simplify '(and x y z true))
(and x y z)
Main Project
Write a set of Clojure functions that perform symbolic simplification and evaluation of boolean expressions using and, or, and not. not will be assumed to take one argument, while and and or will take one or more. You should use false for False and true for True.

Expressions are created as unevaluated lists. Three sample expressions could be defined as follows:

 (def p1 '(and x (or x (and y (not z)))))
 (def p2 '(and (and z false) (or x true)))
 (def p3 '(or true a))
1
2
3
 (def p1 '(and x (or x (and y (not z)))))
 (def p2 '(and (and z false) (or x true)))
 (def p3 '(or true a))
You could also define functions to build unevaluated lists for for you, such as:

 (defn andexp [e1 e2] (list 'and e1 e2))
 (defn orexp  [e1 e2] (list 'or e1 e2))
 (defn notexp [e1] (list 'not e1))
1
2
3
 (defn andexp [e1 e2] (list 'and e1 e2))
 (defn orexp  [e1 e2] (list 'or e1 e2))
 (defn notexp [e1] (list 'not e1))
Using these, p3 could have been created using

(def p3 (orexp true 'a))
1
(def p3 (orexp true 'a))
If you wish to use these in your project, you will need to modify andexp and orexp to allow for one or more arguments.

The main function to write, evalexp, entails calling functions that simplify, bind, and evaluate these expressions.

Simplification consists of replacing particular forms with equivalent functions, including the following:

;; Length 1 Pattern Examples
(or true) => true
(or false) => false
(or x) => x
(and true) => true
(and false) => false
(and x) => x
(not false) => true
(not true) => false
(not (and x y)) => (or (not x) (not y))
(not (or x y)) => (and (not x) (not y))
(not (not x)) => x
;; Length 2 Pattern Examples
(or x false) => x
(or false x) => x
(or true x) => true
(or x true) => true
(and x false) => false
(and false x) => false
(and x true) => x
(and true x) => x
;; Length 3 Pattern Examples
(or x y true) => true
(or x false y) => (or x y)
(and false x y) => false
(and x true y) => (and x y)
[... and so on ...]
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
;; Length 1 Pattern Examples
(or true) => true
(or false) => false
(or x) => x
(and true) => true
(and false) => false
(and x) => x
(not false) => true
(not true) => false
(not (and x y)) => (or (not x) (not y))
(not (or x y)) => (and (not x) (not y))
(not (not x)) => x
;; Length 2 Pattern Examples
(or x false) => x
(or false x) => x
(or true x) => true
(or x true) => true
(and x false) => false
(and false x) => false
(and x true) => x
(and true x) => x
;; Length 3 Pattern Examples
(or x y true) => true
(or x false y) => (or x y)
(and false x y) => false
(and x true y) => (and x y)
[... and so on ...]

You should generalize for any length expression based on these patterns. Your program must work for any arbitrary variables used. As in the microproject, you may wish to write functions to handle certain kinds of cases, and handle the non-recursive case before you handle the recursive one.

Binding consists of replacing some or all of the variables in expressions with provided constants (true or false), and then returning the partially evaluated form.

The evalexp function should take a symbolic expression and a binding map and return the simplest form (that might just be a constant). One way to define this is

  (defn evalexp [exp bindings] (simplify (bind-values bindings exp)))
1
  (defn evalexp [exp bindings] (simplify (bind-values bindings exp)))
Example:

(evalexp p1 '{x false, z true})
1
(evalexp p1 '{x false, z true})
binds x and z (but not y) in p1, leading to (and false (or false (and y (not true)))) and then further simplifies to just false.


