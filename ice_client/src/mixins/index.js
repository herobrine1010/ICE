import axios from 'axios'
export const mixin = {
  data () {
    return {
      HOST: 'http://localhost:8011'
    }
  },
  methods: {
    publisherLogin (userInfo) {
      axios.post(this.HOST + 'login', userInfo)
        .then(response => {
          return response.status
        })
        .catch(response => {
          this.$message.error('登陆失败')
        })
    }
  }
}
