
# Quickstart - Linux


## Make sure you have the required tools installed

You will need a java jdk (10+), maven(3.5+) and 
a running docker daemon.

If you are not sure, and want to check, you can try :

```
$ java --version
openjdk 10.0.2 2018-07-17
OpenJDK Runtime Environment (build 10.0.2+13)
OpenJDK 64-Bit Server VM (build 10.0.2+13, mixed mode)



```
$ mvn -version
Apache Maven 3.5.2 (Red Hat 3.5.2-5)
Maven home: /usr/share/maven
Java version: 10.0.2, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-10-openjdk-10.0.2.13-1.fc28.x86_64
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "4.17.19-200.fc28.x86_64", arch: "amd64", family: "unix"



```
docker version
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


