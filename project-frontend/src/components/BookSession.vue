<template>
 <div id=selection>

	<table width="100%" align="left">
		<tr>
			<td>
			<p>Currently logged in as {{username}}</p>
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>

     <div class="wrapper">
            <div id="formContent">
            <!-- Tabs Titles -->
            <h2  class="active"> Book A Session </h2><br>
            

            <!-- Login Form -->
        
              <label>University: </label> <br>
              <select id='session-university-select' v-model="selectedUniversity" @change="generateCourses(selectedUniversity.name)">
		       		  <option disabled value="">Please select a university</option>
		        		<option v-for="(university, i) in unis" v-bind:key="`university-${i}`" v-bind:value="{ name: university.name, address: university.address }">{{university.name}}</option>
		      		</select>
                <br>
              <label>Courses: </label> <br>
              <select id='session-course-select' v-model="selectedCourse"  @change="generateCourseOfferings(selectedCourse.courseName, selectedCourse.uniName)">
                <option disabled value="">Please select a course</option>
                <option v-for="(course, i) in courses" v-bind:key="`course-${i}`" v-bind:value="{ courseName: course.courseName, uniName: course.uniName, description: course.description }">{{course.courseName}}</option>
			        </select>
                <br>
              <label>Course Offerings: </label> <br>
              <select id='session-co-select' v-model="selectedCourseOffering">
                <option disabled value="">Please select a course offering</option>
                <option v-for="(co, i) in courseOfferings" v-bind:key="`co-${i}`" v-bind:value="{ term: co.term, year: co.year, id: co.id }">{{co.term}} {{co.year}}</option>
			        </select>
                <br><br>
              <b-button id='selection-button' variant="success" type='button' v-bind:disabled="!selectedUniversity || !selectedCourse || !selectedCourseOffering" @click="submit(selectedCourseOffering.id)">Submit</b-button>
                <br>
                <br>
            <div id="formFooter">
                    <a v-bind:disabled="!errorUniversity" class="underlineHover">{{errorUniversity}}</a>
                    <a v-bind:disabled="!errorCourse" class="underlineHover">{{errorCourse}}</a>
                    <a v-bind:disabled="!errorCourseOffering" class="underlineHover">{{errorCourseOffering}}</a>
            </div>

        </div>
    </div>

 <br><br>
 <h2>Select a Course Offering for a Tutoring Session</h2>
 <br>
 	<table align="center">
 		<tr>
 			<td><label>Universities:</label></td>
 			<td>
		      		<select id='session-university-select' v-model="selectedUniversity" @change="generateCourses(selectedUniversity.name)">
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

<style scoped>

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


.available{
    display: none;
}


/* BASIC */

html {
  background-color: #56baed;
}

body {
  font-family: Arial, sans-serif;
  height: 100vh;
}

a {
  color: #92badd;
  display:inline-block;
  text-decoration: none;
  font-weight: 400;
}

h2 {
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  text-transform: uppercase;
  display:inline-block;
  margin: 40px 8px 10px 8px; 
  color: #cccccc;
}



/* STRUCTURE */

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
  max-width: 450px;
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

/* TABS */

h2.inactive {
  color: #cccccc;
}

h2.active {
  color: #0d0d0d;
  border-bottom: 2px solid #5fbae9;
}

/* FORM TYPOGRAPHY*/

input[type=button], input[type=submit], input[type=reset]  {
  background-color: #56baed;
  border: none;
  color: white;
  padding: 15px 80px;
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

input[type=button]:hover, input[type=submit]:hover, input[type=reset]:hover  {
  background-color: #39ace7;
}

input[type=button]:active, input[type=submit]:active, input[type=reset]:active  {
  -moz-transform: scale(0.95);
  -webkit-transform: scale(0.95);
  -o-transform: scale(0.95);
  -ms-transform: scale(0.95);
  transform: scale(0.95);
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
  width: 85%;
  border: 2px solid #f6f6f6;
  border-radius: 5px 5px 5px 5px;
}

input[type=text]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type=text]:placeholder {
  color: #cccccc;
}


/* Simple CSS3 Fade-in Animation */
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



/* OTHERS */

*:focus {
    outline: none;
} 

#icon {
  width:60%;
}

* {
  box-sizing: border-box;
}





</style>

