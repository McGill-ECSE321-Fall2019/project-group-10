<template>
 <div id=selection>

 <h2>Select a Course Offering for a Tutoring Session</h2>
 <br>
 	<table align="center">
 		<tr>
 			<td><label>Universities:</label></td>
 			<td>
		      		<select id='session-university-select' v-model="selectedUniversity">
		       			<option disabled value="">Please select a university</option>
		        		<option v-for="(university, i) in unis" v-bind:key="`university-${i}`" v-bind:value="{ name: university.name, address: university.address }">{{university.name}}</option>
		      		</select>
 			</td>
 			<td>
 			    <b-button id='uni-button' variant="primary" type='button' v-bind:disabled="!selectedUniversity" @click="generateCourses(selectedUniversity.name)">Select University</b-button>
 			</td>
 		</tr>
 		<tr height="30px">
 			<td></td>
 			<td>
 				<span style="color:red">{{errorUniversity}}</span>
 			</td>
 			<td></td>
 		</tr>
 		<tr>
 			<td><label>Courses:</label></td>
 			<td>
			      <select id='session-course-select' v-model="selectedCourse">
			        <option disabled value="">Please select a course</option>
			        <option v-for="(course, i) in courses" v-bind:key="`course-${i}`" v-bind:value="{ courseName: course.courseName, uniName: course.uniName, description: course.description }">{{course.courseName}}</option>
			      </select>
 			</td>
 			<td>
 				<b-button id='course-button' variant="primary" v-bind:disabled="!selectedUniversity || !selectedCourse" type='button' @click="generateCourseOfferings(selectedCourse.courseName, selectedCourse.uniName)">Select Course</b-button>
 			</td>
 		</tr>
 		<tr height="30px">
 			<td></td>
 			<td>
 				<span style="color:red">{{errorCourse}}</span>
 			</td>
 			<td></td>
 		</tr>
 		<tr>
 			<td><label>Course Offerings:</label></td>
 			<td>
			      <select id='session-co-select' v-model="selectedCourseOffering">
			        <option disabled value="">Please select a course offering</option>
			        <option v-for="(co, i) in courseOfferings" v-bind:key="`co-${i}`" v-bind:value="{ term: co.term, year: co.year, id: co.id }">{{co.term}} {{co.year}}</option>
			      </select>
 			</td>
 			<td>
 				<b-button id='selection-button' variant="success" type='button' v-bind:disabled="!selectedUniversity || !selectedCourse || !selectedCourseOffering" @click="submit(selectedCourseOffering.id)">Submit</b-button>
 			</td>			
 		</tr>
 		<tr height="30px">
 			<td></td>
 			<td>
 				<span style="color:red">{{errorCourseOffering}}</span>
 			</td>
 			<td></td>
 		</tr>
    </table>

    <hr>
    <b-button id='home-button' variant="outline-secondary" type='button' @click="goHome()">Return</b-button>

    
 </div>

</template>
<script src="./selection.js">
</script>

<style>
  #selection {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    text-align: center;
    margin-top: 60px;
  }
  table {
  	width: 50%;
  }
  tr, th {
  	text-align: left;
  }
</style>
