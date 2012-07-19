
PURPOSE:


Design a simple P2P system using Java RMI

PERCENT COMPLETE:

We believe we have completed 100% of this project.

PARTS THAT ARE NOT COMPLETE:

None

BUGS:

None

TO COMPILE:

Assuming you are in the directory containing this README:

## To clean:
ant -buildfile src/build.xml clean

## To compile: 
ant -buildfile src/build.xml

TO RUN:

## Assuming you arre in build/classes folder
## Run rmiregistry first
$ rmiregistry

## Run minimum 3 servers
$ java server.ServerDriver <rmiregHost> <rmiregPort>

## Run a client
$ java client.ClientDriver <rmiregHost> <rmiregPort> <songtoSearch> <numHops>

EXTRA CREDIT:

N/A

BIBLIOGRAPHY:

None

ACKNOWLEDGEMENT:

None
