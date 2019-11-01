Welcome to the ECSE 321 Group 10 GitHub repository!<br>

<h2>Team Members:</h2>
Taylor Lynn Curtis<br>
Alex Grunwald<br>
Chelsea Myers-Colet<br>
Yoan Poulmarc'k<br>
Anas Shahid<br>

<h2>Scope of Project:</h2><br>
The goal of our project is to create the student perspective of a tutoring application. 
This will be accessible via an app on any Android device. The application
will allow students to select a course and view all available tutors, their
hourly rates, availabilities and reviews from other students.<br>

<h2>Overview Table</h2>
Below can be found the role of each member and the number of hours spent on each sprint.

<table>
  <tr>
    <th><b>NAME</th> 
    <th><b>ROLE</th>
    <th><b>SPRINT1</th>
    <th><b>SPRINT2</th>
    <th><b>SPRINT3</th>
    <th><b>SPRINT4</th>
  </tr>

  <tr>
    <td>Alex Gruenwald</td>
    <td>Technical(ly) Intern</td>
    <td>19</td>
    <td>53</td>
    <td>[tbh]</td>
    <td>[tbh]</td>
  </tr>
  
  <tr>
    <td>Chelsea M-C</td>
    <td>Documentation Manager</td>
    <td>20</td>
    <td>50</td>
    <td>[tbh]</td>
    <td>[tbh]</td>
  </tr>
  
  <tr>
    <td>Taylor Lynn Curtis</td>
    <td>Software Consulting Lead</td>
    <td>19</td>
    <td>58</td>
    <td>[tbh]</td>
    <td>[tbh]</td>
  </tr>
  
  <tr>
    <td>Anas Shahid</td>
    <td>Software Developer</td>
    <td>11</td>
    <td>49</td>
    <td>[tbh]</td>
    <td>[tbh]</td>
  </tr>
  
  <tr>
    <td>Yoan Poulmarc'k</td>
    <td>Software Architecture Lead</td>
    <td>17</td>
    <td>51</td>
    <td>[tbh]</td>
    <td>[tbh]</td>
  </tr>
</table>

<h2>Tasks By Deliverable</h2>
<h3>Sprint 1</h3>
<ul>
  <li>Alex: Responsible for UML Lab modelling and generating of model code. Proofreading expert. </li>
  <li>Chelsea: Responsible for meeting minutes and writing persistence tests.</li>
  <li>Taylor: Responsible for dependency injections and writing JPA Tag code.</li>
  <li>Anas: Responsible for writing tests and fixing bugs.</li>
  <li>Yoan: Set up database and connected other applications together.</li>
</ul>
<h3>Sprint 2</h3>
<ul>
  <li>Alex: Business methods, restful services and unit tests for R13, R14, and R15. </li>
  <li>Chelsea:</li>
    <ul 1>
      <li> Responsible for meeting minutes and creating templates for the wiki pages</li>
      <li> Business methods, restful services and unit tests for R2, R3, and R4. </li>
    </ul 1>
  <li>Taylor: Business methods, restful services and unit tests for R5, R7, and R8.</li>
  <li>Anas: Business methods, restful services and unit tests for R10, R11, and R12.</li>
  <li>Yoan: Business methods, restful services and unit tests for R1, R6, and R9.</li>
</ul>

<br>To see our meeting minutes, including key design decisions and architecture
framework, head over to our wiki page and select any page titled with a date:<br>
&nbsp;&nbsp;&nbsp;&nbsp;<h2><b><a href="https://github.com/McGill-ECSE321-Fall2019/project-group-10/wiki">Group 10 Wiki</a></b></h2>

<h2>Instructions for setting RESTful APIs and running Spring</h2><br>
<ol>
<li>To start testing the RESTfula apis, we must first begin running of Spring.
<li>First, open up your SpringToolSuiteIDE and open your designated workspace.
<li>Import the required project.
<li>Make your way into the project-group-10/Project-Backend/src/main/java/ca/mcgill/ecse321/project/ProjectApplication.java
<li>Start running the Spring application "Spring Boot App". Once it is loaded, you can begin running the restful tests.
<li>We have chosen to test our RESTful api tests using Postman.
<li>Once in the wiki page, follow the steps for setting up the mock database to actually test the PUT and GET queries / statements.
<li>Click on <b><a href="https://github.com/McGill-ECSE321-Fall2019/project-group-10/wiki/RESTful-API-Tests-using-a-Client-(Postman)">here</a></b> to access this page.
</ol>

<h2>Instructions for email on Eclipse</h2><br>
<ol>
  <li> If the email class poses erorrs on Eclipse do the following on the git repo on command line: </li>
  <li> gradle cleanEclipse </li>
  <li> gradle Eclipse </li>
  <li> gradle build -x test </li>
</ol>

<h2>Instructions for setting up the tests</h2><br>
<ol>
<li>Once you have successfully cloned the repository, make your way into the project folder.<br>
<li>In the terminal, enter the "Project-Backend" folder by typing in - cd Project-Backend/ <br>
<li>Once in the folder, type and enter - "gradle build". <br>
<li>This should automatically start running and testing all tests written in the test directory.
<li>To run gradle without tests: "gradle -xtest"
</ol>

<h2>Heroku Setup</h2><br>
<ol>
  <li>All the Heroku information is set in the "resource" directory, in the "application.properties" folder.</li>
  <li>The Heroku database information can be found <a href="https://github.com/McGill-ECSE321-Fall2019/project-group-10/blob/master/Project-Backend/src/main/resources/application.properties">here.</a>
  <li>The password and username information is not disclosed in the readme for privacy reasons. </li>
<ol>
