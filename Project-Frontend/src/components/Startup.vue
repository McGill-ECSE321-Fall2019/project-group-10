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
        <button @click="gotoLogin()">Get Started</button>
        </div>

        <div class="image"><img src="../images/Tutoring.png" alt="Tutoring image"/></div>
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
<h4>"In order to reach out to us, you can simply email us at <br><a href="#">group-10@mail.mcgill.ca</a>"</h4>

</section>

<!-- Calendar -->
<!-- <link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet"> -->

<!-- <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'> -->

<div class="planner">

	<div class="calendar">
		<div class="calendar-header">
			<span class="btn btn-prev">
				<i class="icon-angle-left"></i>
			</span>

			<span class="">July</span>

			<span class="btn btn-next">
				<i class="icon-angle-right"></i>
			</span>
		</div>
		<table>
			<thead>
				<tr>
					<th>SUN</th>
					<th>MON</th>
					<th>TUE</th>
					<th>WED</th>
					<th>THU</th>
					<th>FRI</th>
					<th>SAT</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><span>30</span></td>
					<td><span>1</span></td>
					<td><span class="active">2</span></td>
					<td><span>3</span></td>
					<td><span>4</span></td>
					<td><span>5</span></td>
					<td><span>6</span></td>
				</tr>
				<tr>
					<td><span>7</span></td>
					<td><span>8</span></td>
					<td><span class="active">9</span></td>
					<td><span>10</span></td>
					<td><span>11</span></td>
					<td><span>12</span></td>
					<td><span>13</span></td>
				</tr>
				<tr>
					<td><span class="active">14</span></td>
					<td><span>15</span></td>
					<td><span>16</span></td>
					<td><span>17</span></td>
					<td><span class="active">18</span></td>
					<td><span>19</span></td>
					<td><span>20</span></td>
				</tr>
				<tr>
					<td><span>21</span></td>
					<td><span>22</span></td>
					<td><span>23</span></td>
					<td><span>24</span></td>
					<td><span>25</span></td>
					<td><span>27</span></td>
					<td><span>27</span></td>
				</tr>
				<tr>
					<td><span>28</span></td>
					<td><span>29</span></td>
					<td><span>31</span></td>
					<td><span>1</span></td>
					<td><span>2</span></td>
					<td><span>3</span></td>
					<td><span>4</span></td>
				</tr>
			</tbody>

		</table>
	</div>
	<div class="schedule">
			<ul class="tabs">
			<li class="tab active"><a href="#">Meetings</a></li>
			<li class="tab"><a href="#">Reminders</a></li>
			<li class="tab"><a href="#">Notes</a></li>
			</ul>

		<div class="tab-pane">
			<div class="schedule-list">

				<div class="schedule-item">
					<div class="time">
						<span>9</span>
						<span>am</span>
					</div>
					<div class="description">
						<div class="description-header">Brainstorm on User Stories</div>
						<div class="description-content">
							<p>Lorem Ipsum dolor sit amet.</p>
							<ul class="contact-list">
								<li class="contact">
									<!-- <a href="#"><img src="http://gravatar.com/avatar/8b1d44e3b2310823bd41d609003b70a9?s=80" alt="" /></a> -->
								</li>
								<li class="contact">
									<!-- <a href="#"><img src="http://gravatar.com/avatar/8b1d44e3b2310823bd41d609003b70a9?s=80" alt="" /></a> -->
								</li>
								<li class="contact add-contact">
									<a href="#"><i class="icon-plus"></i></a>
								</li>
							</ul>
						</div>
					</div>
				</div>

				<div class="schedule-item free">
					<div class="time">
						<span>10</span>
						<span>am</span>
					</div>
					<div class="description">
            <div class="description-header">No Schedule</div>
						<div class="description-content">&nbsp;</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<!--  -->
<footer>
    <p>All Rights Reserved by The Group 10</p>
</footer>












</span>
</template>


