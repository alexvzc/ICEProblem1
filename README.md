# Last CUSIP Price

> Problem 1:
> You are given a file formatted like this:
> 
> ```
> CUSIP
> Price
> Price
> Price
> …
> CUSIP
> Price
> Price
> CUSIP
> Price
> Price
> Price
> …
> Price
> CUSIP
> Price
> …
> ```
> 
> Think of it as a file of price ticks for a set of bonds identified by their CUSIPs.
> 
> You can assume a CUSIP is just an 8-character alphanumeric string.
> 
> Each CUSIP may have any number of prices (e.g., 95.752, 101.255) following it in
> sequence, one per line.
> 
> The prices can be considered to be ordered by time in ascending order, earliest to latest.
> 
> Write me a Java program that will print the closing (or latest) price for each CUSIP in the file.
> 
> DO NOT assume the entire file can fit in memory!

### Build

Apache Maven 3.x and JDK 1.8.0 is required to build this project.

Once built, the target directory will contain a "iceproblem1.jar". This is an executable jar that can be used from the command line.

### Usage

```
java -jar iceproblem1.jar -i [file]
```

Example (run from the project directory after building the project):

```
java -jar iceproblem2.jar -i src/main/resources/cusipprices.txt
```

There's no output to the console, unless there is some error.

### Code review

The class mx.avc.iceproblem1.CUSIPScanner contains all the relevant code of finding a CUSIP along with its last price.
