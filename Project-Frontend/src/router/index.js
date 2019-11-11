import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import BookSession from '@/components/BookSession'
import TutorAvailabilities from '@/components/TutorAvailabilities'

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
      path: '/tutors/',
      name: 'TutorAvailabilities2',
      component: TutorAvailabilities
    }
  ]
})
