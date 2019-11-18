<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <!-- 添加角色按钮区域 -->
      <el-row>
        <el-col>
          <el-button type="primary">添加角色</el-button>
        </el-col>
      </el-row>
      <!-- 角色列表区域 -->
      <el-table :data="roleList" border stripe>
        <!-- 展开列 -->
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-row
              :class="['bdbottom', i1 === 0 ? 'bdtop':'', 'vcenter']"
              v-for="(item1, i1) in scope.row.children"
              :key="item1.id"
            >
              <!-- 渲染一级权限 -->
              <el-col :span="5">
                <el-tag>{{ item1.authName }}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>
              <!-- 渲染二级和三级权限 -->
              <el-col :span="19">
                <!-- 通过 for 循环嵌套渲染二级权限 -->
                <el-row
                  :class="[i2 === 0 ? '':'bdtop', 'vcenter']"
                  v-for="(item2, i2) in item1.children"
                  :key="item2.id"
                >
                  <el-col :span="6">
                    <el-tag type="success">{{ item2.authName }}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <el-col :span="18">
                    <el-tag
                      type="warning"
                      v-for="(item3) in item2.children"
                      :key="item3.id"
                      closable
                      @close="removeRightById()"
                    >{{ item3.authName }}</el-tag>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>
        </el-table-column>
        <!-- 索引列 -->
        <el-table-column type="index"></el-table-column>
        <el-table-column label="角色名称" prop="roleName"></el-table-column>
        <el-table-column label="角色描述" prop="roleDesc"></el-table-column>
        <el-table-column label="操作" width="300px">
          <template slot-scope>
            <el-button size="mini" type="primary" icon="el-icon-edit">编辑</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete">删除</el-button>
            <el-button size="mini" type="warning" icon="el-icon-setting">分配权限</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 所有角色的列表数据
      roleList: [
        {
          id: '0',
          roleName: '主管',
          roleDesc: '技术负责人',
          children: [
            {
              id: '00',
              authName: '商品管理',
              path: null,
              children: [
                {
                  id: '000',
                  authName: '商品列表',
                  path: null,
                  children: [
                    {
                      id: '0000',
                      authName: '商品修改',
                      path: null
                    }
                  ]
                },
                {
                  id: '001',
                  authName: '分类参数',
                  path: null,
                  children: [
                    {
                      id: '0010',
                      authName: '获取参数列表',
                      path: null
                    },
                    {
                      id: '0011',
                      authName: '创建商品参数',
                      path: null
                    },
                    {
                      id: '0012',
                      authName: '删除商品参数',
                      path: null
                    }
                  ]
                },
                {
                  id: '002',
                  authName: '商品分类',
                  path: null,
                  children: [
                    {
                      id: '0020',
                      authName: '添加分类',
                      path: null
                    },
                    {
                      id: '0021',
                      authName: '删除分类',
                      path: null
                    },
                    {
                      id: '0022',
                      authName: '获取分类详情',
                      path: null
                    }
                  ]
                }
              ]
            },
            {
              id: '01',
              authName: '订单管理',
              path: null,
              children: [
                {
                  id: '010',
                  authName: '订单列表',
                  path: null,
                  children: [
                    {
                      id: '0100',
                      authName: '添加订单',
                      path: null
                    }
                  ]
                }
              ]
            },
            {
              id: '02',
              authName: '权限管理',
              path: null,
              children: [
                {
                  id: '020',
                  authName: '角色列表',
                  path: null,
                  children: [
                    {
                      id: '0200',
                      authName: '添加角色',
                      path: null
                    },
                    {
                      id: '0201',
                      authName: '删除角色',
                      path: null
                    },
                    {
                      id: '0202',
                      authName: '角色授权',
                      path: null
                    },
                    {
                      id: '0203',
                      authName: '取消角色授权',
                      path: null
                    },
                    {
                      id: '0204',
                      authName: '获取角色列表',
                      path: null
                    },
                    {
                      id: '0205',
                      authName: '获取角色详情',
                      path: null
                    },
                    {
                      id: '0206',
                      authName: '更新角色信息',
                      path: null
                    },
                    {
                      id: '0207',
                      authName: '更新角色权限',
                      path: null
                    }
                  ]
                },
                {
                  id: '021',
                  authName: '权限列表',
                  path: null,
                  children: [
                    {
                      id: '0210',
                      authName: '查看权限',
                      path: null
                    }
                  ]
                }
              ]
            },
            {
              id: '03',
              authName: '用户管理',
              path: null,
              children: [
                {
                  id: '030',
                  authName: '用户列表',
                  path: null,
                  children: [
                    {
                      id: '0300',
                      authName: '添加用户',
                      path: null
                    },
                    {
                      id: '0301',
                      authName: '删除用户',
                      path: null
                    },
                    {
                      id: '0302',
                      authName: '更新用户',
                      path: null
                    },
                    {
                      id: '0303',
                      authName: '获取用户详情',
                      path: null
                    },
                    {
                      id: '0304',
                      authName: '分配用户角色',
                      path: null
                    }
                  ]
                }
              ]
            },
            {
              id: '04',
              authName: '数据统计',
              path: null,
              children: [
                {
                  id: '040',
                  authName: '数据报表',
                  path: null,
                  children: [
                    {
                      id: '0400',
                      authName: '查看数据',
                      path: null
                    }
                  ]
                }
              ]
            }
          ]
        },
        {
          id: '1',
          roleName: '测试角色1',
          roleDesc: '测试角色描述1',
          children: []
        },
        {
          id: '2',
          roleName: '测试角色2',
          roleDesc: '测试角色描述2',
          children: []
        },
        {
          id: '3',
          roleName: '测试角色3',
          roleDesc: '测试角色描述3',
          children: []
        },
        {
          id: '4',
          roleName: '测试角色4',
          roleDesc: '测试角色描述4',
          children: []
        },
        {
          id: '5',
          roleName: '测试角色5',
          roleDesc: '测试角色描述5',
          children: []
        }
      ]
    }
  },
  created() {
    this.getRolesList()
  },
  methods: {
    // 获取所有角色的列表
    getRolesList() {},
    // 根据Id删除对应的权限
    async removeRightById() {
      // 探矿提示用户是否要删除
      const confirmResult = await this.$confirm('此操作将永久删除该文件，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      // 如果用户确认删除，则返回值为字符串 confirm
      // 如果用户取消删除，则返回值为字符串 cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('取消了删除')
      }
      this.$message.success('虚假的删除成功')
    }
  }
}
</script>

<style lang="less" scoped>
.el-tag {
  margin: 7px;
}

.bdtop {
  border-top: 1px solid #eee;
}

.bdbottom {
  border-bottom: 1px solid #eee;
}

.vcenter {
  display: flex;
  align-items: center;
}
</style>
