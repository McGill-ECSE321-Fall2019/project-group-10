<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  import Startup from './components/Startup'

  import LoginNew from './components/LoginNew'
  import TutorAvail from './components/TutorAvail'
  import Home2 from './components/Home2'

  import BookSession from './components/BookSession'

  import Review from './components/WriteReview'

export default {
    name: 'app',
    components: {
    Startup, LoginNew, TutorAvail, Home2, BookSession, Review
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
