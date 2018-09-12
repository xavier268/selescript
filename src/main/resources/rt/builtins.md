#  Defined built-in variables


## Web variables 

* $url                   Get the current url, or set it (ie, go to the selected page)
* $title      READONLY   Get the title of the page. Setting it has no effect;

## System variables

* $millis     **READONLY**   Time in milliseconds since 1/1/1970. Setting it has no effect.
* $elapsed    **READONLY**   Elapsed time in millis.
* $count      **READONLY**   Counter inside a running loop(1-based), or 0 if outside
* $NL         **READONLY**   A string containing the "new line" character(s).
* $OS         **READONLY**   The OS we are running on
* $JAVA       **READONLY**   The Java Version we are running on

## Important notice

*This file is used to retrieve the builtins to load, by scanning all 
the words that begin with a dollar sign. Do not edit, unless you want 
to add/delete a given builtin.*