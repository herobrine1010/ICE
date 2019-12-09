<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
      <el-breadcrumb-item :to="{ path: '/MainIndex' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品详情</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row>
      <el-col :span="12" class="goods-image">
        <Carousel :carouselImgUrl="carouselImgUrl" />
      </el-col>
      <el-col :span="12">
        <el-row>
          <p class="name">{{goodsInfo.name}}</p>
          <el-row class="value">￥{{goodsInfo.value}}</el-row>
          <el-row>
            <el-col class="platform grey-font" :span="2">平台：</el-col>
            <el-col
              class="platform grey-font"
              :span="2"
              v-for="item in goodsInfo.platform"
              :key="item"
            >{{item}}</el-col>
          </el-row>
          <el-row>
            <el-col class="publisher grey-font">发行商: {{goodsInfo.publisher}}</el-col>
          </el-row>
        </el-row>
        <el-row>
          <el-tag size="mini" :key="tag" v-for="tag in goodsInfo.tag">{{tag}}</el-tag>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-button class="buy-now" type="danger" @click="showBuyDialog()">立即购买</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-button class="input-car" type="warning">加入购物车</el-button>
          </el-col>
          <el-col :span="6">
            <el-button
              @click="staring"
              :type="star_button_type"
              icon="el-icon-star-off"
              circle
              size="small"
              class="star"
            ></el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <div class="body-down">
      <el-row>
        <el-divider content-position="left">商品详情</el-divider>
        <el-row class="grey-font">发布日期：{{goodsInfo.release_date}}</el-row>
        <el-row>
          <p class="grey-font description">{{goodsInfo.description}}</p>
        </el-row>
      </el-row>
      <el-row>
        <el-card class="rate">
          <el-row>评分</el-row>
          <el-rate v-model="rate" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
        </el-card>
      </el-row>
      <el-row v-for="item in user_evaluation" :key="item.name">
        <el-card class="user_evaluation">
          <el-row class="user_evaluation_name">{{item.name}}</el-row>
          <el-avatar :src="item.avatar"></el-avatar>
          <el-row class="grey-font">{{item.date}}</el-row>
          <el-row class="grey-font">{{item.comment}}</el-row>
        </el-card>
      </el-row>
    </div>

    <!-- 修改游戏商品信息的对话框 -->
    <el-dialog
      title="Buy Game"
      :visible.sync="buyDialogVisible"
      width="50%"
      @close="buyDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="buyForm" :rules="buyFormRules" ref="buyFormRef" label-width="100px">
        <el-form-item label="Game ID">
          <el-input v-model="buyForm.gameid" disabled></el-input>
        </el-form-item>
        <el-form-item label="Title" prop="title">
          <el-input v-model="buyForm.title"></el-input>
        </el-form-item>
        <el-form-item label="Price" prop="price">
          <el-input v-model="buyForm.price"></el-input>
        </el-form-item>
        <el-form-item label="Discount">
          <el-radio-group v-model="buyForm.discount" size="small">
            <el-radio-button label="On"></el-radio-button>
            <el-radio-button label="Off"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Pre Order">
          <el-radio-group v-model="buyForm.pre_order" size="small">
            <el-radio-button label="On"></el-radio-button>
            <el-radio-button label="Off"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Consoles">
          <el-checkbox-group v-model="buyForm.consoles" size="small">
            <el-checkbox-button v-for="con in consoleOptions" :label="con" :key="con">{{con}}</el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="Discription" prop="discription">
          <el-input v-model="buyForm.discription"></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框底部确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelBuyGame">Cancel</el-button>
        <el-button type="primary" @click="buyGame">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Carousel from './Carousel'
