<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import Startup from './components/Startup'
import Login from './components/login'
import TutorAvail from './components/TutorAvail'
import Home2 from './components/Home2'
import BookSession from './components/BookSession'
export default {
    name: 'app',
    components: {
    Startup, Login, TutorAvail, Home2, BookSession
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
