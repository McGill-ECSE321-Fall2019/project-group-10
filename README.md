Welcome to the ECSE 321 Group 10 GitHub repository!<br>

<h2>Team Members:</h2>
Taylor Lynn Curtis<br>
Alex Grunwald<br>
Chelsea Myers-Colet<br>
Yoan Poulmarc'k<br>
Anas Shahid<br>

<h2>Important</h2>
For deliverable 4, there are 3 important branches that all have different pieces of the project for simplicity of testing.
<ol>
  <li> master : this branch contains the completed website application for marking
  <li> android : this branch contains the completed android application for marking
  <li> face_recog : this branch contains our facial recog extra feature and should only be used to test this feature
</ol>

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
    <td>20</td>
    <td>21</td>
  </tr>

  <tr>
    <td>Chelsea M-C</td>
    <td>Documentation Manager</td>
    <td>20</td>
    <td>50</td>
    <td>40</td>
    <td>25</td>
  </tr>

  <tr>
    <td>Taylor Lynn Curtis</td>
    <td>Software Consulting Lead</td>
    <td>19</td>
    <td>58</td>
    <td>27</td>
    <td>2</td>
  </tr>

  <tr>
    <td>Anas Shahid</td>
    <td>Software Developer</td>
    <td>11</td>
    <td>49</td>
    <td>40</td>
    <td>20</td>
  </tr>

  <tr>
    <td>Yoan Poulmarc'k</td>
    <td>Software Architecture Lead</td>
    <td>17</td>
    <td>51</td>
    <td>10</td>
    <td>2</td>
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
<h3>Sprint 3</h3>
<ul>
  <li>Alex:
  <ul>
      <li> Review page frontend </li>
      <li> Linking review page to backend services </li>
    </ul>
  </li>
  <li>Chelsea:
    <ul>
      <li> Responsible for meeting minutes and creating templates for the wiki pages and general documentation upkeep</li>
      <li> Initial University, Course, Course offering and Tutor selection page for web frontend </li>
      <li> Linking login, startup, sign up, home, selection and tutor pages to backend services </li>
    </ul>
  </li>
  <li>Taylor:
    <ul>
      <li> Fix sessions backend, and other backend issues that cause errors in the frontend </li>
      <li> Linking travis CI to frontend </li>
      <li> Linking Heroku frontend, setting up npm dev build and managing auto deploy </li>
    </ul>
  </li>
  <li>Anas:
      <ul>
      <li> Startup, login, sign up, home, selection and tutor page frontend (visually pleasing versions) </li>
    </ul>
    </li>
  <li>Yoan:
      <ul>
        <li> Created Heroku frontend application </li>
        <li> Responsible for debugging file system/structural errors</li>
        <li> Set up automated email notifications (check spam)</li>
    </ul>
    </li>
</ul>
<h3>Sprint 4</h3>
<ul>
  <li>Alex:
  <ul>
      <li> Human detection algorithm integration </li>
      <li> Android backend integration</li>
    </ul>
  </li>
  <li>Chelsea:
    <ul>
      <li> Responsible for meeting minutes and general documentation upkeep as well as user web and Android documentation</li>
      <li> Android backend integration and comments </li>
    </ul>
  </li>
  <li>Taylor:
    <ul>
      <li> Code cleanup and comments for Android </li>
    </ul>
  </li>
  <li>Anas:
      <ul>
        <li> Android views</li>
        <li> Android backend integration</li>
    </ul>
    </li>
  <li>Yoan:
      <ul>
        <li> Fixing heroku backend deploy </li>
    </ul>
    </li>
</ul>

<br>To see our meeting minutes, including key design decisions and architecture
framework, head over to our wiki page and select any page titled with a date:<br>
&nbsp;&nbsp;&nbsp;&nbsp;<h2><b><a href="https://github.com/McGill-ECSE321-Fall2019/project-group-10/wiki">Group 10 Wiki</a></b></h2>

<h2>Extra Features</h2><br>
<ol>
  <li>Machine learning face recognition for signing up. See below for ML setup.
  <li>Email notification upon session creation. See below for email setup.
</ol>
<br>

