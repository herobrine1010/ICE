<template>
    <ul
      class="list"
      v-infinite-scroll="load"
      infinite-scroll-disabled="disabled"
      infinite-scroll-distance="10px">
      <Goods :goodsInfo="goodsList[0]" v-for="i in count" :key="i" class="list-item"/>
    </ul>
</template>

<script>
import Goods from './Goods'
import { mapGetters } from 'vuex'
export default {
  components: { Goods },
  data () {
    return {
      count: 2,
      loading: false
    }
  },
  computed: {
    ...mapGetters([
      'goodsList'
    ]),
    noMore () {
      return this.count >= 20
    },
    disabled () {
      return this.loading || this.noMore
    }
  },
  methods: {
    load () {
      this.loading = true
      setTimeout(() => {
        this.count += 2
        this.loading = false
      }, 1000)
    }
  }
}
</script>

<style scoped>
.infinite-list{
  height: 50%;
  overflow: auto;
}
</style>
