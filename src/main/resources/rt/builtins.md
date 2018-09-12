#  Defined built-in variables


| Command | Read or Write | Description |
| ---     | ---           | --- | 
| *Web variables* | | |
| $title  | Read only     | Get the title of the page |
| $url    | Read or Write | Get the current url, or set it (ie, go to the selected page) |
| *System variables* | | |
| $count      | Read only |  Counter inside a running loop(1-based), or 0 if outside |
| $elapsed    | Read only |   Elapsed time in millis |
| $JAVA       | Read only |  The Java Version we are running on |
| $millis | Read only | Time in milliseconds since 1/1/1970 |
| $NL         | Read only |  A string containing the "new line" character(s) |
| $OS         | Read only |  The OS we are running on |

**Important notice**

*This file is used to retrieve the builtins to load, by scanning all 
the words that begin with a dollar sign. Do not edit, unless you want 
to add/delete a given builtin.*