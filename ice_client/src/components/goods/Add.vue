<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加商品</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 提示区域 -->
      <el-alert title="添加商品信息" type="info" center show-icon :closable="false"></el-alert>
      <!-- 步骤条区域 -->
      <el-steps :space="200" :active="activeIndex-0" finish-status="success" align-center>
        <el-step title="基本信息"></el-step>
        <el-step title="商品参数"></el-step>
        <el-step title="商品属性"></el-step>
        <el-step title="商品图片"></el-step>
        <el-step title="商品内容"></el-step>
        <el-step title="完成"></el-step>
      </el-steps>

      <!-- tab栏区域 -->
      <el-form
        :model="addForm"
        :rules="addFormRules"
        ref="addFormRef"
        label-width="100px"
        label-position="top"
      >
        <el-tabs
          v-model="activeIndex"
          :tab-position="'left'"
          :before-leave="beforeTabLeave"
          @tab-click="tabClicked"
        >
          <el-tab-pane label="基本信息" name="0">
            <el-form-item label="游戏名称" prop="game_name">
              <el-input v-model="addForm.game_name" placeholder></el-input>
            </el-form-item>
            <el-form-item label="游戏价格" prop="price">
              <el-input v-model="addForm.price" placeholder></el-input>
            </el-form-item>
            <el-form-item label="发行日期">
              <el-date-picker
                v-model="addForm.release_date"
                align="right"
                type="date"
                placeholder="选择日期"
                :picker-options="pickerOptions"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="游戏分类" prop="category">
              <el-select v-model="addForm.category" clearable placeholder="请选择">
                <el-option
                  v-for="(item, index) in catelist"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品参数" name="1">
            <!-- 渲染表单的Item项 -->
            <!-- <el-form-item :label="item.attr_name" v-for="item in manyTableData" :key="item.attr_id"> -->
            <!-- 复选框组 -->
            <!-- <el-checkbox-group v-model="item.attr_vals"> -->
            <!-- <el-checkbox :label="cb" v-for="(cb, i) in item.attr_vals" :key="i" border></el-checkbox> -->
            <!-- </el-checkbox-group> -->
            <!-- </el-form-item> -->
            <el-form-item label="游戏平台">
              <el-checkbox-group v-model="addForm.consoles">
                <el-checkbox-button v-for="con in consoleOptions" :label="con" :key="con">{{con}}</el-checkbox-button>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item label="游戏标签">
              <el-checkbox-group v-model="addForm.tags">
                <el-checkbox-button v-for="(tag, index) in tagOptions" :label="tag.tagId" :key="index">{{tag.tagName}}</el-checkbox-button>
              </el-checkbox-group>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品属性" name="2">
            <el-form-item label="是否打折">
              <el-radio-group v-model="addForm.discount" size="small">
                <el-radio-button label="On"></el-radio-button>
                <el-radio-button label="Off"></el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否预售">
              <el-radio-group v-model="addForm.pre_order" size="small">
                <el-radio-button label="On"></el-radio-button>
                <el-radio-button label="Off"></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品图片" name="3">
            <!-- 选择一张图片作为游戏的封面上传 -->
            <el-form-item label="封面图片（只能上传一张）">
            <!-- action表示图片要上传到的后台API地址 -->
            <el-upload
              :action="uploadURL"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              list-type="picture"
              :on-success="handleSuccess"
              :limit="1"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            </el-form-item>
            <!-- 选择一张或多张图片作为游戏的详情图片上传 -->
            <el-form-item label="详情图片（最多上传5张）">
            <!-- action表示图片要上传到的后台API地址 -->
            <el-upload
              :action="uploadURL"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              list-type="picture"
              :on-success="handleSuccess"
              :limit="5"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品内容" name="4">
            <!-- 富文本编辑器组件 -->
            <quill-editor v-model="addForm.goods_introduce"></quill-editor>
            <!-- 添加商品的按钮 -->
            <el-button type="primary" class="btnAdd" @click="add">添加商品</el-button>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-card>

    <!-- 图片预览 -->
    <el-dialog title="图片预览" :visible.sync="previewVisible" width="40%">
      <img :src="previewPath" alt class="previewImg" />
    </el-dialog>
  </div>
</template>

<script>
const consoleOptions = ['PS3', 'PS4', 'PS Vita', 'PSP', 'Nintendo Switch', 'Nintendo 3DS', 'Xbox 360', 'Xbox one']

