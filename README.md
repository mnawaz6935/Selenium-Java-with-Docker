# Selenium Java Web Automation for Amazon
# Prerequisite 
  - JDK (v1.8) or Latest version [link](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html) 
  - Maven (v3.8.1) or Latest version [link](https://maven.apache.org/download.cgi) 
  - Eclips IDE For Java Developers [link](https://www.eclipse.org/downloads/)
  - Docker Desktop [link](https://www.docker.com/products/docker-desktop/)	
  
# Setup
### Install the JDK, Maven, Allure and Eclips
##### JDK 
  - [Download JDK](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html)
  - Install JDK file 
  - Set environment variable for JDK bin folder and restart system
  - Verify JDK installation by running following command `java -version`
  
##### Maven 
  - [Download  Maven](https://maven.apache.org/download.cgi) 
  - Copy path to maven bin folder
  - Set environment variable for maven and restart system
  - To check the Maven version by running following command `mvn --version`
  
##### Eclips
  - [Download Eclips](https://www.eclipse.org/downloads/)
  - Install Eclips
  
##### Docker Desktop 
  - [Download  Docker Desktop](https://docs.docker.com/desktop/install/windows-install/)
   - Install Docker Desktop

# Headless - How to Run The Tests in Headless Mode

1. Clone the repo
2. Open the project in Eclipse/Intellij
3. Open project explorer.
4. Update pom.xml maven file.
5. Go to the TestNG file name runTest.xml. You can find it following path : /Amazon-qa-web/runTest.xml
6. Right click in the open file and select Run As > TestNG Suite
7. The tests will start running.

# UI - How to Run The Tests in UI Mode

1. Go to file src/test/main/base/BaseClass.java
2. Go to function 'initConfiguration()'.
3. Comment/Remove the line : 
options.addArguments("--headless");
4. Go to the TestNG file name runTest.xml in the /Amazon-qa-web/src/test/resources/runner/runTest.xml
5. Right click in the open file and select Run As > TestNG Suite
6. The tests will starts running in UI Mode.

# How to View Report

1. The report is generated in the HTML format.
2. Refresh project.
3. Go to 'reports' folder.
4. Open the file named "Extent_Report.html" in your favourite browser.
5. The Report will be shown with screenshots.

# Run via docker

1. Open Docker Desktop
2. Run the Command 'docker-compose -f docker-compose.yml up -d'
3. Run the Command 'mvn clean install'
