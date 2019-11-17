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
    name: 'Home2',

    data() {
      return {
        username: '',
        errorUser: [],
       	User: {},
       	errorUpdate: '',
       	newAge: '',
       	newName: '',
       	newNumber: '',
        sessions: '',
        sessions2: [],
        errorSessions: '',
        deleteSession: '',
        selectedRows: [],
        selectedSession: '',
        errorSession: '',
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


        AXIOS.get(backendUrl + `/sessionbystudent?student_name=` + this.username)
            .then(response => {
                // JSON responses are automatically parsed.

                //this.sessions = response.data

                if (this.sessions == null) {
                    this.errorSessions = 'There are no booked sessions yet...'
                }
                this.sessions2 = response.data
            })
            .catch(e => {
                this.errorSessions = e.response.data.message;
            });
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
              this.errorUpdate = 'Name updated to ' + this.User.name
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
              this.errorUpdate = 'Age updated to ' + this.User.age
           })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorUpdate = e.response.data.message
          });
    	},
    	updateNumber: function(newNumber){
            AXIOS.put(`/user/` + this.User.email + `?name=` + this.User.name + `&phonenumber=` + newNumber
          + `&age=` + this.User.age, {}, {})
          .then(response => {
            // JSON responses are automatically parsed.

              this.User = response.data
              this.errorUpdate = 'Phone number updated to ' + this.User.phoneNumber
          })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.errorUpdate = e.response.data.message
          });
        },
        deleteS: function () {
            //this.errorSession = this.selectedSession.id
            AXIOS.delete(`/session/delete?session_id=` + this.selectedSession.id, {}, {})
               .then(response => {
                   // JSON responses are automatically parsed.
                   deleteSession = response.data;
                   this.errorSession = "session 2"
                   if(deleteSession == "true")
                       window.location.href = frontendUrl + '/#/home/' + this.$route.params.username
               })
               .catch(e => {
                   var errorMsg = e.message
                   console.log(errorMsg)
                   this.errorSession = e.response.data.message
               });
        },
        deleteSession: function() {
            this.errorSession = "session 1"
           //AXIOS.delete(`/session/delete?session_id=` + this.selectedSession.id, {}, {})
           //    .then(response => {
           //        // JSON responses are automatically parsed.
           //        deleteSession = response.data;
           //        this.errorSession = "session 2"
           //        if(deleteSession == "true")
           //            window.location.href = frontendUrl + '/#/home/' + this.$route.params.username
           //    })
           //    .catch(e => {
           //        var errorMsg = e.message
           //        console.log(errorMsg)
           //        this.errorSession = e.response.data.message
           //    });
        }
    }
  }

