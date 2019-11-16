import _ from 'lodash';
import axios from 'axios';
let config = require('../../config');

let backendConfigurer = function () {
    switch (process.env.NODE_ENV) {
        case 'testing':
        case 'development':
            return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
        case 'production':
            return 'https://' + config.build.backendHost + ':' + config.build.backendPort;
    }
}

let backendUrl = backendConfigurer();

let frontendConfigurer = function () {
    switch (process.env.NODE_ENV) {
        case 'testing':
        case 'development':
            return 'http://' + config.dev.host + ':' + config.dev.port;
        case 'production':
            return 'https://' + config.build.host + ':' + config.build.port;
    }
}

let frontendUrl = frontendConfigurer();
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
        selectedAvailability: '',
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
        window.location.href = frontendUrl + '/#/home/' + this.username
      },
        createSession: function () {

            //this.errorSession = this.selectedAvailability

            if (this.selectedAvailability == {}) {
                this.errorSession = "Please select an availability"
            }
            else {
                // add axios post
                AXIOS.post(`/session?tutor_name=` + this.selectedTutor.username + `&student_name=` + this.$route.params.username
                    + `&booking_date=` + this.selectedAvailability.date + `&booking_time=` + this.selectedAvailability.time
                    + `&course_offering_id=` + this.$route.params.id + `&amount_paid=` + Number(this.selectedTutor.hourlyRate), {}, {})
                    .then(response => {
                        // JSON responses are automatically parsed.

                        this.errorSession = response.data
                        window.location.href = frontendUrl + '/#/home/' + this.username
                    })
                    .catch(e => {
                        var errorMsg = e.message
                        console.log(errorMsg)
                        //this.errorSession = e.response.data.message
                    });
            }
        // then return to home page
        //window.location.href = frontendUrl + '/#/home/' + this.username
      }
    }
  }
