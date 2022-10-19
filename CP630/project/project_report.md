# PROJECT Report

Author: Josh Barber

Date: December 16, 2021

Check [readme.txt](readme.txt) for lab work statement and self-evaluation.

## Project Requirements (project)

### R1 Write a project proposal (2-3 pages).


Complete? (***Yes***/No)

Project Proposal attached.
[Project Proposal](cp630_project_proposal_barb4630@mylaurier.ca.pdf)


### R2 Design data format, collect data, create dataset for the application.


Complete? (***Yes***/No)

I've taken data from kaggle.
Datasource: https://www.kaggle.com/fedesoriano/heart-failure-prediction

This data has been adjusted to be all numeric.

### R3 Develop and implement data application algorithms for the proposed application problem.


Complete? (***Yes***/No)

I've turned the data from kaggle to be all numeric values, from this I am using two application algorithms. A decision tree algorithm M5P, and also using linear regression algorithm.


### R4 Do data computing to generate models, representing models in portable format and stored in file or database. More credit is given if distributed approach is used data mining of big dataset.


Complete? (***Yes***/No)

Stored the learning models within the database. Persistence is set for Users and MOdels. Have an mvn project setup to do the initial db saves of the linear regression model, M5P Decision Tree model, and for the Users.


### R5 Create deployable service components using application models using Java based enterprise computing technologies, to create client program to do remote call of the data mining services.

Complete? (***Yes***/No)

Have Session Beans implemented for web services, within the session beans it makes all the necessary weka prediction calls, and loading in the models. Also does User validation for Admin settings.

### R6 Deploy service components onto enterprise application servers.


Complete? (***Yes***/No)

Using Wildfly 18 server.


### R7 Create web services (SOAP, RESTful) to use the data service components.


Complete? (***Yes***/No)

Using Ajax to make JSON calls to webhooks for prediction data.


### R8 Create web user interface/mobile applications to use the application/web services.


Complete? (***Yes***/No)

Using a Dynamic Web Project to structure html for form submissions and react api calls.

### R9 Test your services, log your services, and document your term project.


Complete? (***Yes***/No)

Documented, Logged and fully functional.

### R10 Demonstrate your term project in final project presentation, slides, short video.


Complete? (***Yes***/No)

[Slide Show](https://docs.google.com/presentation/d/1yZERmDneqt14K1zfs5hBJhNegBFz1f1q6k2aF5D4I5Y/edit?usp=sharing)

