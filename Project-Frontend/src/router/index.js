import Vue from 'vue'

import Router from 'vue-router'

import BookSession from '@/components/BookSession'

import TutorAvail from '@/components/TutorAvail'
import Home2 from '@/components/Home2'
import Startup from '@/components/Startup'

import LoginNew from '@/components/LoginNew'

import Review from '@/components/WriteReview'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Startup',
      component: Startup
    },
    {
      path: '/session/:username',
      name: 'BookSession',
      component: BookSession
    },
    {
      path: '/tutors/:id/:username',
      name: 'TutorAvailabilities',
      component: TutorAvail
    },
    {
      path: '/home/:username',
      name: 'Home2',
      component: Home2
    },
    {
      path: '/login/:id',
      name: 'login',
      component: LoginNew
    },
    {
        path: '/review/:username',
        name: 'Review',
        component: Review
    }
  ]
})