import { mapGetters } from 'vuex'
export default {
  name: 'GoodsDetail',
  components: { Carousel },
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
        id: '123',
        name: '名称',
        value: '20',
        platform: ['平台1', '平台2', '平台3'],
        publisher: '发行商1',
        tag: ['标签1', '标签2'],
        release_date: '2019-12-06',
        description: '预售商品，建议与现货商品分开下单！\n\n审判' +
          '的时刻到了\n创下了全球销量纪录的动作射击游戏『守望先锋』的' +
          '粘土人第15弹「死神」登场！经典皮肤的服饰上面具的划痕得到' +
          '了再现。包括外套，都采用超可动式样，配合地狱火霰弹枪以及特效' +
          '件可摆出各种您喜欢的姿势。「英雄胜利姿势」搭配特效纸，还可再现' +
          '暗影步。另外如同亡灵般的大衣配件也附带在内，可以期待使用幽灵形态' +
          '的场景！\n\n请和陆续登场的其他守望先锋角色粘土人组合在一起玩耍吧！\n'
      },
      star_button_type: '',
      rate: 4.5,
      user_evaluation: [
        {
          avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          name: '用户1',
          date: '2019-12-6',
          comment: 'hhhhhhhhhhhhhhhhhhhhhh'
        },
        {
          avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          name: '用户2',
          date: '2019-12-6',
          comment: 'emmmmmmmmmmmmm'
        },
        {
          avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
          name: '用户3',
          date: '2019-12-6',
          comment: '2333333333333333333333'
        }
      ],
      // 购买游戏按钮弹出的对话框-----------------------------------------------------------------------------------
      // 控制修改游戏对话框的显示与隐藏
      buyDialogVisible: false,
      // 查询到的游戏信息对象
      buyForm: {
        gameid: '12345',
        title: 'SUPERMARIO',
        price: '19.90',
        discount: 'On',
        average_rate: '4.8',
        release_date: '1985-09-13',
        pre_order: 'Off',
        rate_count: '100',
        discription: 'The game takes place in the Mushroom Kingdom, and Mario begins his new adventure in order to rescue Princess Peach, kidnapped by Kuba.',
        cover: '/tmp_uploads/30f08d52c551ecb447277eae232304b8',
        pictures: [
          '/tmp_uploads/30f08d52c551ecb447277eae232304b1',
          '/tmp_uploads/30f08d52c551ecb447277eae232304b2',
          '/tmp_uploads/30f08d52c551ecb447277eae232304b3'
        ],
        consoles: [
          'PS4',
          'PS Vita',
          'Xbox one'
        ],
        category: 'Adventure',
        tags: [
          'CARTOON',
          'POPULAR'
        ]
      },
      // 修改表单的验证规则对象
      buyFormRules: {
        title: [
          { required: true, message: 'Please enter game name', trigger: 'blur' }
        ],
        price: [
          { required: true, message: 'Please enter game price', trigger: 'blur' },
          { validator: checkPrice, trigger: 'blur' }
        ],
        discription: [
          { validator: checkDiscription, trigger: 'blur' }
        ]
      }
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

<style lang="less" scoped>
.breadcrumb {
  margin-left: 10%;
  margin-right: 10%;
}
.goods-image {
  padding-left: 22%;
  padding-right: 8%;
}
.name {
  font-size: 25px;
  font-family: 'Microsoft Yahei', Helvetica, Tahoma, Arial, 'DIN  Alternate',
    'PingFang SC', 'Hiragino Sans GB', 'Heiti SC', 'Microsoft YaHei',
    'WenQuanYi Micro Hei', sans-serif;
  display: block;
  font-weight: bold;
}
.value {
  font-size: 20px;
  color: #ea1309;
}
.grey-font {
  color: #797979;
  font-size: 14px;
  height: 18px;
  line-height: 18px;
  font-family: 'Microsoft Yahei', Helvetica, Tahoma, Arial, 'DIN  Alternate',
    'PingFang SC', 'Hiragino Sans GB', 'Heiti SC', 'Microsoft YaHei',
    'WenQuanYi Micro Hei', sans-serif;
}
.platform {
  margin-top: 20px;
  margin-left: 10px;
}
.publisher {
  margin-top: 15px;
  margin-left: 10px;
  margin-bottom: 30px;
}
.buy-now {
  width: 150px;
  margin-top: 30px;
}
.input-car {
  width: 150px;
  margin-top: 10px;
}
.tags {
  display: block;
  margin-top: 50px;
  margin-left: 100px;
}
.star {
  margin-top: 15px;
  margin-left: 150px;
}
.star:hover {
  color: #545c64;
  background-color: #ffecec;
  border-color: #99a9bf;
}
.user_evaluation {
  margin-top: 20px;
}
.rate {
  margin-top: 60px;
}
.body-down {
  margin-top: 30px;
  margin-left: 15%;
  width: 70%;
}
.el-tag {
  margin-right: 15px;
}
</style>
