# Census [![Build Status](https://travis-ci.org/juanmbellini/census.svg?branch=master)](https://travis-ci.org/juanmbellini/census)

Distributed Objects Programming course Project

## Getting started
These instructions will install the development environment into your local machine

### Prerequisites

1. Install maven

	#### MacOS
	```
	$ brew install maven
	```
	
	#### Ubuntu
	```
	$ sudo apt-get install maven
	```
	
	#### Other OSes
	Check [https://maven.apache.org/install.html](https://maven.apache.org/install.html.).

2. Clone the repository, or download source code
	
	```
	$ git clone https://github.com/juanmbellini/census
	```
	or
	
	```
	$ wget https://github.com/juanmbellini/census/archive/master.zip
	```

### Build

1. Change working directory to project root

	```
	$ cd census
	```

2. 	Install project modules

	```
	$ mvn clean install
	```
3. Build jars

	```
	$ mvn clean package
	```
	
	**Note:** Jar files will be located in the ```<project-root>/<module>/target``` directory, where ```<module>``` can be any of ```api```, ```server``` or ```client```.


## Authors

* [Juan Marcos Bellini](https://github.com/juanmbellini)
* [Martin Alexis Goffan](https://github.com/mgoffan)
* [Eric Nahuel Horvat](https://github.com/EricHorvat)
* [Juan Martín Pascale](https://github.com/jpascale)