<template>
	<div>

		<p class="currentLogin">Currently logged in as {{username}}</p>

		<h1> Welcome to your Home Page</h1>

		<!-- Starting up -->
    <div class="wrapper">
        <div id="formContent">
            <!-- Tabs Titles -->


            <!-- Login Form -->
			<div class="profile-details">
           
				<h2 class="active underlineHover"> Profile Details </h2>
				
<table>
<tr>
  <td><label>Username: </label></td>
  <td><input type="text" id="username" name="login" :placeholder="username" disabled> </td>
  <td></td>
</tr>

<tr>
  <td><label>Email: </label></td>
  <td><input type="text" id="Email" name="login" :placeholder="User.email" disabled></td>
  <td></td>
</tr>


<tr>
  <td><label>Name: </label></td>
  <td><input id="name" type="text" v-model="newName" :placeholder="User.name"> </td>
  <td><b-button id='update_name' variant="outline-primary" type='button' v-bind:disabled="!newName" @click="updateName(newName)">Update</b-button></td>
</tr>

<tr>
  <td><label>Age: </label></td>
  <td>	<input id="age" type="text" v-model="newAge" :placeholder="User.age"> </td>
  <td>	<b-button id='update_age' variant="outline-primary" type='button' v-bind:disabled="!newAge" @click="updateAge(newAge)">Update</b-button></td>
</tr>

<tr>
  <td><label>Phone Number: </label></td>
  <td>	<input id="phone" type="text" v-model="newNumber" :placeholder="User.phoneNumber"> </td>
  <td>	<b-button id='update_number' variant="outline-primary" type='button' v-bind:disabled="!newNumber" @click="updateNumber(newNumber)">Update</b-button></td>
</tr>

</table>


            <div id="formFooter">
                    <a class="underlineHover">{{errorUpdate}}</a>
            </div>

        	</div>
        </div>
	</div>
    
<!-- Ending -->

<!-- Booking starts here -->
<div class="limiter">
	<div class="container-table100">
		<div class="wrap-table100">
			<div class="table100">
				<table>
					<thead>
						<tr class="table100-head">
						</tr>
						<tr class="table100-head">
							<th class="column1">Room</th>
							<th class="column2">Tutor</th>
							<th class="column3">Course Offering</th>
							<th class="column4">Date/Time</th>
							<th class="column4">Amount Owed</th>
						</tr>
					</thead>

<!-- Start your update from here -->
					<tbody>
						<tr v-for="(session, i) in sessions2" v-bind:key="`session-${i}`">
							<td class="column1"> {{session.room}} tbd </td>

							<td class="column2"> {{session.tutorDTO.username}}</td>

							<td class="column3"> {{session.courseOfferingDTO.courseName}} {{session.courseOfferingDTO.term}} {{session.courseOfferingDTO.year}} </td>

							<td class="column4"> {{session.date}} {{session.time}}</td>

							<td class="column5"> {{session.amountPaid}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

    <div class="limiter">
      <h5>Select a Session to Cancel</h5>
      <select id='session-select' v-model="selectedSession">
		    <option disabled value="">Selected a session to cancel</option>
		    <option v-for="(session, i) in sessions2" v-bind:key="`session-${i}`" v-bind:value="{id: session.sessionid}">Tutor: {{session.tutorDTO.username}} | Date: {{session.date}} | Paid: ${{session.amountPaid}}</option>
		  </select>
      <b-button type="button" class="btn btn-info" v-bind:disabled="!selectedSession" @click="deleteS()">Cancel</b-button>
      <p>{{errorSession}}</p>
    </div>


<!-- Booking ends here -->


		<hr>
		<b-button id='review' variant="outline-primary" type='button' @click="goToReview()">Write a Review</b-button>

		<b-button id='session' variant="outline-success" type='button' @click="goToSession()">Create a Session</b-button>

		<b-button id='logout' variant="outline-secondary" type='button'  @click="logout()">Logout</b-button>
	</div>
</template>

<script src="./home.js">
</script>

<style scoped>
.currentLogin{
	text-align: right;
	opacity: 0.6;
}

.wrapper {
  display: flex;
  align-items: center;
  flex-direction: column; 
  justify-content: center;
  width: 100%;
  min-height: 100%;
  padding: 20px;
}

#formContent {
  -webkit-border-radius: 10px 10px 10px 10px;
  border-radius: 10px 10px 10px 10px;
  background: #fff;
  padding: 30px;
  width: 90%;
  max-width: 750px;
  position: relative;
  padding: 0px;
  -webkit-box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
  box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
  text-align: center;
}

