<template>
<span class="container">


  <div class="nav-bar">
        <li><a href="#intro">Home</a></li>
        <li><a href="#choose">About</a></li>
        <li><a href="#contact">Contact</a></li>
        <li><a href="#">Login</a></li>
    </div>

    <div class="intro" id="intro">
        <div class="text">
        <h1>Welcome To Tutoring Website!</h1>
        <h3>the way learning should be</h3>
        <button>Get Started</button>
        </div>

        <div class="image"><img src="../images/Tutoring.jpg" alt="Tutoring image"></div>
    </div>



    <section class="choose" id="choose">
        <h2>Why Choose Us As Your Official Tutoring Service</h2>
            <h5>Lorem ipsum dolor sit amet, consectetur adipisicing elit</h5>
        <div class="qualities">
 
            <div class="personalized ">
                <div class="image-1 icon"><img src="../images/user.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Personalized</h4>
                    <p>We pride ourselves on connecting you with passionate local organizations
                        and their instructors who know that every learner is unique.</p>
                </div>
            </div>

            <div class="convenience">
                <div class="image-2 icon"><img src="../images/happy.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Convenience</h4>
                    <p>With our new and improved mobile application, 
                        booking a qualified instructor can be done in a matter of seconds.</p>  
                </div>
            </div>

            <div class="satisfaction">
                <div class="image-3 icon"><img src="../images/thumbs.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Satisfaction Guaranteed</h4>
                    <p>We guarantee a refund if you were not satisfied with your lesson.  Lorem ipsum dolor sit amet consectetur adipisicing elit. Quod, tempora?</p>  
                </div>
            </div>

            <div class="results">
                <div class="image-4 icon"><img src="../images/star.png" alt=""></div>
                <div class="c-text">
                    <h4 class="pad">Results Driven</h4>
                    <p>97% of all our lessons have over a 4.5/5 star rating.</p>  
                </div>
            </div>
        </div>
    </section>


<section class="contact" id="contact">
<h3>"In order to reach out to us, you can simply email us at <br><a href="#">group-10@mail.mcgill.ca</a>"</h3>

</section>


<footer>
    <p>All Rights Reserved by The Group 10</p>
</footer>












</span>
</template>


<script>
export default {
    name:'Startup'
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
    color: rgb(53, 53, 53);
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

.intro img:hover{
        transition: 0.7s;
	transform: scale(1.05);
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

</style>
