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


export default {
  name: 'writereview',
  data () {
    return {
      isClicked: '',
      ratingScore: '',
      tutorName: '',
      errorRating: '',
      errorText: '',
      text: '',
      response: [],
      sessions: [],
      username: '',
      errorSession: '',
      selectedSession: '',
      rating: [],
      text:'',
      users: [],
      user: []
    }
  },
  created: function() {
	this.username = this.$route.params.username
	this.errorSession = ''
    // axios get currently logged in
    // studentName = response.data
    AXIOS.get(`/sessionbystudent?student_name=` + this.username)
    .then(response => {
      // JSON responses are automatically parsed.
      this.sessions = response.data
      this.tutorName = '[select a session]'
      this.ratingScore = '0'
      this.text = ''
      if(this.sessions == null){
    	  this.errorSession = 'There are no sessions for you to review...'
      }
    })
    .catch(e => {
      this.errorSession = e.response.data.message;
    });
  },
  methods: {
    createRating: function(courseId){
      AXIOS.post(`/createrating?rating=` + this.ratingScore + `&username=` + this.tutorName + `&courseId=` + courseId) 
      .then(response => {
        // JSON responses are automatically parsed.
        rating = response.data
      })
      .catch(e => {
        this.errorRating = e.response.data.message
      });
    },   
    createText: function(courseId, isAllowed){
        AXIOS.post(`/createtext?description=` + this.text + `&username=` + this.tutorName + `&courseOfferingId=` + courseId + `&isAllowed=` + isAllowed) 
        .then(response => {
          // JSON responses are automatically parsed.
          text = response.data
        })
        .catch(e => {
          this.errorText = e.response.data.message
        });
      },
    goHome: function (){
      window.location.href = frontendUrl + '/#/home/' + this.username
    },
    updateTutor: function (name){
    	this.tutorName = name
    },
    starRating: function(rating){
    	this.ratingScore = rating
    },
    recordText: function(textInput){
    	this.text = textInput
    }
  }
}
