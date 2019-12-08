<template>
  <div>
  <el-row>
    <el-col :span="4" v-for="(o, index) in 4" :key="o" :offset="index > 0 ? 1 : 2">
      <div>
      <el-card  shadow="hover" @click.native="enterGoodsDetail(0)">
        <img :src="goodsInfo.imgSrc" class="image">
        <div style="padding: 14px;">
          <el-row class="name">{{goodsInfo.name}}</el-row>
          <el-row class="value">{{goodsInfo.value}}</el-row>
          <el-row class="tags">
          <el-tag size="mini">{{goodsInfo.tag[0]}}</el-tag>
          <el-tag size="mini">{{goodsInfo.tag[1]}}</el-tag>
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
      this.$router.push('/GoodsDetail/' + this.goodsInfo.id + '')
    }
  }
}
</script>

<style scoped>
  .el-card{
    width: 100%;
    cursor:pointer;
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
    font-size: large;
  }

  .value{
    font-size: x-large;
    color: #ea1309;
  }

  .tags{
    display: flex;
    justify-content: flex-end;
  }

  .image {
    max-width: 100%;
    height: auto;
    display: block;
  }
</style>
