<template>
	<div>

		<table width="100%" align="center">
			<tr>
				<td>
				<p left-margin="0" right-margin="100%">Currently logged in as {{username}}</p>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>

		<h1> Welcome to your Home Page</h1>

		<div id="profile_details">
			<label> <h4>Profile Details:</h4>
				<table>
					<tr>
						<td> <h5> Username: </h5> </td>
						<td> <p>{{username}}</p> </td>
						<td>
						</td>
						<td></td><td></td><td></td><td></td><td></td><td></td>
					</tr>
					<tr>
						<td> <h5> Email: </h5> </td>
						<td> <p>{{User.email}}</p> </td>
						<td>
						</td>
						<td></td><td></td><td></td><td></td><td></td><td></td>
					</tr>
					<tr>
						<td> <h5> Name: </h5> </td>
						<td> <input id="name" type="text" v-model="newName" :placeholder="User.name"> </td>
						<td>
							<b-button id='update_name' variant="outline-primary" type='button' v-bind:disabled="!newName" @click="updateName(newName)">Update</b-button>
						</td>
						<td></td><td></td><td></td><td></td><td></td><td></td>
					</tr>
					<tr>
						<td> <h5> Age: </h5> </td>
						<td> <input id="age" type="text" v-model="newAge" :placeholder="User.age"> </td>
						<td>
							<b-button id='update_age' variant="outline-primary" type='button' v-bind:disabled="!newAge" @click="updateAge(newAge)">Update</b-button>
						</td>
						<td></td><td></td><td></td><td></td><td></td><td></td>
					</tr>
					<tr>
						<td> <h5> Phone Number: </h5> </td>
						<td> <input id="phone" type="text" v-model="newNumber" :placeholder="User.phoneNumber"> </td>
						<td>
							<b-button id='update_number' variant="outline-primary" type='button' v-bind:disabled="!newNumber" @click="updatePhone(newNumber)">Update</b-button>
						</td>
						<td></td><td></td><td></td><td></td><td></td><td></td>
					</tr>
				</table>
			</label>
			 <span style="color:red">{{errorUpdate}}</span>

		</div>

		<hr>
		<div>
			<ul>
              <li v-for="(session, i) in sessions" v-bind:key="`session-${i}`" style="list-style-type: disc;">
                <span>{{session.date}} {{session.time}}, tutor: {{session.tutor.username}}</span>
              </li>
            </ul>
		</div>
		<span style="color:red">{{errorSessions}}</span>
		{{sessions}}

		<hr>
		<b-button id='review' variant="outline-primary" type='button' @click="goToReview()">Write a Review</b-button>

		<b-button id='session' variant="outline-success" type='button' @click="goToSession()">Create a Session</b-button>

		<b-button id='logout' variant="outline-secondary" type='button'  @click="logout()">Logout</b-button>
	</div>
</template>

<script src="./home.js">
</script>

<style>
</style>