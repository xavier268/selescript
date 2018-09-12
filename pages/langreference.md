# Selescript Langage Reference

## General structure of the langage

A minimum Hello World program :

````
// This is a comment
/* This is also a comment */
print "Hello World" ;
````

Using variable :
````
user = "Xavier" ;
quotedstring = ' loves "Selescript" ! ' ;   // Notice how string can use single or double quotes.
print user + quotedstring ;
````

Navigating the web :

````
print "Visiting google home page ..." ;
$url = "www.google.com" ;

print "title is : " + $title ;
````



## Expressions

Expressions result from combining  strings, numbers and variables.

* String is a text enclossed in single quotes or double quotes.
* numbers are positives or negative **integer** numbers. They should not start with 0, except for 0 itself. 
* variables can use letters and digits (but cannot start with a digit). They do not need to be declared. If used before being assigned a value, they return null.
* there are pre-defined variable, starting with a $ sign, that produce special effects. See below, **built-ins variables**.

Parenthesis work as expected in expressions.

All expressions ultimately evaluate to a string, possibly empty, or null.

* a null string means **false**
* any non null string (*even empty !*) means **true**

## operators

Operators can apply to strings or numbers. If needed, numbers are promoted to strings.

* *Number* operators are :  +  addition, - substraction or unary minus, / integer divide, * times
* *String* operators are :  + concatenation, & and, | or, ! not,  == equals, != ,ot equals, ~ pattern find or find and replace
* the *@* operator is a derefencing operator, extracting the text or an attribute from an xpath string expression

## Using the  @ operator

When applied to an expression that represents an xpath, @ will get the attribute of the first element that matches the xpath, or the text of the element if no attribute is specified. Attribute is specified by prepending the attribute and a semicolon before the xpath.

## Statements

Statements use expressions to produce an effect in the program. Statements are always terminated by a closing parenthesis } or by a semi-colon ;

* Assignements to a variable (user variable or builtin variable)
* Output information, to the console (print), formated (emit), to the database (db)
* Click on an element and continue immediateletely (click) or click and wait for the element to disapperar before continuing(clickw).
* Evaluate an expression by simply ending the expression with a semicolon. If expression is false/null, the next element in the loop is processed immediateley, or the loop ends if no more elemnts. 

## Loops

## Xpath reference

In Selescript, all xpath are expected to be relative, ie start with a '.'.

For a quick Xpath tutorial, see [here](https://www.w3schools.com/xml/xpath_intro.asp)

## Built_in variables

The langage provides builtins variables. They all start with a $ sign, and can be read and/or written.

The full documentation for the buitins variable is [here](../src/main/resources/rt/builtins.html).

## Grammar reference 

The reference grammar for the langage is [here](../src/main/antlr4/auto/Selescript.g4)
