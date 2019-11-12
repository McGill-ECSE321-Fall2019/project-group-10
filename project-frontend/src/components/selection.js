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

function url_redirect(url){
    var X = setTimeout(function(){
        window.location.replace(url);
        return true;
    },300);

    if( window.location = url ){
        clearTimeout(X);
        return true;
    } else {
        if( window.location.href = url ){
            clearTimeout(X);
            return true;
        }else{
            clearTimeout(X);
            window.location.replace(url);
            return true;
        }
    }
    return false;
};

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
  // const u1 = new UniversityDTO('McGill2', '123 Main')
  // const u2 = new UniversityDTO('Concordia2', '456 Test')
  // const c1 = new CourseDto('ECSE 321', 'Intro to Software', 'McGill')
  // const c2 = new CourseDto('ECSE 325', 'Digital Systems', 'McGill')
  // const co1 = new CourseOfferingDto('Fall', 2019, 1)
  // const co2 = new CourseOfferingDto('Winter', 2020, 2)
  // Sample initial content
  // this.unis = [u1, u2]
  // this.courses = [c1, c2]
  // this.courseOfferings = [co1, co2]
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
      this.errorUniversity = e;
    });
  },
  methods: {
    generateCourses: function(uniName){
      // get all the courses associated with a university
      this.courses = []
      AXIOS.get(`/universities/`+ uniName)
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorUniversity = 'Selected: ' + uniName
        this.courses = response.data 
      })
      .catch(e => {
        this.errorUniversity = e;
      });
    },   
    generateCourseOfferings: function(courseName, uniName){
      // get all course offerings of a course
      this.courseOfferings = []
      AXIOS.get(`/courses/`+ uniName + `/` + courseName)
      .then(response => {
        // JSON responses are automatically parsed.
        this.errorCourse = 'Selected: ' + courseName + ' at ' + uniName
        this.courseOfferings = response.data
      })
      .catch(e => {
        this.errorCourse = e;
      });
      },
    submit: function (id){
      window.location.href = frontendUrl + '/#/tutors/' + id +'/' + this.username
    },
    goHome: function (){
      window.location.href = frontendUrl + '/#/home/'
    }
  }
}
