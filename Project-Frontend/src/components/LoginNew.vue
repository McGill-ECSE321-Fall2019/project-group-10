

<template>
<span class="container">
<!-- "class="signin/signup"" -->
    <div class="button-pos"><button type="button" class="btn btn-info" @click="goBack()">Go Back</button></div>
        <div class="wrapper fadeInDown">
            <div id="formContent">
            <!-- Tabs Titles -->
            <h2  @click="signInClick" v-bind:class="{'active': available, 'inactive': !available, 'underlineHover': true}"> Sign In </h2>
            <h2   @click="toggleClass"  class="signingup" v-bind:class="{'active': !available, 'inactive': available, 'underlineHover': true}">Sign Up </h2>

            <!-- Login Form -->
            <form>
                <input type="text" id="username" v-model="username" class="fadeIn second" placeholder="Username">
                <input type="password" id="password" v-model="password" class="fadeIn second" placeholder="Password">
                <div v-bind:class="{'available': !available}"><input type="button"  v-bind:disabled="!username || !password" value="Log In" @click="logIn()"></div>
                <div class="signup" v-bind:class="{'available': available}">
                    <input type="text" id="name" v-model="Name" placeholder="Name">
                    <input type="text" id="email" v-model="email" placeholder="Email">
                    <input type="text" id="age" v-model="age" placeholder="Age">
                    <input type="text" id="phonenumber" v-model="number" placeholder="Phonenumber">
                    <br>
                    <br>
                    <input type="button" @click="runPyScript()" value="Confirm Identity">
                    <br>
                    <input type="button" v-bind:disabled="!username || !password || !Name || !email || !age || !number || imageValue != 'person'" @click="signUp()" value="Sign Up">
                </div>
            </form>
            <div id="formFooter">
                    <a class="underlineHover">{{SignUpError}}</a>
            </div>
          <b>{{mlError}}</b>
        </div>
            </div>

</span>
</template>


 <script>

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
  name: "formContent",
  data(){
    return{
      Name: '',
      SignUpError: '',
      email: '',
      age: '',
      number: '',
      username: '',
      password: '',
      available: true,
      loggedin: false,
      imageValue: '',
      mlError: '',
      response: []
    }
  },
  created() {
    if (this.$route.params.id == "1") {
      this.available = false;
    }
  },
  methods:{
    runPyScript(){
      AXIOS.get(`/begin_facial_recognition`, {}, {})
        .then(response => {
          // JSON responses are automatically parsed.
          this.imageValue = response.data.response
          this.mlError = response.data.errorPhrase
        })
        .catch(e => {
          this.SignUpError = e.response.data.message
        });
    },

      toggleClass(){
        this.available = false;
       
      },

      signInClick(){
        this.available = true;
      },

    goBack: function (){
      window.location.href = frontendUrl + '/#/'
    },

    signUp: function () {

      AXIOS.post(`/createuser2/?userName=` + this.username + `&userPassword=` +
        this.password + `&userEmail=` + this.email + `&age=` + this.age + `&phoneNum=` + this.number + `&name=` + this.Name, {}, {})
        .then(response => {
          // JSON responses are automatically parsed.

          this.username = response.data.username
          this.password = response.data.password
          //logIn()
          window.location.href = frontendUrl + '/#/home/' + this.username;
          this.loggedin = "true"
        })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
          this.SignUpError = e.response.data.message
        });
        
    },

    createStudent: function () {
      // create the student
        AXIOS.post(`/createstudent/?userName=`+ this.username + `&userPassword=` + this.password
          + `&userEmail=` + this.email, {}, {})
          .then(response => {
            // JSON responses are automatically parsed.
            //logIn()
          })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
            this.SignUpError = e.response.data.message
          });
    },

    logIn: function () {
      AXIOS.post(`/login?username=` + this.username + `&password=` + this.password, {}, {})
        .then(response => {
          // JSON responses are automatically parsed.

          //window.location.href = frontendUrl + '/#/home/' + this.username
          this.loggedin = response.data
          if (this.loggedin == true) {
            window.location.href = frontendUrl + '/#/home/' + this.username
          }else {
            this.SignUpError = "Wrong username/password combo"
          }
          //this.SignUpError = this.loggedin
        })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
          this.SignUpError = e.response.data.message
        });
      //this.SignUpError = this.loggedin

      //if (this.loggedin == "true") {
      //  window.location.href = frontendUrl + '/#/home/' + this.username
      //}
      //else {
      //  //this.SignUpError = "Wrong username/password combo"
      //}
    }
  }    
}
</script>
 


<style scoped>

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
  -webkit-transition: all 0.3s ease-in-out;
  -moz-transition: all 0.3s ease-in-out;
  -ms-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
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
  -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

input[type=text]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type=text]:placeholder {
  color: #cccccc;
}

input[type=password] {
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
  -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

input[type=password]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type=password]:placeholder {
  color: #cccccc;
}



/* ANIMATIONS */

/* Simple CSS3 Fade-in-down Animation */
.fadeInDown {
  -webkit-animation-name: fadeInDown;
  animation-name: fadeInDown;
  -webkit-animation-duration: 1s;
  animation-duration: 1s;
  -webkit-animation-fill-mode: both;
  animation-fill-mode: both;
}

@-webkit-keyframes fadeInDown {
  0% {
    opacity: 0;
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }
  100% {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}

@keyframes fadeInDown {
  0% {
    opacity: 0;
    -webkit-transform: translate3d(0, -100%, 0);
    transform: translate3d(0, -100%, 0);
  }
  100% {
    opacity: 1;
    -webkit-transform: none;
    transform: none;
  }
}

/* Simple CSS3 Fade-in Animation */
@-webkit-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
@-moz-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
@keyframes fadeIn { from { opacity:0; } to { opacity:1; } }

.fadeIn {
  opacity:0;
  -webkit-animation:fadeIn ease-in 1;
  -moz-animation:fadeIn ease-in 1;
  animation:fadeIn ease-in 1;

  -webkit-animation-fill-mode:forwards;
  -moz-animation-fill-mode:forwards;
  animation-fill-mode:forwards;

  -webkit-animation-duration:1s;
  -moz-animation-duration:1s;
  animation-duration:1s;
}

.fadeIn.first {
  -webkit-animation-delay: 0.4s;
  -moz-animation-delay: 0.4s;
  animation-delay: 0.4s;
}

.fadeIn.second {
  -webkit-animation-delay: 0.6s;
  -moz-animation-delay: 0.6s;
  animation-delay: 0.6s;
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
.button-pos{
  position: relative;
}
</style>
