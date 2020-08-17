# Applying MapReduce to Common Data Problems
---
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Using MapReduce to perform numeric summarizations, conditional filtering and sorting and finally, building inverted indexes that can be used with search engines

### Tech
---
All MapReduce codes are written in Java and use the Apache Hadoop Common JARs version 2.7.7. These codes are, however, compatible with most Hadoop 2.0+ JARs (Not sure about Hadoop 3.0+).
Steps to install the JARs and run the tasks:
1. Download Hadoop 2.0+ (< Hadoop 3.0) JARs from any mirror.
2. Unzip the tar file and open up a new project in any IDE.
3. Navigate to the project dependecies pane (For IntelliJ, it is under Project Structure).
4. Click on Add Dependencies/JARs; you want to add the directories
    * hadoop-2.x.x/share/hadoop/common
    * hadoop-2.x.x/share/hadoop/hdfs
    * hadoop-2.x.x/share/hadoop/mapreduce
    * hadoop-2.x.x/share/hadoop/yarn
    * hadoop-2.x.x/share/hadoop/common/lib
5. Download Windows binaries from corresponding builds. [[Reference Repository]](https://github.com/cdarlint/winutils)
6. Copy the corresponding bin folder to hadoop-2.x.x/share/hadoop/common directory.
7. Now create a new directory named "winutils"; I make this directory under C:/winutils
8. Navigate inside and make a sub-directory bin.
9. Copy winutils.exe and hadoop.dll from winutils-master/hadoop-2.x.x/bin to C:/winutils/bin.
10. Add a new system variable HADOOP_HOME and point it to the C:/winutils directory.
11. Also, edit the system PATH variable and add %HADOOP_HOME%/bin to the list.
12. Restart your system and run the codes.

### Task Walkthrough
---
1. Numeric Summarization
Given a raw data dump of US 1990 Census dataset (taken from UC Irwine archive), the task is to find the average number of working hours per week for people with different marital status. 
The solution includes a Mapper that emits { marital_status,(sum,count) } pairs. This is further read by a Combiner that, again, emits { marital_status,(sum,count) } pairs. The Reducer reads in these pairs and writes out { marital_status,avg_working_hours } to the output file.
Code for this task is under [src/com/mapreduce/examples/numericsummerization](https://github.com/JimilProgGrammer/MapReduce_Examples/tree/master/src/com/mapreduce/examples/numericsummerization)
---
2. Conditional Filtering
Given sample data of purchases, the task is to find all purchase records where the purchase is of a particular category. 
The solution includes a Mapper that filters records on the purchase category and emits { text_record, null } pairs. The Reducer is an identity function that simply writes out these pairs to the output file.
Code for this task is under [src/com/mapreduce/examples/filtering](https://github.com/JimilProgGrammer/MapReduce_Examples/tree/master/src/com/mapreduce/examples/filtering)
---
3. Filter Distinct
Given a list of search terms, the task is to filter out distinct terms from the list and output those to a file. 
The solution includes a Mapper that simply writes out each term with a null value as the output. So the Mapper emits { search_term, NULL } pairs. The Reducer receives each key only once with an Iterable of NULL values. Thus, the Reducer simply writes out { distinct_term, NULL } pairs to the result.
Code for this task is under [src/com/mapreduce/examples/filterdistinct](https://github.com/JimilProgGrammer/MapReduce_Examples/tree/master/src/com/mapreduce/examples/filterdistinct)
---
4. Get Top N terms
Given a sample social network data dump with user IDs and number of followers, the task is to get the top 3 influential users on the network along with a number of their followers.
Each Mapper maintains a priority queue to get top 3 users from it's own partition of the social network dump. This is written out to the intermediate result file on cleanup() operation of each Mapper. Thus, Mapper emits { NULL, <UserID, Followers> } pairs. Further, a single Reducer is run that gets localized top 3 users from each Mapper. It again maintains a priority queue and follows a similar approach as the Mapper to extract the global top 3 users and writes out { NULL,<UserID, Followers> } pair to the output.
Code for this task is under [src/com/mapreduce/examples/topn](https://github.com/JimilProgGrammer/MapReduce_Examples/tree/master/src/com/mapreduce/examples/topn)
---
5. Inverted Indexes
Given a corpus of documents (source code files in our case), build an inverted index that maintains each word and all the source code files that contain that particular word to enable very fast searches.
Each Mapper writes out { term,filename } pairs as the intermediate result. The Reducer receives an iterable of filenames for each term and it simply concatenates those filenames, delimitted with the "|" symbol and writes out { term,list_of_files } pairs to the output.
Code for this task is under [src/com/mapreduce/examples/invertedindex](https://github.com/JimilProgGrammer/MapReduce_Examples/tree/master/src/com/mapreduce/examples/invertedindex)


[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
