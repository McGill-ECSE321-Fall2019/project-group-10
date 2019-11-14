<template>
	<div id=tutors>

	
    <p>{{errorCourseOffering}}</p>

	<table  align="center">
		<tr>
			<td>
			<p class="loggedIn">Currently logged in as {{username}}</p>
			</td>
			</tr>
	</table>

		<h2 class="display-4">Select a Tutor for the Session</h2><br>
	
			<label class="h5">Available tutors: </label><br>
<span  class="custom-dropdown">
			<select id='session-tutor-select' v-model="selectedTutor">
		        <option disabled value="">Please select a tutor</option>
		        <option v-for="(tutor, i) in tutors" v-bind:key="`tutor-${i}`" v-bind:value="{ username: tutor.username, education: tutor.education, hourlyRate: tutor.hourlyRate, experience: tutor.experience, avails: tutor.avails, ratings: tutor.ratings, texts: tutor.texts }">{{tutor.username}}</option>
		    </select>
	    
</span>
	    <br> <span style="color:red">{{errorTutor}}</span>
<br>
	    <hr>
	    <h2 class="h1">Tutor Information</h2><br>

<div class="limiter">
	<div class="container-table100">
		<div class="wrap-table100">
			<div class="table100">
				<table>
					<thead>
						<tr class="table100-head">
						</tr>
						<tr class="table100-head">
							<th class="column1">Hourly Rate</th>
							<th class="column2">Availabilities</th>
							<th class="column3">Ratings</th>
							<th class="column4">Comments</th>
						</tr>
					</thead>


					<tbody>
						<tr>
							<td class="column1"> {{selectedTutor.hourlyRate}}</td>

							<td class="column2"> <select id='session-avail-select' class="custom-dropdown" v-model="selectedAvailability"> 
				        		<option disabled value="">Please select an availability</option>
				        		<option v-for="(a, i) in selectedTutor.avails" v-bind:key="`a-${i}`" v-bind:value="{ time: a.time, date: a.date }">{{a.date}} {{a.time}}</option>
				    			</select></td>

							<td class="column3"> <ul>
			            		 <li v-for="(r, i) in selectedTutor.ratings" v-bind:key="`r-${i}`" style="list-style-type: disc;"> 
			               		 <span class='ratings'>{{r.rating}}</span>
			           			 </li>
			       				 </ul></td>

							<td class="column4"> <ul>
			            		 <li v-for="(t, i) in selectedTutor.texts" v-bind:key="`t-${i}`" style="list-style-type: disc;"> 
			                	 <span class='texts'>{{t.description}}</span>
			            		 </li>
			       				 </ul></td>
							
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

	    <br><br>
        <b-button id='select-avail' variant="primary" type='button' v-bind:disabled="!selectedAvailability || !selectedTutor" @click="createSession()">Request Session</b-button>

        <hr>
		<b-button id='home-button' variant="outline-secondary" type='button' @click="goHome()">Return</b-button>
		<b-button id='back-button' variant="outline-secondary" type='button' @click="goBack()">Back To Course Selection</b-button>

		<p>{{errorSession}}</p>
		</div>
	
</template>

<script src="./tutorsavail.js">
</script>

<style scoped>

	.loggedIn{
		text-align: right;
		color: #4b4b4b;
		opacity: 0.6;
	}

  #tutors {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    text-align: center;
    margin-top: 60px;
  }
  table {
  	width: 100%;
  	border-collapse: collapse;
  }
  tr, td, th {
  	text-align: center;

  }
  ul {
  	list-style-type: none;
  	text-align: center;
  }

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

label{
	margin-bottom: 0;
}



</style>