import axios from 'axios'
export const mixin = {
  data () {
    return {
      HOST: 'http://localhost:8080'
    }
  },
  methods: {
    login (userInfo) {
      axios.get(this.HOST + 'login')
        .then(response => {
          console.log(response)
        })
    }
  }
}
