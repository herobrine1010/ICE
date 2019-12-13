<template>
  <div>
    <Carousel :carouselImgUrl="this.$store.state.carouselImgUrl"/>
    <SortMenu/>
    <InfiniteScroll ></InfiniteScroll>
  </div>
</template>

<script>
import Carousel from './common/Carousel'
import SortMenu from './common/SortMenu'
import InfiniteScroll from './common/InfiniteScroll'
export default {
  name: 'MainIndex',
  components: { Carousel, SortMenu, InfiniteScroll }
  // data () {
  //   return {
  //   }
  // }
}
</script>

<style scoped>

</style>
