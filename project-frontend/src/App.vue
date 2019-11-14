<template>
  <div id="app">
    <!-- <Startup /> -->
    <!-- <Login /> -->
    <!-- <TutorAvail /> -->
    <BookSession />
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
  name: 'app'
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
