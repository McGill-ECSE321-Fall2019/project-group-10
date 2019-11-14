<template>
  <div id="app">
    <!-- <Startup />
    <hr>
    <Login />
    <hr>
    <TutorAvail />
    <hr> -->
    <BookSession />
    <!-- <br> -->
    <router-view></router-view>
  </div>
</template>

<script>
import Startup from './components/Startup'
import Login from './components/login'
import TutorAvail from './components/TutorAvail'
import Home from './components/Home'
import BookSession from './components/BookSession'
export default {
  name: 'app',
  components: {
    Startup, Login, TutorAvail, Home, BookSession
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;

}
</style>
