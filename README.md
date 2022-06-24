# Magneto

This app is a tool created to help Magneto in his search for mutants. Based on a DNA sequence this app can tell if it is a mutant or not.

There are two endpoints on this application:

 - [GET] http://localhost:8080/stats/

 - [POST] http://localhost:8080/mutant/

##Application Usage
You will receive as parameter an array of Strings that represent each row of a table of (NxN) with the DNA sequence. The letters of the Strings can only be: (A, T, C, G), which represents each nitrogenous DNA base.

![Table Image](https://github.com/edwinefl21/Magneto/blob/master/images/matrix.png)


### Endpoint /stats

This endpoint returns a **JSON** with the statistics about the DNA validations
http://mutant.eastus.cloudapp.azure.com:8080/stats/ [GET]
```javascript
{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
```
![Table Image](https://github.com/edwinefl21/Magneto/blob/master/images/get_petition.png)
### Endpoint /mutants

This endpoint will validate a DNA sequence to check if there is a mutant or not
http://localhost:8080/mutant/ [POST]

The POST body must be a JSON similar the bellow example
```javascript
{ 
	"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```
![Table Image](https://github.com/edwinefl21/Magneto/blob/master/images/post_petition.png)

- If the DNA sequence validate is **positive** to mutants will be returned the HTTP code 200.
- If the DNA sequence validate is **negative** to mutants will be returned the HTTP code 403.
- If the DNA contains more than one four-letter sequence equal it is added 1 register in bd.

![Table Image](https://github.com/edwinefl21/Magneto/blob/master/images/DB_DNA.png)


## Automatic compiling and deploying
There is a file `start-magneto-api.sh` that compiles and deploys, the project and the DB, into two different docker containers.<br/>
For executing this file, you need to go into the PROJECT_ROOT_PATH and execute the following command:
```
$ ./start-magneto-api.sh
```

## Deploying
The project is dockerized, and it has an external MongoDB Database dockerized too. For deploying both of them you only need to run the following commands into PROJECT_ROOT_PATH.

The first one will destroy the images generated previously and the second one will create them again.

The project will always check that the DB is already initialized for starting to deploy the application. You can check this process executing `docker-compose up` instead of `docker-compose up -d`
```
$ docker-compose down -v
$ docker-compose up -d
```
