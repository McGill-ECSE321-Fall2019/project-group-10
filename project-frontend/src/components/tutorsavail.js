import axios from 'axios'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
//var backendUrl = 'http://localhost:8080'

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

// define DTO objects
function TutorDTO(username, education, hourlyRate, experience, avails, ratings, texts) {
    this.username = username;
    this.experience = experience;
    this.hourlyRate = hourlyRate;
    this.education = education;
    this.avails = avails;
    this.ratings = ratings;
    this.texts = texts;
}

function AvailabilityDTO(date, time) {
    this.date = date;
    this.time = time;
}

export default {
    name: 'Tutors',

    data() {
      return {
        errorCourseOffering: '',
        tutors: [],
        tutor: {},
        selectedTutor: '',
        tutoravails: [],
        tutorratings: [],
        tutortexts: [],
        errorTutor:'',
        selectedAvailability: {},
        username: '',
        errorSession: '',
        response: [],
        resp: []
      }
    },

    created() {
      this.fetchData()
      this.username = this.$route.params.username
    },

    watch: {
      '$route': 'fetchData'
    },

    methods: {
      getTutor: function(username){
        // get the tutor information
        AXIOS.get(`/tutor/`+ username)
        .then(response => {
          // JSON responses are automatically parsed.
          this.errorTutor = 'Selected: ' + username
          this.tutor = response.data 
        })
        .catch(e => {
          this.errorTutor = e;
        });

        AXIOS.get(`/allavailabilities/`+ username)
        .then(response => {
          // JSON responses are automatically parsed.
          this.avails = response.data 
        })
        .catch(e => {
          this.errorTutor = e;
        });
      },       
      fetchData() {
        AXIOS.get(backendUrl + '/courseoffering/' + this.$route.params.id +'/')
        .then((resp) => {
          this.tutors = resp.data
          console.log(resp)
        })
        .catch((err) => {
          console.log(err)
          this.errorCourseOffering = err.response.data.message
        })
      },
      goBack: function (){
        window.location.href = frontendUrl + '/#/session/' + this.username
      },
      goHome: function (){
        window.location.href = frontendUrl + '/#/home/'
      },
      createSession: function(){
        // add axios post
        AXIOS.post(`/session?tutor_name=`+ this.selectedTutor.name + `&student_name=` + this.username
          + `&booking_date=` + this.selectedAvailability.date + `&booking_time=` + this.selectedAvailability.time 
          + `&course_offering_id=` + this.$route.params.id + `&amount_paid=` + this.selectedTutor.amount_paid, {}, {})
          .then(response => {
            // JSON responses are automatically parsed.
          })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorSession = e.response.data.message
          });
        // then return to home page
        window.location.href = frontendUrl + '/#/home/'
      }
    }
  }