<template>
<div>
	 <div class="wrapper">
        <div id="formContent">
            <!-- Tabs Titles -->
            <h2  class="active"> Write a Review </h2><br>
        <br>
		<h4><b>{{username}}</b> reviewing <b>{{tutorName}}</b></h4>


		<font color="red">{{errorSession}}</font>
		<br>
		<div>
			<div>
				
				<select v-model="selectedSession" @change="updateTutor(selectedSession.tutorName)">
					<option disabled value="">Please select a session</option>
					<option v-for="(session, i) in sessions" v-bind:key="`session-${i}`" v-bind:value="{id: session.courseOfferingDTO.id, time: session.time, date: session.date, tutorName: session.tutorDTO.username}">Tutor: {{session.tutorDTO.username}} | Date: {{session.date}} | Paid: ${{session.amountPaid}}</option>
				</select>
				<br>
			</div>
		</div>

	<br>
	<h4><b>Rating {{ratingScore}}/5</b></h4>

	<br>
	<table id=StarRating>
		<tr>
			<td class="container">
				<img class="image" src="../assets/star.png" @click="starRating(1)"/>
				<div class="middle">
					<div class="startext">1</div>
				</div>
			</td>
			<td class="container">
				<img class="image" src="../assets/star.png" @click="starRating(2)">
				<div class="middle">
					<div class="startext">2</div>
				</div>
			</td>
			<td class="container">
				<img class="image" src="../assets/star.png" @click="starRating(3)">
				<div class="middle">
					<div class="startext">3</div>
				</div>
			</td>
			<td class="container">
				<img class="image" src="../assets/star.png" @click="starRating(4)">
				<div class="middle">
					<div class="startext">4</div>
				</div>
			</td>
			<td class="container">
				<img class="image" src="../assets/star.png" @click="starRating(5)">
				<div class="middle">
					<div class="startext">5</div>
				</div>
			</td>
		</tr>
	</table>
	<br>
	
	<h4><b>Description</b></h4>
	<br>
		<textarea name="text" v-model="text" rows="4" cols="40" placeholder="How was your tutor..." class="TextDescription" maxlength="1000"></textarea>
		<br>200 words max<br><br>

	  <b-button id='home-button' class="btn btn-info" variant="success" type='button' v-bind:disabled="!selectedSession||!ratingScore||!text" @click="createRating(selectedSession.id), createText(selectedSession.id, 'true'), goHome()">Submit</b-button>
	<br>

	<br>
	<div id="formFooter">
        <font color="red">{{errorRating}}</font>
		<font color="red">{{errorText}}</font>
    </div>
    </div>

</div>
<br>
<br>
	<b-button id='home-button' variant="outline-secondary" type='button' @click="goHome()">Return</b-button>


</div>
</template>

<script src="./reviews.js">
</script>
	
<style>
  
	.TextDescription {
	padding: 14px 20px;
	margin: 8px 0;
	border: 3px solid #ccc;
	border-radius: 4px;
	cursor: pointer;
  }

  	#sessionTable {
	 margin-left: auto;
	 margin-right: auto;
  }

  .centered {
	margin-left: auto;
	margin-right: auto;
	width: 100px;
  }

  #StarRating {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
	width: 400px;
	margin-left: auto;
	margin-right: auto;
  }

  #TextBoxSize {
	width: 60%;
	height: 400px;
  }



.container {
  position: relative;
}
.image {
  opacity: 1;
  display: block;
  height: auto;
  backface-visibility: hidden;
}
.middle {
  opacity: 0;
  position: absolute;
  top: 50%;
}
.container:hover .image {
  opacity: 0.4;
}
.container:hover .middle {
  opacity: 1;
}
.startext {
  color: black;
  font-size: 32px;
}

/* BASIC */

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
.currentLogin{
  text-align: right;
  opacity: 0.6;
}
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
/* input[type=button], input[type=submit], input[type=reset]  */
 select{
  background-color: #56baed;
  border: none;
  color: white;
  padding: 15px 40px;
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

}

label{
  border: none;
  color: #353535;
  padding: 10px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  width: 85%;
 -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

option {
  background-color: #56baed;
  border: none;
  color: #f6f6f6;
  padding: 15px 32px;
  text-align: center;

}

input[type=text]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type=text]:placeholder {
  color: #cccccc;
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





</style>
