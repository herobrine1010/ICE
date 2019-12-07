<template>
<div>
  <BreadCrumb :breadcrumbItemList="breadcrumbItemList"/>
  <el-row>
    <el-col :span="12" class="goods-image">
  <Carousel :carouselImgUrl="carouselImgUrl"/>
  </el-col>
  <el-col :span="12">
<el-row>
  <p class="name">{{goodsInfo.name}}</p>
  <el-row class="value">{{goodsInfo.value}}</el-row>
  <el-row>
    <el-col class="platform grey-font" :span="2">平台：</el-col>
  <el-col class="platform grey-font" :span="2" v-for="item in goodsInfo.platform" :key="item">{{item}}</el-col>
  </el-row>
  <el-row>
    <el-col class="publisher grey-font">发行商: {{goodsInfo.publisher}}</el-col>
  </el-row>
  <el-input-number size="small" :min="1" v-model="num" :step="1"></el-input-number>
</el-row>
    <el-row>
      <el-col :span="6">
      <el-button class="buy-now" type="warning">立即购买</el-button>
      </el-col>
      <el-col :span="6" class="tags">
      <el-tag size="mini">{{goodsInfo.tag[0]}}</el-tag>
      <el-tag size="mini">{{goodsInfo.tag[1]}}</el-tag>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
      <el-button class="input-car" type="danger">加入购物车</el-button>
      </el-col>
      <el-col :span="6">
        <el-button @click="staring" :type="star_button_type" icon="el-icon-star-off" circle  size="small" class="star"></el-button>
      </el-col>
    </el-row>
  </el-col>
  </el-row>
  <br>
  <br>
  <br>
  <div class="body-down">
  <el-row>
      <el-divider content-position="left">商品详情</el-divider>
      <el-row class="grey-font">发布日期：{{goodsInfo.release_date}}</el-row>
      <el-row >
        <p class="grey-font description">
          {{goodsInfo.description}}
        </p>
      </el-row>
  </el-row>
    <br>
    <br>
  <el-row>
    <el-card class="rate">
      <el-row>评分：</el-row>
      <br>
      <el-rate
        v-model="rate_value"
        disabled
        show-score
        text-color="#ff9900"
        score-template="{value}">
      </el-rate>
    </el-card>
  </el-row>
  <el-row v-for="item in user_evaluation" :key="item.name">
    <el-card  class="user_evaluation">
      <el-row class="user_evaluation_name">{{item.name}}</el-row>
      <el-avatar :src="item.avatar"></el-avatar>
      <el-row class="grey-font">{{item.date}}</el-row>
      <br>
      <el-row class="grey-font">{{item.comment}}</el-row>
    </el-card>
  </el-row>
  </div>
</div>
</template>

<script>
import BreadCrumb from './BreadCrumb'
import Carousel from './Carousel'
import { mapGetters } from 'vuex'
export default {
  name: 'GoodsDetail',
  components: { BreadCrumb, Carousel },
  data () {
    return {
      breadcrumbItemList: ['类别一'],
      carouselImgUrl: [
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20190702171201495133.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20191128153002017543.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20191128153002035028.jpg',
        'http://datafanthfuloss.oss-cn-shanghai.aliyuncs.com/cpsupload/pic/20191128153002085699.jpg'
      ],
      goodsInfo: {
        'name': '名称',
        'value': '￥20',
        'platform': ['平台1', '平台2', '平台3'],
        'publisher': '发行商1',
        'tag': ['标签1', '标签2'],
        'release_date': '2019-12-06',
        'description': '预售商品，建议与现货商品分开下单！\n\n审判' +
          '的时刻到了\n创下了全球销量纪录的动作射击游戏『守望先锋』的' +
          '粘土人第15弹「死神」登场！经典皮肤的服饰上面具的划痕得到' +
          '了再现。包括外套，都采用超可动式样，配合地狱火霰弹枪以及特效' +
          '件可摆出各种您喜欢的姿势。「英雄胜利姿势」搭配特效纸，还可再现' +
          '暗影步。另外如同亡灵般的大衣配件也附带在内，可以期待使用幽灵形态' +
          '的场景！\n\n请和陆续登场的其他守望先锋角色粘土人组合在一起玩耍吧！\n'
      },
      num: 0,
      star_button_type: '',
      rate_value: 4.5,
      user_evaluation: [
        {
          'avatar': 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          'name': '用户1',
          'date': '2019-12-6',
          'comment': 'hhhhhhhhhhhhhhhhhhhhhh'
        },
        {
          'avatar': 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          'name': '用户2',
          'date': '2019-12-6',
          'comment': 'emmmmmmmmmmmmm'
        },
        {
          'avatar': 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          'name': '用户3',
          'date': '2019-12-6',
          'comment': '2333333333333333333333'
        }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'goodsList'
    ])
  },
  methods: {
    staring () {
      if (this.star_button_type === '') {
        this.star_button_type = 'warning'
      } else {
        this.star_button_type = ''
      }
      console.log(this.star)
    }
  }
}
</script>

<style scoped>
.goods-image{
  padding-left: 22%;
  padding-right: 8%;
}
.name{
  font-size: 25px;
  font-family: "Microsoft Yahei",Helvetica, Tahoma, Arial, "DIN  Alternate","PingFang SC", "Hiragino Sans GB", "Heiti SC", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;
  display: block;
  font-weight: bold;
}
.value{
  font-size: 20px;
  color: #ea1309;
}
  .grey-font{
    color: #797979;
    font-size: 14px;
    height: 18px;
    line-height: 18px;
    font-family: "Microsoft Yahei",Helvetica, Tahoma, Arial, "DIN  Alternate","PingFang SC", "Hiragino Sans GB", "Heiti SC", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;
  }
  .platform{
    margin-top: 20px;
    margin-left: 10px;
  }
  .publisher{
    margin-top: 15px;
    margin-left: 10px;
    margin-bottom: 30px;
  }
  .buy-now{
    width: 150px;
    margin-top: 30px;
  }
  .input-car{
    width: 150px;
    margin-top: 5px;
  }
.tags{
  display: block;
  margin-top: 50px;
  margin-left: 100px;
}
  .star{
    margin-top: 15px;
    margin-left: 150px;
  }
  .star:hover{
    color: #545c64;
    background-color: #ffecec;
    border-color: #99a9bf;
  }
  .user_evaluation{
    margin-top: 20px;
  }
.rate{
  margin-top: 20px;
}
  .body-down{
    margin-left: 15%;
    width: 70%;
  }
</style>
