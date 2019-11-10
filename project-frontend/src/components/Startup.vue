<template>
<div class="container">
    
    
    <div class="nav-bar">
        <li><a href="#">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Contact</a></li>
        <li><a href="#">Login</a></li>
    </div>

    <div class="intro">
        <div class="text">
        <h1>Welcome To Tutoring Website!</h1>
        <h3>the way learning should be</h3>
        <button>Get Started</button>
        </div>

        <div class="image"><img src="../images/Tutoring.jpg" alt="Tutoring image"></div>
    </div>


    <div class="about-us">
        <h2>Available All Around Canada</h2>
        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem, quae?
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem, quae?
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Quidem, quae </p>
    </div>

    <div class="qualities">
        <div class="personalized">
            <div class="image-1"><img src="../images/user.png" alt=""></div>
            <h4>Personalized</h4>
            <p>We pride ourselves on connecting you with passionate local organizations
                 and their instructors who know that every learner is unique.</p>
        </div>

        <div class="convenience">
            <div class="image-2"><img src="../images/happy.png" alt=""></div>
            <h4>Convenience</h4>
            <p>With our new and improved mobile application, 
                booking a qualified instructor can be done in a matter of seconds.</p>  
        </div>

        <div class="satisfaction">
            <div class="image-3"><img src="../images/thumbs.png" alt=""></div>
            <h4>Satisfaction Guaranteed</h4>
            <p>We guarantee a refund if you were not satisfied with your lesson.</p>  
        </div>

        <div class="results">
            <div class="image-4"><img src="../images/star.png" alt=""></div>
            <h4>Results Driven</h4>
            <p>97% of all our lessons have over a 4.5/5 star rating.</p>  
        </div>
    </div>

<footer>
    <p>Copyright 2019 Tutoring System Inc. / Terms of Use and Privacy Policy / Created by Group-10</p>
</footer>

</div>
</template>

<script>
export default {
    name:'Startup'
}
</script>



<style scoped>
/* global */

*{
 margin: 0 auto; 
 padding:0;

}

/*  */


/* Navbar */
.nav-bar{
    display: flex;
    justify-content: space-between;
}

.nav-bar li{
    list-style-type:none;
    margin-left: 10%;
    padding-left: 1em;
    padding-right: 1em;
    /* display: inline-block; */
    
}

/*  */



/* Intro */
.intro{
    display: flex;
   
}

.text{
    align-content: left;
flex-basis: 2300px;
margin-top:8rem;
margin-left: -2rem;
}

.text h1,h3{
    color: rgb(75, 74, 74);
    padding-left: 0;
}

.intro img{
    max-width:100%;
    height:auto;
}

button{
   padding:10px;
    margin: 1rem;
}
/*  */

/* About us */

.about-us h2{
    color:rgb(75, 74, 74);
}

.about-us p{
    justify-content: left;
}

/*  */
</style>