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

## Procedimiento Map Reduce

Desde los parámetros se introduce el número de Query que se desea ejecutar. Una vez cargados los registros, se obtiene el component query (`HazelcastQuery`) y se ejecuta el método `perform` de dicha interfaz que recibe la lista de `Citizen` y los parámetros de la query:

```
HazelcastQueryCreator.getCreatorByQueryId(params.getQueryId())
                .createHazelcastQuery(hazelcastClient)
                .perform(citizens, queryParams);
```

El método `perform` wrapea toda la lógica del JobTracker de Hazelcast, ejecuta la query llamando al método `perform` y devuelve un resultado.

Cada query en el cliente, implementa una interfaz que hace obligatoria la implementación del método `perform`. Los métodos `perform` en las distintas implementaciones de query en el cliente realizan el llamado en cadena de `mapper` -> `combiner` -> `reducer` -> `submit` -> `get` del job, inyectando en cada caso la implementación de cada Mapper, Combiner y Reducer correspondiente a dicha query. Estas implementaciónes están ubicadas en sus respectivos packages en API.

A continuación se describen mediante un esquema los procedimientos Map Reduce en cada query. Todos los Mappers de las queries reciben Objetos (, Citizen). Cada par Key Value de map reduce se expresará de la forma (K, V)

### Query 1

(, Citizen) -> MAPPER -> (Region, 1) -> COMBINER -> (Region, X) -> REDUCER -> (Region, N) -> SUBMIT -> (Region, N) (Ordenado) [X < N]

### Query 2

(, Citizen) -> MAPPER -> (Province, 1) -> COMBINER -> (Province, X) -> REDUCER (Province, N) -> SUBMIT -> (Region, N) (Ordenado y filtrado)

Nota: Para Query1 y Query2 se reciclan `UnitCounterCombiner` y `CountReducer`.

### Query 3

(, Citizen) -> MAPPER -> (Region, BooleanPair<IsWorking, IsHomeless>) -> COMBINER -> (Region, IntegerPair(IsWorkingCount, IsHomelessCount)) -> REDUCER -> (Region, Double).

### Query 4

(, Citizen) -> MAPPER -> (Region, hogarId) -> REDUCER (Guarda resultado intermedio en un Set de hogarId) -> (Region, N) -> SUBMIT -> (Region, N) (Ordenado)

### Query 5

(, Citizen) -> MAPPER -> (Region, homeId) -> REDUCER -> (Region, Double) -> SUBMIT -> (Region, N) (Ordenado)

Nota: HomeId es Long.

### Query 6

(, Citizen) -> MAPPER -> (DepartmentName, ProvinceName) -> REDUCER -> (Department, X) -> SUBMIT -> (Department, X) (Ordenado y filtrado)

### Query 7

(, Citizen) -> MAPPER -> (<Province, Province>, <Department, Department>) -> REDUCER -> (<Pronvice, Province>, X) -> SUBMIT -> (Region, N) (Ordenado)
Nota: Si el reducer recibe <Prov1, Prov2> <Dep, null> significa que el Dep1 corresponde a Prov1 y no a Prov2, en el momento en que reciba <Prov1, Prov2> <null, Dep> se va a contabilizar un departamento en común.
Nota2: Department es un String


## Authors

* [Juan Marcos Bellini](https://github.com/juanmbellini)
* [Martin Alexis Goffan](https://github.com/mgoffan)
* [Eric Nahuel Horvat](https://github.com/EricHorvat)
* [Juan Martín Pascale](https://github.com/jpascale)