export default {
  data () {
    // 验证价格的规则
    var checkPrice = (rule, value, cb) => {
      // 验证价格的正则表达式
      const regPrice = /^[0-9]+\.[0-9]{2}$/

      if (regPrice.test(value)) {
        // 合法的价格
        return cb()
      }

      cb(new Error('Please enter legal price, two decimal places after the decimal point'))
    }
    return {
      activeIndex: '0',
      // 添加商品的表单对象
      addForm: {
        game_name: '',
        price: 0,
        release_date: '',
        // 商品分类单选
        category: '',
        // 游戏平台多选
        consoles: [],
        // 游戏标签多选
        tags: [],
        // 是否打折
        discount: 'Off',
        // 是否预售
        pre_order: 'Off',
        // 图片的数组
        pics: [],
        // 商品的详情描述
        goods_introduce: '',
        attrs: []
      },
      addFormRules: {
        game_name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入商品价格', trigger: 'blur' },
          { validator: checkPrice, trigger: 'blur' }
        ]
      },
      // 日期选择数据
      pickerOptions: {
        // disabledDate (time) {
        //   return time.getTime() > Date.now()
        // },
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      // 商品分类列表
      catelist: [
        {
          value: '选项1',
          label: 'Action'
        }, {
          value: '选项2',
          label: 'Adventure'
        }, {
          value: '选项3',
          label: 'Arcade'
        }, {
          value: '选项4',
          label: 'Board Games'
        }, {
          value: '选项5',
          label: 'Racing'
        }, {
          value: '选项6',
          label: 'Role-Playing Games'
        }, {
          value: '选项7',
          label: 'Shooter'
        }, {
          value: '选项8',
          label: 'Simulation'
        }, {
          value: '选项9',
          label: 'Sports'
        }, {
          value: '选项10',
          label: 'Strategy'
        }
      ],
      // 游戏平台选项
      consoleOptions,
      // 游戏标签选项
      tagOptions: [],
      // 上传图片的URL地址
      uploadURL: 'https://jsonplaceholder.typicode.com/posts/',
      previewPath: '',
      previewVisible: false
    }
  },
  created () {
    this.getTagsList()
    this.getCateList()
  },
  methods: {
    // 获取所有商品分类数据-------------------------------
    getCateList () {
      // const { data: res } = await this.$http.get('categories')
      // if (res.meta.status !== 200) {
      //   return this.$message.error('获取商品分类数据失败！')
      // }
      // this.catelist = res.data
      // console.log(this.catelist)
    },
    // 获取所有的tags
    getTagsList () {
      this.$axios.get('/api/getAllTags')
        .then(response => {
          console.log(response)
          this.tagOptions = response.data.result
        })
    },
    beforeTabLeave (activeName, oldActiveName) {
      // console.log('即将离开的标签页名字是:' + oldActiveName)
      // console.log('即将进入的标签页名字是:' + activeName)
      // return false
      if (oldActiveName === '0' && this.addForm.category === '') {
        this.$message.error('请先选择商品分类')
        return false
      }
      if (oldActiveName === '0' && this.addForm.release_date === '') {
        this.$message.error('请先选择发行日期')
        return false
      }
    },
    tabClicked () {
      // 证明访问的是动态参数面板
      // if (this.activeIndex === '1') {
      //   const { data: res } = await this.$http.get(
      //     `categories/${this.cateId}/attributes`,
      //     {
      //       params: { sel: 'many' }
      //     }
      //   )
      //   if (res.meta.status !== 200) {
      //     return this.$message.error('获取动态参数列表失败！')
      //   }
      //   console.log(res.data)
      //   res.data.forEach(item => {
      //     item.attr_vals =
      //       item.attr_vals.length === 0 ? [] : item.attr_vals.split(' ')
      //   })
      //   this.manyTableData = res.data
      // } else if (this.activeIndex === '2') {
      //   const { data: res } = await this.$http.get(
      //     `categories/${this.cateId}/attributes`,
      //     {
      //       params: { sel: 'only' }
      //     }
      //   )
      //   if (res.meta.status !== 200) {
      //     return this.$message.error('获取静态属性失败！')
      //   }
      //   console.log(res.data)
      //   this.onlyTableData = res.data
      // }
    },
    // 处理图片预览效果
    handlePreview (file) {
      console.log(file)
      this.previewPath = file.url
      this.previewVisible = true
    },
    // 处理移除图片的操作
    handleRemove (file) {
      // console.log(file)
      // // 1. 获取将要删除的图片的临时路径
      // const filePath = file.response.data.tmp_path
      // // 2. 从 pics 数组中，找到这个图片对应的索引值
      // const i = this.addForm.pics.findIndex(x => x.pic === filePath)
      // // 3. 调用数组的 splice 方法，把图片信息对象，从 pics 数组中移除
      // this.addForm.pics.splice(i, 1)
      // console.log(this.addForm)
    },
    // 监听图片上传成功的事件
    handleSuccess (response) {
      // console.log(response)
      // // 1. 拼接得到一个图片信息对象
      // const picInfo = { pic: response.data.tmp_path }
      // // 2. 将图片信息对象，push 到pics数组中
      // this.addForm.pics.push(picInfo)
      // console.log(this.addForm)
    },
    // 添加商品
    add () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) {
          return this.$message.error('请填写必要的表单项')
        }
        // 得到分类ID
        let cateID = null
        console.log('cate', this.addForm.category)
        for (let item in this.catelist) {
          if (this.addForm.category === this.catelist[item].value) {
            cateID = parseInt(item) + 1
            break
          }
        }
        console.log(cateID)
        // 得到平台ID
        let consoleIDList = []
        for (let index in this.addForm.consoles) {
          switch (this.addForm.consoles[index]) {
            case 'PS3':
              consoleIDList.push(1)
              break
            case 'PS4':
              consoleIDList.push(2)
              break
            case 'PS Vita':
              consoleIDList.push(3)
              break
            case 'PSP':
              consoleIDList.push(4)
              break
            case 'Nintendo Switch':
              consoleIDList.push(5)
              break
            case 'Nintendo 3DS':
              consoleIDList.push(6)
              break
            case 'xbox 360':
              consoleIDList.push(7)
              break
            case 'xbox one':
              consoleIDList.push(8)
              break
            default:
              break
          }
        }
        // 是否打折
        let isDiscount = null
        if (this.addForm.discount === 'Off') {
          isDiscount = 0
        } else {
          isDiscount = 1
        }
        // 是否预定
        let isPreOrder = null
        if (this.addForm.pre_order === 'Off') {
          isPreOrder = 0
        } else {
          isPreOrder = 1
        }
        let gameAdder = {
          title: this.addForm.game_name,
          price: this.addForm.price,
          discount: isDiscount,
          release_date: this.addForm.release_date,
          pre_order: isPreOrder,
          description: this.addForm.goods_introduce,
          cate_id: cateID,
          list_console_id: consoleIDList,
          list_tag_id: this.addForm.tags
        }
        console.log(this.addForm.tags)
        console.log(gameAdder)
        this.$axios.post('/api/addGame', gameAdder)
          .then(response => {
            console.log(response)
            this.$message.success('上架游戏成功')
          })
          .catch(() => {
            this.$message.error('上架游戏失败')
          })
        // 执行添加的业务逻辑
        // lodash cloneDeep(obj)
        // const form = _.cloneDeep(this.addForm)
        // form.goods_cat = form.goods_cat.join(',')
        // console.log(form)
        // // 处理动态参数
        // this.manyTableData.forEach(item => {
        //   const newInfo = {
        //     attr_id: item.attr_id,
        //     attr_value: item.attr_vals.join(' ')
        //   }
        //   this.addForm.attrs.push(newInfo)
        // })
        // // 处理静态属性
        // this.onlyTableData.forEach(item => {
        //   const newInfo = { attr_id: item.attr_id, attr_value: item.attr_vals }
        //   this.addForm.attrs.push(newInfo)
        // })
        // form.attrs = this.addForm.attrs
        // console.log(form)

        // // 发起请求添加商品
        // // 商品的名称，必须是唯一的
        // const { data: res } = await this.$http.post('goods', form)

        // if (res.meta.status !== 201) {
        //   return this.$message.error('添加商品失败！')
        // }

        // this.$message.success('添加商品成功！')
        // this.$router.push('/goods')
      })
    }
  }
}
</script>

<style lang="less" scoped>
.el-checkbox {
  margin: 0 10px 0 0 !important;
}

.previewImg {
  width: 100%;
}

.btnAdd {
  margin-top: 15px;
}
</style>
