# selescript
A sripting langagage for scrapping web pages with selenium.

## Langage definition & grammar

### General design goals of the langage

The design goal for **selescript** is to allow to simply describe the structure 
of the web pages and web objects one wants to scrap.

The langage **should** : 

* be OS agnostic (linux, windows, ios, ...) and only require a recent java installation
* langage is case sensitive,
* white space and new lines are not relevant, allowing for easy formatting
* accept options that fully specify its operation (output format, various page/time limits, ...)
* allow to navigate from one page to another
* react sensibly to missing or stale objects
* be able to take a stream of or a single parameters as input
* be able to detect and recognize what page is being displayed
* allow nested location of objects
* detect the presence/absence of certain caracteristics in the page
* favors use of xpath locators ?
* "emit" recognized objects to the outside world, in the specified format
* should be extensible by implementing "converters" that take an object as input and return a modified object as output. Converters can also provide some aggregation capabilities.

The langage **should not** :

* specify further processing/storage of emitted objects into a database, for instance
* set up the selenium grid server environment (it is recommanded to run a docker server grid)

The general philosophy of the langage should be to :

*  be descriptive (of the page and objects of interest) rather than procedural 
(compiler should figure out how to emit the items of interest)
* ensure that the compiled scraper requires as few dependencies as possible, and can be easily compiled and executed.

### Structure and components of the langage

- **Scopes** are defined with { and }. All variables and options are visible. 
within the current scope and within all the nested scopes. Within scope, the **;** is used a a separator.
- **Unit** is the top level objects. Units cannot be nested. The main class name generated is derived from the unit name.
- **Page** represent a web page in the browser. Pages are part of the unit. Pages can be nested. When pages are nested, the inner pages are independant pages that jsut inherit all the shared properties defined by the upper page. Overwriting is permitted.
- **Element** :  there can be multiple elements in a page. Only elements can be emitted. Elements are defined by locators. They can be tested for existence, content, etc ... Multiple elements can be defined at once with the appropriate xpath. Xpaths are specified with a double quote string prefixed by x, such as : **x"//[@href.contains('jkh')]"**.
- **Variables** do not need to be declared. ( are Elements and Variables the same notion ?). Variables contain lists of things, possibly singletons.
- **Built-ins** are set by assigning/retrieving special variables. They start with a **$** sign.
- **Comments** are using only java-style comments /*....*/ , to remain independant from the new lines.
- **converters** that are defined in the compiled langage (typically java) can be called from the langage. Typically to handle dates or monetary information.
- **tests** can be easily implemented to specify how to recognize/reject a page or an element. Typical test include : assume ... , reject ... , ensure ....
- **assignements** to variables allow to assign a list of string or numbers. (handling of float or int ?)
- **Strings** are defined between " and ". 
- -**Actions** can be used to ask to emit someting, to click on something (and navigate else where), to take a snapshot, etc ...


### Code examples 

```
/* comments can appear anywhere */
unit myscrapper {

  $thisIsAnOption = "all","options","are","lists by default" ;
  myvariable = 3 , 4 ; 
  another variable = 5 ;
  
  
  page mypage {
  
    expect $url contains "www.amazon.fr" ;
    expect $url ! contains myvariable ;         /* ! means NOT */  
      
  
    reject $page >= 30 ;        /* no more that 30 pages ... */
    reject $item >  20 ;        /* no more that 20 emitted items */
      
      
    content = x"//div[@class='sellingItem']" ; /** this is an xpath locator generting an element.
    
    element myelement {
        price = content.x".//span[@class='red']"; /* dotted notaion will start from the previous locator object */
        text = content ; /* default is to get content as text */  
        image = content.x"//[img]".href ; /* Should allow to access any attribute of existing objects */
        }

    emit myelement ;  /* will find all potential values matching the definition, and emit them */
}