<h2>Instructions for testing Machine Learning algorithm</h2><br>
<ol>
  <li> The machine learning extension is only added to the face_recog branch as we did not want the software to interfere with basic front end testing for the reviewer. This branch should only be used to test the algorithm.
  <li> Before starting, make sure you have opencv installed on your computer. If it is not, use this on your command line to install <br><b>pip install opencv-python</b>
  <li> You may also use brew if that is already downloaded using this command:<br><b>brew install opencv</b>
  <li> If you do not have pip installed, click <a href="https://l.facebook.com/l.php?u=https%3A%2F%2Fgist.github.com%2Fhaircut%2F14705555d58432a5f01f9188006a04ed%3Ffbclid%3DIwAR0tZ6RvZMkMHSFf1PW2XG3N7xpi_RnOsVgCb1BTSsT1hYG1IdFTxK3XWIk&h=AT2nhYVBVNF5dyDVCxgeKH3acaF9u-TrbBpIjVE-FH4MOcYK8Wy-KU7Uxme3irL5Ob5RlLMKN7DUUtctBysehoeyEHAfgsWHSjVWvv53po8Y5cto7V6AKktN3jBox5Q-7DinCbLD9ug">here</a> for instructions on how to download it for MacOS and how to set path.
  <li> Make your way into the face_recog branch by running -> git checkout face_recog on the cmdline. Here you will find one additional folder called -> facial_recognition where all scripts are held.
  <li> To provide a quick explanation how it works:
    <ol>
      <li> To start the script, make your way into the facial_recognition folder. Here, you will have to run the script runScript.py (very original name) with the command -> python runScript.py. Python must be installed in order for you to run this. If it is not installed, click <a href="https://www.codecademy.com/articles/install-python">here</a> for instructions.
      <li> A prompt will be shown asking to access your computer. 
      <li> The algorithm will use your camera to take a picture of you to check to see if you are a human. 
      <li> You need to make sure you have set your computer's cameras permission to allow terminal access. To do so in mac, go into your system preferences > security and privacy > privacy. In the left tab select 'Camera'. Find terminal in the right tab and make sure it is checked for permissions. If it is checked, you can run the script.
      <li> If you are using a windows system, find the link <a href="https://support.microsoft.com/en-ca/help/4468232/windows-10-camera-microphone-and-privacy">here</a> for more information on terminal permissions for accessing your camera.
      <li> DO NOT SMILE FOR THE PICTURE.
      <li> Once the picture is taken, an algorithm runs on the image taken and attempts to find all objects in the picture including all people.
      <li> Once complete, a file called -> prediction.txt will be generated. In this file will be written everything the algorithm detected. 
  </ol>
  <li> Once this is done, in the front end, after making your way to the home screen, click on sign up. (Make sure you run -> npm run dev in the face_recog branch).
  <li> The algorithm is meant to detect individuals who are signing up to make sure they are human.
  <li> The button -> confirm identity, will run the script in the future (issues with running pythons script form java). If you press the button now (after running the script), a restful api call will be sent to the service class and will read from the prediction.txt file and see what objects were detected. 
  <li> If you are more than 65% human, you will be detected and you will be able to sign up for an account once all information is added.
</ol>
  
<h2>Instructions for testing and setting up email on Eclipse</h2><br>
<ol>
  <li> If the email class poses erorrs on Eclipse do the following on the git repo on command line: </li>
  <li> gradle cleanEclipse </li>
  <li> gradle Eclipse </li>
  <li> gradle build -x test </li>
  <li> Once running the commands as seen above, the email is ready to be used.
  <li> The email will only be sent to a registered tutor upon session creation. If you would like to test it, change one line in the code, run the program by running the tesObjects test to set up mock varaibles as seen below, and then create a session.
  <li>
    <ol>
      <li> First, we must change the email location. Make your way into the file JavaEmail.java with this path -> /Project-Backend/src/test/java/ca/mcgill/ecse321/project/service/TestObjects.java
      <li> Open the file and make your way to line 51. This is where the tutor email is set. Replace the dummy email with your own, save the file, rerun the spring server and then run the testObject test as seen in the instructions below.
      <li> You have now created a tutor with your email. Run the appliction Frontend as instructed below. Sign in with username: cmc and password: dogs (quick access opposed to making a new account), click on create session and choose the tutor who's email you just changed. Confirm session creation and you should receive an email from project-group-10.
    </ol>
  </li>
</ol>

<h2>Instructions for setting up Android and Heroku</h2><br>
<ol>
  <li>Run the Spring backend by going to https://project-backend-10.herokuapp.com.</li>
  <li>Run the Android application using a Pixel 2 emulator.</li>
  <li>To test Android with test objects using the Spring Boot application, follow steps below in "Instructions for setting up objects to test the frontend web service". Do this only after running the Spring backend on Heroku but before running the Android emulator. To check the creation, go to the /students endpoint which should display a student with the username "cmc" and password "dogs" which can be used to log in. This also creates a session and a tutor will one availability for creating another session</li>
  <li> To do all this on command line after opening the Heroku backend website: 
    <ol> 
      <li> Navigate to the git repository and proceed into the Project-Backend folder </li>
      <li> Switch branches: git checkout CreateObjectsREST </li>
      <li> Run gradle to generate the snapshot file: gralde build -xtest </li>
      <li> Run the Spring backend: java -jar ./build/libs/Project-Backend-0.0.1-SNAPSHOT.jar </li>
      <li> Open a new terminal into the same directory. </li>
      <li> Create the objects using a POST command: curl -X POST http://localhost:8080/createobjects/ </li>
      <li> Check that objects were created by going to the students endpoint on Heroku: https://project-backend-10.herokuapp.com/students </li>
      <li> Stop running spring in terminal: Ctrl+C </li>
      <li> Switch to the android branch: git checkout android </li>
      <li> Now you can open Android studio and interact with the app on a Pixel 2 </li>
    </ol>
  </li>
</ol>

<h2>Instructions for setting up objects to test the frontend web service</h2><br>
<ol>
  <li>Run the Spring backend.</li>
  <li>Run the class TestObjects.java in src/test/java/ca/mcgill/ecse321/project/service as a JUnit test with the purpose string set to "create".</li>
  <li>This will create test objects which can be used to interact with the frontend.</li>
</ol>
<h2>Setting up frontend</h2>
<ol>
  <li>Make your way into the Frontend directory -> cd ./project-group-10/Project-Frontend
  <li>Run "npm install"
  <li>Once the installation is complete, run "npm run dev" in the frontend directory.
  <li>Your default internet browser will load with the introduction page. Use Chrome for the best visual.
  <li>To log in, you can use the username "cmc" with password "dogs".</li>
</ol>

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
</ol>
