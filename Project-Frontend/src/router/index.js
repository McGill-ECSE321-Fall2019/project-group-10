import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import BookSession from '@/components/BookSession'
import TutorAvailabilities from '@/components/TutorAvailabilities'
import Home from '@/components/Home'
import Home2 from '@/components/Home2'
import Startup from '@/components/Startup'
import Login from '@/components/Login'
import Registration from '@/components/Registration'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/session/:username',
      name: 'BookSession',
      component: BookSession
    },
    {
      path: '/tutors/:id/:username',
      name: 'TutorAvailabilities',
      component: TutorAvailabilities
    },
    {
      path: '/home/:username',
      name: 'Home',
      component: Home2
    },
    {
      path: '/startup',
      name: 'Startup',
      component: Startup
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Registration',
      component: Registration
    }
  ]
})
