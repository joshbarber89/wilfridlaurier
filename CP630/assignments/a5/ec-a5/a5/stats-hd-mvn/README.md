# A5Q1 help
Author: HBF  
Date: 2020-12-01  


This project is a reference design and trouble shooting for A5Q1. 

1. Run TestData.java as Java program to generate the statsdata.txt
2. Export the stats-hd-mvn to Hadoop MapReduce jar by Maven command: `mvn clean package` this will create stats-hd-mvn.jar in the target folder.
3. Open cmd console, cd to the root folder of the stats-hd-mvn project. Run the following commands:

~~~
hadoop fs -put statsdata.txt /ec
hadoop fs -cat /ec/statsdata.txt
~~~

This puts statsdata.txt to HDFS in /ec directory. 

~~~
hadoop jar target/stats-hd-mvn.jar stats.ec.StatsMR /ec/statsdata.txt  /stats
~~~

This submits stats-hd-mvn.jar to Hadoop runtime to do stats computing. 

~~~
hadoop fs -cat /stats/part-r-00000
~~~

This cats the results of computing: stats   100,50.5

4. Run StatsHDFSClient.java as Java application. This client program connects to HDFS and read and parse /stats/part-r-00000, and create StatsSummary object and save it to file hdstats.bin

5. Run StatsQuery.java as Java application. This loads the hadstats.bin and call methods to get the stats info. 

