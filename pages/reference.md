# Selescript langage reference

The selescript langage uses a lot of notions derived from the underlying [Selenium Webdriver API](https://www.seleniumhq.org/docs/03_webdriver.jsp).

## Values

All values are represented as utf-8 String values (stringval). 
A litteral stringval is enclosed between single or double quotes. 

Integer (actually, long integer) numbers are also valid stringvals. 
They do not require quotes, but quoting them is ok. 
There is no fraction/decimal arithemetic.
All numbers are plain stringval.
However, the opposite is not true. For instance '56' is a number. '56r5' is not.
Arithmetic opertaions will fail on stringval that do not represent a number.

*null* represents the null String. 
It means false. All other values are true, ( **including the empty string "" !** ).

## User variables 

All user variables have global scope. They can only store stringval. 
They do not need to be declared.
If used before initialization, they contain null.

## Built-in variables

Built-in variables start with $ sign. 
For instance, $read or $os. 
They can be read, and sometimes written to (see reference below).

## Operators

Operators operate on stringvals. 
You can use parenthesis to force precedence. 
Otherwise, implicit precedence applies.
See [grammar reference](https://xavier268.github.io/selescript/src/main/antlr4/auto/Selescript.g4) for precedence details.

**Arithmetic** operators operate **only** on numbers : plus(++), minus and unary minus(-), multiply(*) and integer division.

**String** operators are : concatenation (+), find or find and replace (~)

**IMPORTANT** : addition is ++, concatenation is +. Please note the difference !

**Comparison** operators are and(&), or(|), not(!), equals(==) and not equals(!=). They return null for false. 
The 'true' value varies, and is usually a meaningfull value, always not null, sometimes empty.

The **dereferencing** operator (@) fetches the text or attribute value of a dom web element.
If provided a single stringval argument, it interprets the argument as the xpath (prefer relative xpath !) 
of a  webelement, and returns the enclosed text. With no argument, it applies 
to the current webelement ".".
If multiple webelements are found, only the first one is processed.
@ accept TAGS that permit to specify that we want to retrieve the value a specific attribute. 

For instance, **result = @ href: './/a'** retrieves the value of 
the 'href' attribute of the first 'a' element, and store it in a user variable named result.

The **find** and **find and replace** operators use the **~**, as in :

````
test1 = 'a long sentence' ~ 'long' ; // test1 will contain 'long', and evaluate to true.
test2 = 'a short sentence' ~ 'long' ; // test2 will contain null, and evaluate to false.
test3 = 'a short sentence' ~ 's.*?t','long' ; 
    // test3 will contain 'a long sentence', where 'short' has been replaced with 'long'.
````

Usual regex syntax applies.

## Statements

Statements are terminated with a semicolon (;) or a closing curly bracket (}).
They do not produce a value, but an effect.

You can **assign** to a user variable or a builin variable, such as in :

````
duration = 12 * 2 + " hours" ;      // Assign to a use variable
$url = 'https://www.google.com' ;   // assign to a builtin variable
````

**print** will output the provided stringval. Using tags allow to url-encode the text, 
or use different filters/formats.

````
print "Hello World" ;       // Plain text
print t: "Hello World" ;    // Same as above, plain text
print u: "This string will be url-encoded" ;            // Url encoded with u:
print "http://www.google.com/?q=" , u: "hello world" ;  // Combining both with a comma

````

**db** will send a mongo document to the specified database. Tags are mandatory.
They specify the field name. 

For instance :

````
db text: "hello world" , result: @ , url: $url ;
````

will record in mongo a document similar to :

````
{ 
    "selescript_version" : "1.2.1",
    "data" {
        "text":"hello world",
        "result":"the content of the page",
        "url":"http://www.google.com"
     }
}
````

**type** will type text in a textarea or an input element. It uses the t: or u: tags to specify the text pieces, encoded or plain. It uses x: to specify the (relative) xpath.
All tags and arguments are optionnal. Default is no text, plain text, on current element.

**submit** and **click** will submit or click on a webelemnt, specified by its xpath. 
Use the w: tag to force to wait until the elemnt becomes stale before continuing.

**go** is the main flow control statement. All arguments are optionnal.
Tags can specify max number of loops (c:), 
max time in different units(sec: ms: hours: ...) and how we want to select the webelemnts
( x: or xpath:, linktext:, name:, id:, plinktext:, ...).
**go** loops can be nested. 
The depth of nesting is $depth. The loop index is $count.

A statement that contains a single string val is evaluated as a test. 
If the stringval is null, **break** is called. 

Loop can be interrupted by evaluating the special builtins : $break, $continue and $abort.
The & and | operators are lazily evaluated and are suited to be combined with these builtins, such as in :

````
( uservariable == 'expected value') | continue ; // will continue if not the expected value
````