<script>
  var config = require('../../config')
  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  export default {
    name: 'Startup',
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
div, ul, li{
	list-style-type: none;
}

.planner{
	font-family: 'Open Sans', sans-serif;
	width: 394px;
	margin: 100px auto;
	box-shadow: 0 0 40px rgba(0,0,0,0.5);
	border-radius: 5px;
	overflow: hidden;
}

.calendar{

	padding: 10px;
	background-color: #333333;
}

.calendar-header{
	color: #a7a7a7;
	border-bottom: 1px solid #484848;
	margin: 0; padding: 0;
	text-align: justify;
	line-height: 0px;
	font-size: 0px;
	padding-bottom: 10px;
  }

	.calendar-header span{
		display: inline-block;
		font-size: 36px;
		font-weight: 200;
		text-align: center;
		line-height: 36px;
	}

	.calendar-header:after{
		content: '';
		display: inline-block;
		width: 100%;
	}


table{
	width: 100%;
	padding: 10px;
	color: #9d9d9d;
}

th{
	font-weight: normal;
	font-size: 14px;
	color: #5b5b5b;
}

td{
	font-weight: normal;
	font-size: 12px;
	text-align: center;
}


td > span{
	display: inline-block;
	text-align: center;
	padding: 3px; margin: 0px;
	width: 20px; height: 20px;
  line-height: 20px;
}

td > span:hover{
		 font-weight: bold;
	}

td > span.active{
		border: 1px solid #98cd60;
		border-radius: 30px;
	}
td > span:active{
		border: 2px solid #98cd60;
		border-radius: 30px;
	}



.schedule{
	margin: 0;
}

.tabs{
	margin: 0; padding: 0;
	text-align: justify;
	line-height: 0px;
	font-size: 0px;
  background-color: #6b6b6b;
}
.tabs	.tab{
		display: inline-block;
		width: 33.3333%;
		background-color: #6b6b6b;
		text-align: center;
		color: #333333;
		margin: 0; padding: 0;
		border: 0px none;
		line-height: 38px;
		font-size: 14px;
		transition: background 0.2s;
}

.tab:active{
			background-color: #999999;
			color: #ffffff;
			font-weight: 600;
}

.tab:avtive:hover{
			background-color: #999999;
}


.tabs	.tab:hover{
			background-color: #777777;
		}

		a{
			color: inherit;
			text-decoration: none;
		}

	.tabs:after{
		content: '';
		width: 100%;
		display: inline-block;
	}


.schedule-list{
	padding: 20px;
	margin-left: 37px;
	border-left: 2px solid #cccccc;
	display: block;
}

.schedule-item{
	display: block;
	margin-bottom: 50px;
	padding: 0;
  clear: both;
  min-height: 100px;
  overflow: visible;
}

.schedule-item:last-child{
		margin-bottom: 10px;
    min-height: 30px;
	}

.time{
		display: block;
		float: left;
		margin-left: -41px;
		width: 36px; height: 36px;
		border: 2px solid #cccccc;
		background-color: #ffffff;
		color: #cccccc;
		border-radius: 40px;
		text-align: center;
		padding: 0px;
		line-height: 25px;
}
.time	span{
			font-size: 12px;
			height: 10px;
			margin: auto;
			display: block;
		}

.description{
		display: block;
    float: left;
    width: 305px;
		margin-top: 10px;
		margin-left: 10px;
		color: #fd9a4a;
		font-size: 14px;
    overflow: visible;
}

.description	.description-content{
		margin-top: 5px;
}
.description	.description-content p {
				font-size: 12px;
				margin: 0;
				color: #c5c5c5;
			}

.description	.description-content	ul.contact-list{
				margin: 0;
				margin-top: 10px;
				padding: 0;
}
.description	.description-content	ul.contact-list	li.contact{
					overflow: hidden;
					display: block;
					float: left;
					margin: 0; padding: 0;
					border: 2px solid fadeout(#98cd60, 75%);
					border-radius: 60px;
					width: 56px; height: 56px;
					text-decoration: none;
					text-align: center;
					margin-right: 10px;
					transition: all 0.2s;
}
.description	.description-content	ul.contact-list	li.contact	img{
						width: 60px; height: 60px;
					}

.description	.description-content	ul.contact-list	li.contact:hover{
						border: 2px solid #98cd60;
					}

.description	.description-content	ul.contact-list	li.contact.add-contact{
						color: #98cd60;
						font-size: 20px;
						line-height: 60px;
}
.description	.description-content	ul.contact-list	li.contact.add-contact a{
							color: inherit;
							text-decoration: none;
						}

.description .description-content	ul.contact-list	li.contact .add-contact a:hover{
							background-color: fadeout(#98cd60, 75%);
						}

.schedule-item.free .time{
			border: 2px solid #98cd60;
		}

.schedule-item.free .description .description-header{
        background-color: #ffffff;
        color: #c5c5c5;
        display: block;
        float: left;

			}

.schedule-item.free .description .description-content{
        margin-left: 5px;
        margin-top: 0;
        content: '';
        width: 215px;
        display: block;
        float: right;
        background-image:  url(https://dl.dropboxusercontent.com/u/2915418/filler.png);
        background-repeat: no-repeat;
        background-position: right center;
      }
</style>
