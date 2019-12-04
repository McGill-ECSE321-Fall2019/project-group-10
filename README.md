Welcome to the ECSE 321 Group 10 android repository!<br>

<h2>Team Members:</h2>
Taylor Lynn Curtis<br>
Alex Grunwald<br>
Chelsea Myers-Colet<br>
Yoan Poulmarc'k<br>
Anas Shahid<br>

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
