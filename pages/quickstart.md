# Linux quick start

## Make sure you have the needed tools installed

You will need :

* a git client
* recent maven and java jdk (10+)
* a running and recent docker daemon (useful for quick start, not mandatory)


## Download the needed files 

````
$ git clone git@github.com:xavier268/selescript.git
````

## Compile and prepare the environement

Change to the directory where you doanloaded the application.

````
$ cd selescript
````

Make the ss.sh executable. Your life will be easier later ...

````
$ chmod +x ss.sh
````

Download and run the docker images that will be used.
The first time you run it, this may take a while, depending on your internet 
connection speed.

````
$ bash <selgrid.start.sh
````

## Run a demo script

````
$ ./ss.sh -s demos/demoGoogle.ss
````

Here you go !

## To learn more

* [Selescript reference](reference.md)
* [This project API](../target/site/apidocs/)
* [Selenium project](https://www.seleniumhq.org/)
* [Xpath reference](https://developer.mozilla.org/en-US/docs/Web/XPath)
* [Xpath tutorial](https://www.w3schools.com/xml/xpath_intro.asp)

