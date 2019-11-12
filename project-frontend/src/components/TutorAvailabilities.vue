<template>
	<div id=tutors>
		<h2>Select a Tutor for the Session</h2><br>
		<label>Available tutors:
			<select id='session-tutor-select' v-model="selectedTutor">
		        <option disabled value="">Please select a tutor</option>
		        <option v-for="(tutor, i) in tutors" v-bind:key="`tutor-${i}`" v-bind:value="{ username: tutor.username, education: tutor.education, hourlyRate: tutor.hourlyRate, experience: tutor.experience, avails: tutor.avails, ratings: tutor.ratings, texts: tutor.texts }">{{tutor.username}}</option>
		    </select>
	    </label>

	    <br> <span style="color:red">{{errorTutor}}</span>

	    <hr>
	    <h2>Tutor Information</h2><br>

	    <table align="center" width="75%">
	    	<tr>
	    		<th>Hourly Rate</th>
	    		<th>Availabilities</th>
	    		<th>Ratings</th>
	    		<th>Comments</th>
	    	</tr>
	    	<tr>
	    		<td>
	    			{{selectedTutor.hourlyRate}}
	    		</td>
	    		<td>
					<select id='session-avail-select' v-model="selectedAvailability">
				        <option disabled value="">Please select an availability</option>
				        <option v-for="(a, i) in selectedTutor.avails" v-bind:key="`a-${i}`" v-bind:value="{ time: a.time, date: a.date }">{{a.date}} {{a.time}}</option>
				    </select>
	    		</td>
	    		<td>
				    <ul>
			            <li v-for="(r, i) in selectedTutor.ratings" v-bind:key="`r-${i}`" style="list-style-type: disc;">
			                <span class='ratings'>{{r.rating}}</span>
			            </li>
			        </ul>
	    		</td>
	    		<td>
				    <ul>
			            <li v-for="(t, i) in selectedTutor.texts" v-bind:key="`t-${i}`" style="list-style-type: disc;">
			                <span class='texts'>{{t.description}}</span>
			            </li>
			        </ul>
	    		</td>
	    	</tr>
	    </table>

	    <br><br>
        <b-button id='select-avail' variant="primary" type='button' v-bind:disabled="!selectedAvailability || !selectedTutor" @click="createSession(selectedTutor.username, selectedAvailability)">Request Session</b-button>

        <hr>
		<b-button id='home-button' variant="outline-secondary" type='button' @click="goHome()">Return</b-button>
		<b-button id='back-button' variant="outline-secondary" type='button' @click="goBack()">Back To Course Selection</b-button>
	</div>
</template>

<script src="./tutoravails.js">
</script>

<style>
  #tutors {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    text-align: center;
    margin-top: 60px;
  }
  table {
  	width: 75%;
  	border-collapse: collapse;
  }

  ul {
  	list-style-type: none;
  	text-align: left;
  }
</style>