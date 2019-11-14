import axios from 'axios'
var config = require('../../config')

// define urls for front and backend
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
//var backendUrl = 'http://localhost:8080'

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

// define DTO objects
function UniversityDTO(name, address) {
  this.name = name;
  this.address = address;
}

function CourseDto(courseName, description, uniName) {
  this.courseName = courseName;
  this.description = description;
  this.uniName = uniName;
}

function CourseOfferingDto(term, year, id) {
  this.term = term;
  this.year = year;
  this.id = id;
}

export default {
  name: 'booksession',
  data () {
    return {
      unis : [],
      errorUniversity: " ",
      selectedUniversity: '',
      courses: [],
      errorCourse: " ",
      selectedCourse: '',
      courseOfferings: [],
      errorCourseOffering: " ",
      selectedCourseOffering: '',
      response: [],
      username:''
    }
  },
  created: function() {
      // Test data
    // get all the universities available
    this.username = this.$route.params.username
    this.unis = []
    this.courses = []
    this.courseOfferings = []
    AXIOS.get(`/universities`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.unis = response.data
    })
    .catch(e => {
      this.errorUniversity = e.response.data.message;
    });
  },
  methods: {
    generateCourses: function(uniName){
      // get all the courses associated with a university
      this.courses = []
      AXIOS.get(`/universities/`+ uniName)
      .then(response => {
        // JSON responses are automatically parsed.
        this.courses = response.data 
        this.errorCourse = ''
      })
      .catch(e => {
        this.errorUniversity = e.response.data.message;
      });
    },   
    generateCourseOfferings: function(courseName, uniName){
      // get all course offerings of a course
      this.courseOfferings = []
      AXIOS.get(`/courses/`+ uniName + `/` + courseName)
      .then(response => {
        // JSON responses are automatically parsed.
        this.courseOfferings = response.data
      })
      .catch(e => {
        this.errorCourse = e.response.data.message;
      });
      },
    submit: function (id){
      window.location.href = frontendUrl + '/#/tutors/' + id +'/' + this.username
    },
    goHome: function (){
      window.location.href = frontendUrl + '/#/home/' + this.username
    }
  }
}
