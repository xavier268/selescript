# Selescript Langage Reference

## General structure of the langage

A minimum Hello World program could be :

````
// This is a comment
/* This is also a comment */
print "Hello World" ;
````

Using variable, we can do :
````
user = "Xavier" ;
quotedstring = ' loves "Selescript" ! ' ;   // Notice how string can use single or double quotes.
Print xavier + quotedstring ;
````

Okay, but we want to navigate the web, so this would make more sens :
````
print "Visiting google home page ..." ;
$url = "www.google.com" ;

print "title is : " + $title ;
````



## Expressions

## Statements

Statements can use expressions.

## Loops

## Xpath reference

In Selescript, all xpath are expected to be relative, ie start with a '.'.

## Built_in variables

The langage provides builtins variables. They all start with a $ signi, and can be read and/or written.

The full documentation for the buitins variable is [here](../src/main/resources/rt/builtins.list).

## Grammar reference 

The reference grammar for the langage is [here](../src/main/antlr4/auto/Selescript.g4)
