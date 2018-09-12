
# Quickstart - Linux


## Make sure you have the required tools installed !

You will need : 

* java jdk (10+), 
* maven(3.5+) and, 
* a running docker daemon.

You should see something similar to :

````
$ java --version

openjdk 10.0.2 2018-07-17
OpenJDK Runtime Environment (build 10.0.2+13)
OpenJDK 64-Bit Server VM (build 10.0.2+13, mixed mode)
````



````
$ mvn -version

Apache Maven 3.5.2 (Red Hat 3.5.2-5)
Maven home: /usr/share/maven
Java version: 10.0.2, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-10-openjdk-10.0.2.13-1.fc28.x86_64
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "4.17.19-200.fc28.x86_64", arch: "amd64", family: "unix"
````


````
$ docker version

Client:
 Version:           18.06.1-ce
 API version:       1.38
 Go version:        go1.10.3
 Git commit:        e68fc7a
 Built:             Tue Aug 21 17:25:02 2018
 OS/Arch:           linux/amd64
 Experimental:      false

Server:
 Engine:
  Version:          18.06.1-ce
  API version:      1.38 (minimum version 1.12)
  Go version:       go1.10.3
  Git commit:       e68fc7a
  Built:            Tue Aug 21 17:26:30 2018
  OS/Arch:          linux/amd64
  Experimental:     false
  ````
  
## Download the selescript program
  
Git clone on your workstation :
  
  ````
$ git clone git@github.com:xavier268/selescript.git

Clonage dans 'selescript'...
remote: Counting objects: 1613, done.
remote: Compressing objects: 100% (12/12), done.
remote: Total 1613 (delta 4), reused 0 (delta 0), pack-reused 1601
Réception d'objets: 100% (1613/1613), 201.93 KiB | 743.00 KiB/s, fait.
Résolution des deltas: 100% (720/720), fait.
````

Then, change to the root of the clone directory :

````
$ cd selescript/
````

## Launch a grid of browsers 

We are providing, for your convenience, a script that starts a selenium hub with a couple of firefox and chrome browsers, and a mongo db instance. It is not intentend for production, but is an easy way to get started fast.

````
$ bash <selgrid.start.sh 

This script is provided as a convenience script to test the selescript langage
It will launch a docker swarm/stack with a few headless browsers
You may need to launch this as root, depending of your configuration details

Server Version: 18.06.1-ce
Swarm: inactive
Kernel Version: 4.17.19-200.fc28.x86_64
Swarm: active
ID                            HOSTNAME            STATUS              AVAILABILITY        MANAGER STATUS      ENGINE VERSION
hwtoaqzy5wch675ua09zpeqst *   fedora28onDellXPS   Ready               Active              Leader              18.06.1-ce
Creating network selgrid_default
Creating service selgrid_hub
Creating service selgrid_mongo
Creating service selgrid_firefox
Creating service selgrid_chrome
NAME                SERVICES            ORCHESTRATOR
selgrid             4                   Swarm
ID                  NAME                MODE                REPLICAS            IMAGE                        PORTS
p54fgqoctw7q        selgrid_chrome      replicated          0/1                 selenium/node-chrome:3.14    
0b2m5264xn02        selgrid_firefox     replicated          1/1                 selenium/node-firefox:3.14   
79q3ndiafbh9        selgrid_hub         replicated          1/1                 selenium/hub:3.14            *:4444->4444/tcp
wxcf7fu4c488        selgrid_mongo       replicated          1/1                 mongo:3.6                    *:27017->27017/tcp
````

You may notice that some replicas are not all running (showing 0/1 instead of 1/1). You can just wait for a few more seconds and check that everything is fine by typing :

````
$ docker service ls

ID                  NAME                MODE                REPLICAS            IMAGE                        PORTS
p54fgqoctw7q        selgrid_chrome      replicated          1/1                 selenium/node-chrome:3.14    
0b2m5264xn02        selgrid_firefox     replicated          1/1                 selenium/node-firefox:3.14   
79q3ndiafbh9        selgrid_hub         replicated          1/1                 selenium/hub:3.14            *:4444->4444/tcp
wxcf7fu4c488        selgrid_mongo       replicated          1/1                 mongo:3.6                    *:27017->27017/tcp
````

Okay, everything up and running, we can compile and run our first demo script.

## Running a demo file


Let's make the ss.sh script executable, it will be our main entry point to Selescript.

````
$ chmod +x ss.sh
````

We can now use Selescript to run a demo on Google.
Since it is the first time we run it, it is going to be compiled, will load a few things, and that may take some time.
Once compiled, it will start the scrapper (because we specified -x for "eXecute" on the command line).

````
$ ./ss.sh -x -s demos/demoGoogle.ss