#formFooter {
  background-color: #f6f6f6;
  border-top: 1px solid #dce8f1;
  padding: 25px;
  text-align: center;
  -webkit-border-radius: 0 0 10px 10px;
  border-radius: 0 0 10px 10px;
}

input[type=text] {
  background-color: #f6f6f6;
  border: none;
  color: #0d0d0d;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 5px;
  width:90%;
  border: 2px solid #f6f6f6;
  -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

input[type=text]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type=text]:placeholder {
  color: #cccccc;
}

.available{
	visibility: hidden;
}

h2.active {
  color: #494949;
  border-bottom: 2px solid #5fbae9;
}

.underlineHover:after {
  display: block;
  left: 0;
  bottom: -10px;
  width: 0;
  height: 2px;
  background-color: #56baed;
  content: "";
  transition: width 0.2s;
}

.underlineHover:hover {
  color: #0d0d0d;
}

.underlineHover:hover:after{
  width: 100%;
}



  /* Booking Page */
  .limiter {
  width: 100%;
  margin: 0 auto;
  
}

.container-table100 {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  padding: 33px 30px;
  padding-top: 0;
 
}

.wrap-table100 {
  width: 1170px;
}

table {
  border-spacing: 1;
  border-collapse: collapse;
  background: white;
  border-radius: 10px;
  overflow: hidden;
  width: 100%;
  margin: 0 auto;
  position: relative;
}
table * {
  position: relative;
}
table td, table th {
  padding-left: 8px;
}
table td, table th:last-child {
  padding-right: 18px;
}
table thead tr {
  height: 60px;
  background: #3abbf7;
}
table tbody tr {
  height: 50px;
}
table tbody tr:last-child {
  border: 0;
}
table td, table th {
  text-align: left;
}
table td.l, table th.l {
  text-align: right;
}
table td.c, table th.c {
  text-align: center;
}
table td.r, table th.r {
  text-align: center;
}


.table100-head th{
  font-family: OpenSans-Regular;
  font-size: 18px;
  color: #fff;
  line-height: 1.2;
  font-weight: unset;
}

tbody tr:nth-child(even) {
  background-color: #f5f5f5;
}

tbody tr {
  font-family: OpenSans-Regular;
  font-size: 15px;
  color: #808080;
  line-height: 1.2;
  font-weight: unset;
}

tbody tr:hover {
  color: #555555;
  background-color: #f5f5f5;
  cursor: pointer;
}

.column1 {
  width: 260px;
  padding-left: 40px;
}

.column2 {
  width: 160px;
}

.column3 {
  width: 245px;
}

.column4 {
  width: 110px;
  text-align: right;
}

.column5 {
  width: 170px;
  text-align: right;
}

li{
	list-style-type: none;
}


.custom-dropdown{
  position: relative;
  display: inline-block;
  vertical-align: middle;
  margin: 10px; /* demo only */
}
.custom-dropdown option{
	color: aliceblue;
}
.custom-dropdown select {
  background-color: #3abbf7;
  color: #fff;
  font-size: inherit;
  padding: .5em;
  padding-right: 2.5em; 
  border: 0;
  margin: 0;
  border-radius: 3px;
  text-indent: 0.01px;
  text-overflow: '';
  -webkit-appearance: button; /* hide default arrow in chrome OSX */
}
.custom-dropdown::before,
.custom-dropdown::after {
  content: "";
  position: absolute;
  pointer-events: none;
}

.custom-dropdown::after { /*  Custom dropdown arrow */
  content: "\25BC";
  height: 1em;
  font-size: .625em;
  line-height: 1;
  right: 1.2em;
  top: 50%;
  margin-top: -.5em;
}

.custom-dropdown::before { /*  Custom dropdown arrow cover */
  width: 2em;
  right: 0;
  top: 0;
  bottom: 0;
  border-radius: 0 3px 3px 0;
}

.custom-dropdown select[disabled] {
  color: rgba(180, 180, 180, 0.3);
}

.custom-dropdown select[disabled]::after {
  color:  rgba(180, 180, 180, 0.3);
}

.custom-dropdown::before {
  background-color: rgba(0,0,0,.15);
}

.custom-dropdown::after {
  color: rgba(0,0,0,.4);
}
 select{
  background-color: #56baed;
  border: none;
  color: white;
  padding: 15px 40px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  text-transform: uppercase;
  font-size: 13px;
  -webkit-box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
  box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
  margin: 5px 20px 40px 20px;

}
/*  */
</style>