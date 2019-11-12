import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import BookSession from '@/components/BookSession'
import TutorAvailabilities from '@/components/TutorAvailabilities'
import Home from '@/components/Home'
import Startup from '@/components/Startup'
import Login from '@/compoents/Login'
import Registration from '@/compoents/Registration'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/session',
      name: 'BookSession',
      component: BookSession
    },
    {
      path: '/tutors/:id',
      name: 'TutorAvailabilities',
      component: TutorAvailabilities
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
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
