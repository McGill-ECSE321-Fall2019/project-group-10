<template>
 <div id=selection>

 <h2>Select a Course Offering for a Tutoring Session</h2>
    <label>Universities:
      <select id='session-university-select' v-model="selectedUniversity">
        <option disabled value="">Please select a university</option>
        <option v-for="(university, i) in unis" v-bind:key="`university-${i}`" v-bind:value="{ name: university.name, address: university.address }">{{university.name}}</option>
      </select>
    </label>

    <b-button id='uni-button' type='button' v-bind:disabled="!selectedUniversity" @click="generateCourses(selectedUniversity.name)">Select University</b-button>

    <br><span style="color:red">{{errorUniversity}}</span>

    <br>
    <label>Course:
      <select id='session-course-select' v-model="selectedCourse">
        <option disabled value="">Please select a course</option>
        <option v-for="(course, i) in courses" v-bind:key="`course-${i}`" v-bind:value="{ courseName: course.courseName, uniName: course.uniName, description: course.description }">{{course.courseName}}</option>
      </select>
    </label>

    <b-button id='course-button' v-bind:disabled="!selectedUniversity || !selectedCourse" type='button' @click="generateCourseOfferings(selectedCourse.courseName, selectedCourse.uniName)">Select Course</b-button>

    <br><span style="color:red">{{errorCourse}}</span>

    <br>
    <label>Course Offering:
      <select id='session-co-select' v-model="selectedCourseOffering">
        <option disabled value="">Please select a course offering</option>
        <option v-for="(co, i) in courseOfferings" v-bind:key="`co-${i}`" v-bind:value="{ term: co.term, year: co.year, id: co.id }">{{co.term}} {{co.year}}</option>
      </select>
    </label>

    <b-button id='selection-button' type='button' v-bind:disabled="!selectedUniversity || !selectedCourse || !selectedCourseOffering" @click="submit(selectedCourseOffering.id)">Submit</b-button>

    <br><span style="color:red">{{errorCourseOffering}}</span>

    <hr>
    <router-link to="http://127.0.0.1:8087/home" tag="button">Return</router-link>

    
 </div>

</template>
<script src="./selection.js">
</script>

<style>
  #tutoringservice {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>
