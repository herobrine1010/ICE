<template>
  <div>
  <el-row>
    <el-col :span="4" v-for="(o, index) in goodsInfo.length" :key="o" :offset="index > 0 ? 1 : 2">
      <div>
      <el-card  shadow="hover" @click.native="enterGoodsDetail(goodsInfo[index].id)">
        <img :src="goodsInfo[index].imgSrc" class="image">
        <div style="padding: 14px;">
          <el-row class="name">{{goodsInfo[index].name}}</el-row>
          <el-row class="value">{{goodsInfo[index].value}}</el-row>
          <el-row class="tags">
          <el-tag v-for="tag in goodsInfo[index].tag" :key="tag" size="mini">{{tag}}</el-tag>
          </el-row>
        </div>
      </el-card>
      </div>
    </el-col>
  </el-row>
  </div>
</template>

<script>
export default {
  name: 'Goods',
  props: ['goodsInfo'],
  methods: {
    enterGoodsDetail (goodsId) {
      // window.sessionStorage.setItem('activePath', '/GoodsDetail')
      this.$router.push('/GoodsDetail/' + goodsId + '')
    },
    loadingGoods () {
      // console.log('Goods中加载商品', this.goodsInfo)
      for (let index in this.goodsInfo) {
        if (this.goodsInfo[index] === undefined) {
          this.goodsInfo.splice(index, 1)
        }
      }
    }
  },
  // created () {
  //   this.loadingGoods()
  // },
  updated () {
    this.loadingGoods()
  }
}
</script>

<style scoped>
  .el-card{
    width: 100%;
    cursor:pointer;
    height: 380px;
  }
  .el-card:hover{
      background-color:#e0e0e0;
  }
  .el-col{
    margin-bottom: 15px;
  }
  .el-tag{
    margin-left: 5px;
  }
  .name{
    margin-top: 5px;
    height: 50px;
    font-size: large;
  }

  .value{
    margin-top: 5px;
    height: 30px;
    font-size: x-large;
    color: #ea1309;
  }

  .tags{
    margin-top: 5px;
    height: 10px;
    display: flex;
    justify-content: flex-end;
  }

  .image {
    max-width: 100%;
    height: auto;
    display: block;
  }
</style>
