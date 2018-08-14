# selescript
A sripting langagage for scrapping web pages with selenium.

## Langage definition & grammar

### Design goals of the langage

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
* "emit" recognized objects to the outside world, in the specified format

The langage **should not** :

* specify further processing/storage of emitted objects into a database, for instance
* set up the selenium grid server environment (it is recommanded to run a docker server grid)

The general philosophy of the langage should be to :

*  be descriptive (of the page and objects of interest) rather than procedural 
(compiler should figure out how to emit the items of interest).

### Structure and components of the langage

- Scopes are defined with { and }. All avriables and options are visible 
within the current scope and within all the nested scopes.
- unit : the top level object. Unit cannot be nested. 
- page :  a web page in the browser. Pages are part of the unit. Pages can be nested, in such case


