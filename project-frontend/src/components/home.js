import axios from 'axios'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
//var backendUrl = 'http://localhost:8080'

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'Home',

    data() {
      return {
        username: '',
        errorUser: [],
       	User: {},
       	errorUpdate: '',
       	newAge: '',
       	newName: '',
       	newNumber: '',
       	sessions: [],
       	errorSessions: '',
        response: []
      }
    },
    created: function() {
      this.username = this.$route.params.username
      AXIOS.get(backendUrl + '/students/' + this.$route.params.username)
        .then((response) => {
          this.User = response.data
          console.log(response)
        })
        .catch((err) => {
          console.log(err)
          this.errorUser = err.response.data.message
        })
        response = []
      AXIOS.get(`/sessionsbystudent?student_name=` + this.username)
      .then((response) => {
          this.sessions = response.data
          //this.errorSessions = response.data
        })
        .catch((err) => {
          console.log(err)
          this.errorSessions = err.response.data.message
        })
    },
    methods: {
    	goToSession: function (){
      		window.location.href = frontendUrl + '/#/session/' + this.$route.params.username
    	},
    	goToReview: function (){
      		window.location.href = frontendUrl + '/#/review/' + this.$route.params.username
    	},
    	logout: function (){
    		AXIOS.get(backendUrl + '/logout/' + this.$route.params.username)
	        .then((response) => {
	          console.log(resp)
	        })
	        .catch((err) => {
	          console.log(err)
	          this.errorUser = err.response.data.message
	        })
          
      		window.location.href = frontendUrl + '/#/'
    	},
    	updateName: function(newName){
    	  AXIOS.put(`/user/` + this.User.email +`?name=`+ newName + `&phonenumber=` + this.User.phoneNumber
          + `&age=` + this.User.age, {}, {})
          .then(response => {
            // JSON responses are automatically parsed.
            this.User = response.data
          })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorUpdate = e.response.data.message
          });
    	},
    	updateAge: function(newAge){
    	  AXIOS.put(`/user/` + this.User.email +`?name=`+ this.User.name + `&phonenumber=` + this.User.phoneNumber
          + `&age=` + newAge, {}, {})
          .then(response => {
            // JSON responses are automatically parsed.
            this.User = response.data
          })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorUpdate = e.response.data.message
          });
    	},
    	updateNumber: function(newNumber){
    	  AXIOS.put(`/user/` + this.User.email +`?name=`+ this.User.name + `&phonenumber=` + newNumber
          + `&age=` + this.User.age, {}, {})
          .then(response => {
            // JSON responses are automatically parsed.
            this.User = response.data
          })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorUpdate = e.response.data.message
          });
    	}
    }
  }

