<template>
  <div>
    <Carousel :carouselImgUrl="carouselImgUrl"/>
    <SortMenu/>
  </div>
</template>

<script>
import Carousel from './common/Carousel'
import SortMenu from './common/SortMenu'
export default {
  name: 'MainIndex',
  components: { Carousel, SortMenu },
  data () {
    return {
      carouselImgUrl: [
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190710183805263591.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20181213104603310708.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190315115404560713.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190516105901911866.jpg'
      ]
    }
  }
}
</script>

<style scoped>

</style>
