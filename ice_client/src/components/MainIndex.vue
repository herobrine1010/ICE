<template>
  <div>
    <div class="carouse-img">
    <Carousel :carouselImgUrl="carouselImgUrl" class="carouse-img"/>
    </div>
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
  components: { Carousel, SortMenu, InfiniteScroll },
  // created () {
  //   console.log("imageURL", this.$store.state.carouselImgUrl)
  // }
  data () {
    return {
      carouselImgUrl: [
        'http://localhost:8021/images/main_page/1.jpg',
        'http://localhost:8021/images/main_page/2.jpg',
        'http://localhost:8021/images/main_page/3.jpg'
      ]
    }
  }
}
</script>

<style scoped>
.carouse-img{
  padding-left: 8%;
  padding-right: 12%;
}
</style>