[... a lot of stuff appears here ... ]
[ Then the demo will run, displaying information retrieved from the web ]
done.
````

## And there's more !

You can get the command line arguments from ss.sh by typing :

````
$ ./ss.sh -h

Apache Maven 3.5.2 (Red Hat 3.5.2-5)
Maven home: /usr/share/maven
Java version: 10.0.2, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-10-openjdk-10.0.2.13-1.fc28.x86_64
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "4.18.5-200.fc28.x86_64", arch: "amd64", family: "unix"
Using <target/selescript-0.5.2-SNAPSHOT-jar-with-dependencies.jar> with arguments : -h

Selescript compiler.
(c) Xavier Gandillot - 2018
See details on https://xavier268.github.io/selescript/
Selescript version : 0.5.2
Using Selenium version : 3.14.0
Compiling for java version : 10
Generated files will be saved in : /home/xavier/Bureau/selescript/target/dist-NoName

Available commands : 

     -h
     --help        : print this help message and exit

     -v
     --version     : print version information and exit

     -d
     --debug        : set debug mode to true, default is false

     --dry-run
     --dryrun       : compile everything, but do not save to file.
                      Default is to actually save to file

     -s FILE
     --source FILE  : compile from specified FILE.
                      Default is to read from stdin.

     -x
     --execute
     --run          : execute immediately the compiled script.
                      Default is not to run immediately.
                      Execute has no effect if in --dry-run mode
                      WARNING : this is LINUX specific

     -g
     --grid
     --grid-url
     --gridurl      : specify the full url to the grid.
                      Default : http://localhost:4444/wd/hub

     --mongo-url    : mongo connexion string (host & port).
                      Default to mongodb://localhost:27017
     --mongo-db     : mongo database name. Default selescriptdb
     --mongo-col    : mongo collection. Default selescriptcol.

      --firefox
      --chrome      : select browser type. Default is firefox.

     -c
     --class
     --name
     --classname CLASSNAME : generated scrapper will be named CLASSNAME.
                             Default will be derived from source file if
                             available, or 'NoName' if reading from stdin.

````

The current version could have already changed, and more options might be displayed.

## Running directly the compiled file

Once compiled, an executable jar is availble for use. It can be used in an OS-independant way, can run on windows, or any where.
You can access it by looking inside the target directoy, where you will have one dist-xxx directory per compile file.
Fr instance, changing into the dist-demoGoogle directory, you will see :

````
$ ll

total 44
-rw-rw-r-- 1 xavier xavier 1967 10 sept. 19:44  demoGoogle.ss
-rw-rw-r-- 1 xavier xavier    9 10 sept. 19:44  LICENSE.txt
-rw-rw-r-- 1 xavier xavier  661 10 sept. 19:45 'Log-lun. sept. 10 19:44:43 CEST 2018.txt'
-rw-rw-r-- 1 xavier xavier 3069 10 sept. 19:44  pom.xml
-rw-rw-r-- 1 xavier xavier  166 10 sept. 19:44  README.txt
-rw-rw-r-- 1 xavier xavier 1660 10 sept. 19:44  run.sh
-rw-rw-r-- 1 xavier xavier  675 10 sept. 19:44  selgrid.start.sh
-rw-rw-r-- 1 xavier xavier  204 10 sept. 19:44  selgrid.stop.sh
-rw-rw-r-- 1 xavier xavier 1222 10 sept. 19:44  selgrid.yaml
drwxrwxr-x 3 xavier xavier 4096 10 sept. 19:44  src
drwxrwxr-x 7 xavier xavier 4096 10 sept. 19:44  target
````

Move into the target directory.
````
$ cd target
$ ll

total 11692
drwxrwxr-x 2 xavier xavier     4096 10 sept. 19:44 archive-tmp
drwxrwxr-x 3 xavier xavier     4096 10 sept. 19:44 classes
drwxrwxr-x 3 xavier xavier     4096 10 sept. 19:44 generated-sources
drwxrwxr-x 2 xavier xavier     4096 10 sept. 19:44 maven-archiver
drwxrwxr-x 3 xavier xavier     4096 10 sept. 19:44 maven-status
-rw-rw-r-- 1 xavier xavier    20031 10 sept. 19:44 scrapper-DemoGoogle-1536601483422-SNAPSHOT.jar
-rw-rw-r-- 1 xavier xavier 11930551 10 sept. 19:44 scrapper-DemoGoogle-1536601483422-SNAPSHOT-jar-with-dependencies.jar
````

The file that ends with jar-with-dependencies.jar is the only one that you need to run again the same scrapper.
````
$ java -jar scrapper-DemoGoogle-1536601483422-SNAPSHOT-jar-with-dependencies.jar

[../.. the demo runs directly ]
````

You can adjust the level of logging information from the logback.xml resource file. 


  


