<h1>Below will explain how to set up all restful tests</h1>
<br>
<h3>To begin, we created a setup for any database that wants to test our code. This test can be completed manually or automatically with the help from the command line. Below, you will see each request that is made for setup.</h3>
<br>
Starting with: <b>http://localhost:8080/setup/<b>
<ol>
<li><h6>Create User:</h6>1?age=14&name=Dan&email=Dan.B@gmail.com&phonenumber=2920290292</li>
<ul>
<li>age = the age of the user being created | Must be above the age of 12</li>
<li>name = the profile name of the user that will be seen while logged in</li>
<li>mail = the email of the user - Must be an email of the format ****@**.***</li>
<li>phonenumber = the phone number of the user - Must be of the format ***-***-**** || **********</li>
</ul>
<li><h6>Create Tutor:</h6>2?username=k&password=t4&amountPaid=10&hourlyRate=2&experience=4&useremail=Dan.B@gmail.com</li>
<ul>
<li>username = the username is what the tutor will log in with | Must be unique</li>
<li>password = the password is what the tutor will log in with | Must be extra unique</li>
<li>amountPaid = the amount the tutor has earned in total from the company</li>
<li>hourlyRate = the tutor's hourly rate of pay</li>
<li>experience = the tutor's experience tutoring</li>
<li>useremail = the email is what links the user to the tutor</li>
</ul>
</ol>

