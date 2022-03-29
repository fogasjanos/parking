Parking Lot
===========

Table of contents
=================

* [The Problem](#the-problem)
* [Installation](#installation)
  * [Requirements](#requirements)
  * [How to build](#how-to-build)
  * [How to run](#how-to-run)
    * [Interactive mode](#interactive-mode)
    * [Load commands from file](#load-commands-from-file)
* [Usage](#usage)
* [Possible improvements](#possible-improvements)

# The Problem
Implement a parking lot's automated ticketing system. The ticket numbers identifies the parking space and starts from 1.
To have a ticket the customer have to provide the plate number of his/her car. The assigned ticket number should be the
lowest as possible (aka the nearest to the entrance/exit).
When the customer leave the lot it will be charged by the parking time. It's $10 for the first two hours and extra $10
for each additional hour.

The application has a command line interface with some simple commands (see below). The application should accept a 
filename and read the commands from that file.

Commands:
* `create [size]` - Creates parking lot of size n
* `park [car-number]` - Parks a car
* `leave [car-number] [hours]` - Removes (unpark) a car
* `status` - Prints status of the parking lot

## Sample

For this input:
```text
create 6
park HUB-1234
park HUB-9999
park ABE-0001
park HUB-7777
park HUB-2701
park HUB-3141
leave HUB-3141 4
status
park VIK-333
park GAB-999
leave HUB-1234 4
leave ABE-0001 6
leave GAB-999 2
park JAN-0987
park LAC-1111
park JAN-0123
status
```

The application should return with this output:
```text
Created parking lot with 6 slots
Allocated slot number: 1
Allocated slot number: 2
Allocated slot number: 3
Allocated slot number: 4
Allocated slot number: 5
Allocated slot number: 6
Registration Number HUB-3141 from Slot 6 has left with Charge 30
Slot No. Registration No.
1        HUB-1234
2        HUB-9999
3        ABE-0001
4        HUB-7777
5        HUB-2701
Allocated slot number: 6
Sorry, parking lot is full
Registration Number HUB-1234 from Slot 1 has left with Charge 30
Registration Number ABE-0001 from Slot 3 has left with Charge 50
Registration Number GAB-999 not found
Allocated slot number: 1
Allocated slot number: 3
Sorry, parking lot is full
Slot No. Registration No.
1        JAN-0987
2        HUB-9999
3        LAC-1111
4        HUB-7777
5        HUB-2701
6        VIK-333

```

# Installation

## Requirements

To be able to compile / run the application you need the followings:
- Internet connection to build the app for the first time.
- Gradle 7.4+ (Can be downloaded from https://gradle.org/install/)
  or you can use the bundled wrapper
- JDK 14+ (Can be downloaded from https://adoptium.net/)


## How to build

Installed gradle:
```shell
gradle clean build
```

Gradle wrapper:
```shell
gradlew clean build
```


## How to run

### Interactive mode

***Note:*** exit with `Ctrl+Z` or `Ctrl+D`

Installed gradle:
```shell
gradle --console plain run
```

Gradle wrapper:
```shell
gradlew --console plain run
```


### Load commands from file

***Note:*** it won't work with PowerShell, try to use the good old `cmd` under Windows.

Installed gradle:
```shell
gradle run < src/test/resources/sample-input.txt
```

Gradle wrapper:
```shell
gradlew run < src/test/resources/sample-input.txt
```

# Usage

## Commands

* `create [size]` - Creates parking lot of size n
* `park [car-number]` - Parks a car
* `leave [car-number] [hours]` - Removes (unpark) a car
* `status` - Prints status of the parking lot


# Possible improvements
- AppTest is failing when all tests run from IntelliJ IDEA on src/test right-click "Run all Tests Ctlr+Alt+F10" but it's
fine when run from gradle. Or when you type gradlew test into the IDEA's console and hit Ctlr+Enter.
