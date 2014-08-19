App55 Java Library
==================

Please see https://www.app55.com/docs for information on the service endpoints.

To build use : mvn clean install  
To run the tests setup the environment variables `APP55_API_KEY` and `APP55_API_SECRET` with your sandbox api key and secret,  
to run just the tests use : mvn test  
To run just the integration tests use : mvn -Dtest=IntegrationTest test  

To import the project into Eclipse, you will want to run:  
mvn eclipse:eclipse  
in the project directory first. Then import into eclipse as an 'existing maven project'.  

An .iml project file is included for adding the project to Intellij.  

Remember to set the api key and secret in the TestConfiguration or in your IDE run/debug 
configurations, if you want to to run/debug the project within Eclipse.  

Revision History
----------------
* 0.8.10&nbsp; Update for card valid flag
* 0.8.9&nbsp;&nbsp; Update for user deactivate option
* 0.8.8&nbsp;&nbsp; Fix ApiException not thrown for appropriate HTTP errors
* 0.8.7&nbsp;&nbsp; Add cancelTransaction method
* 0.8.6&nbsp;&nbsp; Jar now oss.sonatype.org Maven repo and synced to the central Maven repo.
* 0.8.4&nbsp;&nbsp; Now supports asynchronous requests and multiple HTTP transport mechanisms.
* 0.8.3&nbsp;&nbsp; Now supports Scheduled payments.
* 0.8.2&nbsp;&nbsp; Initial library release. For App55 Release 2.8.0

Maven
-----
If you use Maven you can reference this project like this. The jars are available from the oss.sonatype.org and periodically synced to the central Maven repo.

    <dependency>
        <groupId>app55</groupId>
        <artifactId>app55-java</artifactId>
        <version>0.8.6</version>
    </dependency>

Dependencies
------------
* [Jackson](http://wiki.fasterxml.com/JacksonDownload) Core / Mapper 1.9.x
