# Magneto

This app is a tool created to help Magneto in his search for mutants. Based on a DNA sequence this app can tell if it is a mutant or not.

There are two endpoints on this application:

 - [GET] http://mutantsmeli.koreacentral.cloudapp.azure.com:8080/stats/

 - [POST] http://mutantsmeli.koreacentral.cloudapp.azure.com:8080/mutant/

##Application Usage
You will receive as parameter an array of Strings that represent each row of a table of (NxN) with the DNA sequence. The letters of the Strings can only be: (A, T, C, G), which represents each nitrogenous DNA base.

![Table Image](https://github.com/edwinefl21/mutants/blob/main/images/matrix.png)


### Endpoint /stats/

This endpoint returns a **JSON** with the statistics about the DNA validations
http://mutantsmeli.koreacentral.cloudapp.azure.com:8080/stats/ [GET]
```javascript
{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
```
![Table Image](https://github.com/edwinefl21/mutants/blob/main/images/get_petition.png)
### Endpoint /mutants/

This endpoint will validate a DNA sequence to check if there is a mutant or not
http://mutantsmeli.koreacentral.cloudapp.azure.com:8080/mutant/ [POST]

The POST body must be a JSON similar the bellow example
```javascript
{ 
	"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```
![Table Image](https://github.com/edwinefl21/mutants/blob/main/images/post_petition.png)
![Table Image](https://github.com/edwinefl21/mutants/blob/main/images/post_petition2.png)


- If the DNA sequence validate is **positive** to mutants will be returned the HTTP code 200.
- If the DNA sequence validate is **negative** to mutants will be returned the HTTP code 403.
- If the DNA sequence does not contain the allowed letters (A, T, C, G) mutants will be returned the HTTP code 400.
- If the DNA sequence parameter is empty or null  will be returned the HTTP code 400.

![Table Image](https://github.com/edwinefl21/mutants/blob/main/images/DB_DNA.png)

## Coverage
![Table Image](https://github.com/edwinefl21/mutants/blob/main/images/coverage.png)


## Deploying
The project is dockerized, and it has an external MongoDB Database dockerized too. For deploying both of them you only need to run the following commands into PROJECT_ROOT_PATH.

The first one will destroy the images generated previously and the second one will create them again.

The project will always check that the DB is already initialized for starting to deploy the application. You can check this process executing `docker-compose up` instead of `docker-compose up -d`
```
$ docker-compose down -v
$ docker-compose up -d
```
