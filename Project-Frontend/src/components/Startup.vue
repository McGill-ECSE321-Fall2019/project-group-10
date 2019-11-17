<template>
<span class="container">

  <div class="nav-bar">
        <li><a href="#intro">Home</a></li>
        <li><a href="#choose">About</a></li>
        <li><a href="#contact">Contact</a></li>
        <li><a id="hyperlink" href="#" @click="myFunction()">Login</a></li>
    </div>

    <div class="intro" id="intro">
        <div class="text">
        <h1>Welcome To Tutoring Website!</h1>
        <h3>the way learning should be</h3>
        <button type="button" class="btn btn-info" @click="gotoLogin()">Get Started</button>
        </div>

        <div class="image"><img src="../images/tutoring.jpg" alt="Tutoring image"></div>
    </div>



    <section class="choose" id="choose">
        <h1>Why Choose Us As Your Official Tutoring Service</h1>
            <h5>Well let’s be honest, there isn’t really any competition.</h5>
        <div class="qualities">
 
            <div class="personalized ">
                <div class="image-1 icon"><img src="../images/user.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Personalized</h4>
                    <p>Our instructors are unique because they were where you are today, no more than a few years ago.
                  Whether that makes u feel more confident in their ability to teach or not,
                  they know what you are going through and understand how to deliver a quality teaching experience.</p>
                </div>
            </div>

            <div class="convenience">
                <div class="image-2 icon"><img src="../images/happy.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Convenience</h4>
                    <p>With the world moving into simplicity and speed, we bring to you our new and improved mobile application.
                  With it, you can book sessions from the comfort of your bed.
                  After all, you are in your bed because you are too lazy too study which is what we will help you with.</p>  
                </div>
            </div>

            <div class="satisfaction">
                <div class="image-3 icon"><img src="../images/thumbs.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Satisfaction Guaranteed</h4>
                    <p>We have a long history of success stories. We have tutored the brightest minds this world has ever seen.
                  People such as Mark Zuckerberg and Steve Jobs, have all used our tutoring services to learn.
                  Despite dropping out, they made their legacy off what we taught</p>  
                </div>
            </div>

            <div class="results">
                <div class="image-4 icon"><img src="../images/star.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Results Driven</h4>
                    <p>If numbers don’t mean anything to you,
                  we will teach you what they mean.
                  For now, understand that you can’t beat 5 out of 5 performance… and that is what we have.</p>  
                </div>
            </div>
        </div>
    </section>


<section class="contact" id="contact">
<h4>In order to reach out to us, you can simply email us at <br><a href="#">group-10@mail.mcgill.ca</a></h4>

</section>

<!--  -->
<footer>
    <p>All Rights Reserved by The Group 10</p>
</footer>

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

  export default {
    name: 'Startup',
    // data(){
    //  return{
    //    frontendUrl: '',
    //    backendUrl: ''
    //  }
    //},
    //created: function () {
    //  frontendUrl: frontendConfigurer();
    //  backendUrl: backendConfigurer
    //},
    methods: {
      gotoLogin: function() {
        window.location.href = frontendUrl + '/#/login/1'
      },
      myFunction() { //Call this function whenever you want to update the href
        var link = document.getElementById("hyperlink");
        link.href = frontendUrl + '/#/login/0';
      }
    }
  }
</script>

<style scoped>

*{
    margin: 0;
}

.nav-bar{
    display: flex;
    list-style: none;
    justify-content: center;
}

.nav-bar li a{ 
    color: rgb(73, 73, 73);
	font-size: 20px;
}

.nav-bar li {
    margin: 0 10%;
}
/*  */
/* End of Navbar */
/*  */


.intro{
    display: flex;
    flex-direction: row;
}

.text{
    flex-basis: 50%;
    margin-top: 10%;
}


.image{
    flex-shrink: 3;
}

.intro img{
    max-width:60%;
    height:auto;
}

button{
    margin-top: 2rem;
}

/*  */
/* End of Intro Section */
/*  */
.choose{
    margin-top:10%;
}
.qualities{
    display: flex;
    justify-content: space-evenly;
    margin-top:4rem;
}


.personalized{
    flex-basis: 20%;
}

.personalized:hover{
    transition: 0.6s;
	transform: scale(1.1);
}
.convenience{
    flex-basis: 20%;
}
.convenience:hover{
    transition: 0.6s;
	transform: scale(1.1);
}
.satisfaction{
    flex-basis: 20%;
}
.satisfaction:hover{
    transition: 0.6s;
	transform: scale(1.1);
}
.results{
    flex-basis:20%;
}
.results:hover{
    transition: 0.6s;
	transform: scale(1.1);
}
.pad{
    padding:20px;
}
/*  */
/* End of section 2 */

.contact{
    margin-top: 06%;
}
.contact h3{
    padding:10px;
}

footer{
    margin-top: 4%;
    opacity: 0.6;
}

/* Only for Calendar */

</style>
