# ICE商家后台管理 API 接口文档

## 1. API 接口说明

- 接口基准地址 `http://………………（后端填写）`
- 需要授权的API，必须在请求头中使用`Authorization`字段提供`token`令牌
- 使用 HTTP Status Code 标识状态
- 数据返回格式统一使用 JSON

### 1.1. 支持的请求方法

- GET（SELECT）：从服务端取出资源（一项或多项）。
- POST（CREATE）：在服务器新建一个资源。
- PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
- PARCH（UPDATE）：在服务器更新资源（客户端提供改变的属性）。
- DELETE（DELETE）：从服务器删除资源。
- HEAD：获取资源的元数据。
- OPTIONS：获取信息，关于资源的哪些属性是客户端可以改变的。

### 1.2. 通用返回状态说明

| *状态码* | *含义*                | *说明*                                              |
| -------- | --------------------- | --------------------------------------------------- |
| 200      | OK                    | 请求成功                                            |
| 201      | CREATED               | 创建成功                                            |
| 204      | DELETED               | 删除成功                                            |
| 400      | BAD REQUEST           | 请求的地址不存在或者包含不支持的参数                |
| 401      | UNAUTHORIZED          | 未授权                                              |
| 403      | FORBIDDEN             | 被禁止访问                                          |
| 404      | NOT FOUND             | 请求的资源不存在                                    |
| 422      | Unprocesable entity   | [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误 |
| 500      | INTERNAL SERVER ERROR | 内部错误                                            |

## 2. 登录

### 2.1. 登录验证接口

- 请求路径：publisherLogin
- 请求方法：post
- 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| username | 用户名   | 不能为空 |
| password | 密码     | 不能为空 |

- 相应参数

| 参数名 | 参数说明                 | 备注                 |
| ------ | ------------------------ | -------------------- |
| status | 返回状态                 | 参照1.2              |
| error  | 状态说明                 | 自动生成的session id |
| result | 查询结果，始终是一个List | 该接口下无意义       |

- 响应数据

```json
{
    "status": "200",
    "error": "22AB1062B617299B78E10F99C8C5F595",
    "result": null
}
```

### 2.2. 获取publisher信息

- 请求路径：publisherInfo
- 请求方法：get
- 请求参数：无
- 响应参数

| 参数名         | 参数说明                      | 备注                 |
| -------------- | ----------------------------- | -------------------- |
| status         | 返回状态                      | 参照1.2              |
| error          | 状态说明                      | 自动生成的session id |
| result         | 查询结果，始终是一个List      | 以下为result内的内容 |
| publisher_id   | publisher的id                 | Integer              |
| publisher_name | publisher的名字               | String               |
| logo_path      | publisher的logo的图片文件路径 | String               |
| pwd            | publisher的登陆密码           | String               |
| description    | publisher的简要介绍           | String               |

- 响应数据：

```json
{
    "status": null,
    "error": null,
    "result": [
        {
            "publisher_id": 2,
            "publisher_name": "Naughty Dog",
            "logo_path": null,
            "pwd": null,
            "description": null
        }
    ]
}
```

### 2.3. 更新publisher信息

- 请求路径：updatePublisherInfo
- 请求方法：post
- 请求参数

| 参数名        | 参数说明            | 备注     |
| ------------- | ------------------- | -------- |
| publisherId   | publisher的id       | 可以为空 |
| publisherName | publisher的名字     | 可以为空 |
| pwd           | publisher的登陆密码 | 可以为空 |
| description   | publisher的简要介绍 | 可以为空 |

- 响应参数

| 参数名 | 参数说明                 | 备注               |
| ------ | ------------------------ | ------------------ |
| status | 返回状态                 | 参照1.2            |
| error  | 状态说明                 | 是否更新成功的信息 |
| result | 查询结果，始终是一个List | 此API下无意义      |

- 响应数据

```json
{
    "status": "200",
    "error": "Update Success!",
    "result": null
}
```

## 3. 游戏管理

### 3.1. 初始化游戏列表数据

- 请求路径：initGameList
- 请求方法：get
- 请求参数

| 参数名   | 参数说明     | 备注       |
| -------- | ------------ | ---------- |
| pageSize | 每页显示条数 | 不可以为空 |

- 响应参数

| 参数名       | 参数说明                     | 备注                                                 |
| ------------ | ---------------------------- | ---------------------------------------------------- |
| status       | 返回状态                     | 参照1.2                                              |
| error        | 状态说明                     |                                                      |
| result       | 查询结果，始终是一个List     | 以下为result内的内容                                 |
| game_id      | 游戏ID                       | 可展示，不可修改，后台专属，商家上架游戏时不需要展示 |
| title        | 游戏名称                     | 游戏的名称，商家上架时定义                           |
| price        | 价格                         | 游戏的价格，商家上架时定义，小数点后两位             |
| discount     | 是否打折                     | boolean，是否处于打折状态，商家可修改                |
| average_rate | 游戏评分                     | 玩家打分，可展示，不可修改，小数点后一位，满分5.0    |
| release_date | 发行日期                     | 游戏发行的日期，商家上架时定义，"YYYY-MM-DD"         |
| pre_order    | 是否预售                     | boolean，是否可预售，商家可修改                      |
| rate_count   | 评分数                       | 评分数，可展示，不可修改                             |
| cover        | 封面图片                     | 游戏封面图片，只有一张                               |
| description  | 游戏简介                     | 游戏简介，商家上架时定义                             |
| on_sale      | 是否上架                     | 暂时无用                                             |
| category     | 游戏分类（cateId, cateName） | 分类，商家上架时选择，只能选一个，不可修改           |
| tags_list    | 标签列表（tagId, tagName）   | 标签数组，商家上架时选择，可多选，可修改             |
| pictures     | 详情图片                     | 游戏详情图片数组，可以有多张，详情页展示             |
| consoles     | 游戏平台                     | 游戏平台数组，商家上架时选择，可修改                 |

- 响应数据

```json
{
    "status": "200",
    "error": null,
    "result": [
        {
            "game_id": 2,
            "title": "The Last Of Us",
            "price": 9.99,
            "discount": false,
            "average_rate": -1.0,
            "release_date": "2013-06-13T16:00:00.000+0000",
            "pre_order": false,
            "rate_count": 0,
            "cover": null,
            "description": null,
            "on_sale": true,
            "category": {
                "cateId": 2,
                "cateName": "Adventure"
            },
            "tags_list": [
                {
                    "tagId": 2,
                    "tagName": "Action"
                },
                {
                    "tagId": 3,
                    "tagName": "Adventure"
                },
                {
                    "tagId": 9,
                    "tagName": "voilence"
                },
                {
                    "tagId": 22,
                    "tagName": "Horror"
                },
                {
                    "tagId": 24,
                    "tagName": "Sci-fi"
                },
                {
                    "tagId": 38,
                    "tagName": "Zombies"
                },
                {
                    "tagId": 47,
                    "tagName": "Fighting"
                }
            ],
            "pictures": [
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/1.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/2.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/3.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/4.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/5.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/6.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/7.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/8.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/2/cover.jpg"
            ],
            "consoles": [
                {
                    "consoleId": 1,
                    "consoleName": "PS3"
                }
            ]
        },
        {
            "game_id": 3,
            "title": "The Last Of Us Remastered",
            "price": 19.99,
            "discount": false,
            "average_rate": -1.0,
            "release_date": "2014-07-28T16:00:00.000+0000",
            "pre_order": false,
            "rate_count": 0,
            "cover": null,
            "description": null,
            "on_sale": true,
            "category": {
                "cateId": 2,
                "cateName": "Adventure"
            },
            "tags_list": [
                {
                    "tagId": 2,
                    "tagName": "Action"
                },
                {
                    "tagId": 3,
                    "tagName": "Adventure"
                },
                {
                    "tagId": 9,
                    "tagName": "voilence"
                },
                {
                    "tagId": 22,
                    "tagName": "Horror"
                },
                {
                    "tagId": 38,
                    "tagName": "Zombies"
                },
                {
                    "tagId": 47,
                    "tagName": "Fighting"
                },
                {
                    "tagId": 58,
                    "tagName": "Remaster"
                }
            ],
            "pictures": [
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/1.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/2.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/3.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/4.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/5.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/6.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/7.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/3/cover.jpg"
            ],
            "consoles": [
                {
                    "consoleId": 2,
                    "consoleName": "PS4"
                }
            ]
        },
        {
            "game_id": 4,
            "title": "UNCHARTED 4: A Thief's End",
            "price": 19.99,
            "discount": false,
            "average_rate": -1.0,
            "release_date": "2016-05-09T16:00:00.000+0000",
            "pre_order": false,
            "rate_count": 0,
            "cover": null,
            "description": null,
            "on_sale": true,
            "category": {
                "cateId": 2,
                "cateName": "Adventure"
            },
            "tags_list": [
                {
                    "tagId": 2,
                    "tagName": "Action"
                },
                {
                    "tagId": 3,
                    "tagName": "Adventure"
                },
                {
                    "tagId": 17,
                    "tagName": "Puzzle"
                },
                {
                    "tagId": 60,
                    "tagName": "Runner"
                }
            ],
            "pictures": [
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/4/1.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/4/2.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/4/3.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/4/4.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/4/5.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/4/6.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/4/cover.jpg"
            ],
            "consoles": [
                {
                    "consoleId": 2,
                    "consoleName": "PS4"
                }
            ]
        },
        {
            "game_id": 5,
            "title": "The Last Of Us Part II",
            "price": 59.99,
            "discount": false,
            "average_rate": -1.0,
            "release_date": "2020-05-28T16:00:00.000+0000",
            "pre_order": true,
            "rate_count": 0,
            "cover": null,
            "description": null,
            "on_sale": true,
            "category": {
                "cateId": 2,
                "cateName": "Adventure"
            },
            "tags_list": [
                {
                    "tagId": 2,
                    "tagName": "Action"
                },
                {
                    "tagId": 3,
                    "tagName": "Adventure"
                },
                {
                    "tagId": 9,
                    "tagName": "voilence"
                },
                {
                    "tagId": 22,
                    "tagName": "Horror"
                },
                {
                    "tagId": 38,
                    "tagName": "Zombies"
                },
                {
                    "tagId": 47,
                    "tagName": "Fighting"
                }
            ],
            "pictures": [
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/5/1.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/5/2.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/5/3.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/5/4.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/5/5.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/5/6.jpg",
                "E:/Schooling/大三上/J2E软工项目/ICE/images/games/5/cover.jpg"
            ],
            "consoles": [
                {
                    "consoleId": 2,
                    "consoleName": "PS4"
                }
            ]
        }
    ]
}
```

### 3.2. 在列表中搜索游戏

- 请求路径：searchPublishedGames
- 请求方法：get
- 请求参数

| 参数名      | 参数说明     | 备注                          |
| ----------- | ------------ | ----------------------------- |
| query       | 搜索关键字   | 只会匹配游戏Title，大小写随意 |
| currentPage | 请求的页码   | 不可以为空                    |
| pageSize    | 每页显示条数 | 不可以为空                    |

- 响应参数：同3.1
- 响应数据：同3.1

### 3.3. 跳转至某页码

- 请求路径：jumpToGamePage
- 请求方法：get
- 请求参数

| 参数名      | 参数说明     | 备注       |
| ----------- | ------------ | ---------- |
| currentPage | 请求的页码   | 不可以为空 |
| pageSize    | 每页显示条数 | 不可以为空 |

- 响应参数：同3.1
- 响应数据：同3.1

### 3.4. 上架游戏

- 请求路径：addGame
- 请求方法：post
- 请求参数

| 参数名          | 参数说明   | 备注                                                         |
| --------------- | ---------- | ------------------------------------------------------------ |
| title           | 游戏名称   | 游戏的名称，商家上架时定义                                   |
| price           | 价格       | 游戏的价格，商家上架时定义，小数点后两位                     |
| discount        | 是否打折   | boolean，是否处于打折状态，商家可修改，0:不打折，1:打折      |
| release_date    | 发行日期   | 游戏发行的日期，商家上架时定义，"YYYY-MM-DD"                 |
| pre_order       | 是否预售   | boolean，是否可预售，商家可修改                              |
| description     | 游戏简介   | 游戏简介，商家上架时定义                                     |
| cate_id         | 游戏分类id | 分类，商家上架时选择，只能选一个，不可修改                   |
| cover           | 封面图片   | String，游戏封面图片，只有一张                               |
| list_console_id | 游戏平台   | Integer数组，游戏平台id，商家上架时选择，可修改              |
| list_tag_id     | 标签       | Integer数组，标签id，商家上架时选择，可多选，可修改          |
| pictures        | 详情图片   | String数组，游戏详情图片，可以有多张，详情页展示，上传的图片临时路径 |

- 请求数据

```json
{
	"title": "Test0",
	"price": 19.99,
	"discount": 1,
	"release_date": "2018-09-13",
	"pre_order": 0,
	"description": "Test Description",
	"cate_id":1,
	"cover":"path",
	"list_console_id":[
		1,
		2,
		3
	],
	"list_tag_id":[
		5,
		6,
		7,
		8,
		9,
		20
	],
	"pictures":[
		"path1",
		"path2",
		"path3",
		"path4"
	]
}
```

- 响应参数：同3.1

- 响应数据

```json
{
    "status": "201",
    "error": "Upload Success!",
    "result": [
        {
            "game_id": 8,
            "title": "Test0",
            "price": 19.99,
            "discount": true,
            "average_rate": -1.0,
            "release_date": "2018-09-12T16:00:00.000+0000",
            "pre_order": false,
            "rate_count": 0,
            "cover": null,
            "description": "Test Description",
            "on_sale": true,
            "category": {
                "cateId": 1,
                "cateName": "Action"
            },
            "tags_list": [
                {
                    "tagId": 5,
                    "tagName": "Strategy"
                },
                {
                    "tagId": 6,
                    "tagName": "Simulation"
                },
                {
                    "tagId": 7,
                    "tagName": "RPG"
                },
                {
                    "tagId": 8,
                    "tagName": "Singleplayer"
                },
                {
                    "tagId": 9,
                    "tagName": "voilence"
                },
                {
                    "tagId": 20,
                    "tagName": "Fantacy"
                }
            ],
            "pictures": [],
            "consoles": [
                {
                    "consoleId": 1,
                    "consoleName": "PS3"
                },
                {
                    "consoleId": 2,
                    "consoleName": "PS4"
                },
                {
                    "consoleId": 3,
                    "consoleName": "PS Vita"
                }
            ]
        }
    ]
}
```

### 3.5. 下架游戏

- 请求路径：deleteGame?gameId=
- 请求方法：delete
- 请求参数

| 参数名 | 参数说明 | 备注                  |
| ------ | -------- | --------------------- |
| gameId | 游戏 ID  | 不能为空`携带在url中` |

- 响应数据

```json
{
    "status": "204",
    "error": "Deleting Success!",
    "result": null
}
```

## 4. 订单管理

### 4.1. 初始化订单数据列表

- 请求路径：initOrderList
- 请求方法：get
- 请求参数

| 参数名   | 参数说明     | 备注     |
| -------- | ------------ | -------- |
| pagesize | 每页显示条数 | 不能为空 |

- 响应参数

| 参数名      | 参数说明                           | 备注                                                         |
| ----------- | ---------------------------------- | ------------------------------------------------------------ |
| status      | 返回状态                           | 参照1.2                                                      |
| error       | 状态说明                           | 是否更新成功的信息                                           |
| result      | 查询结果，始终是一个List           | 以下为result内的内容                                         |
| order_id    | 订单ID                             | 可展示，不可修改，订单被创建时后端自动生成                   |
| order_date  | 订单创建时间                       | 可展示，不可修改，订单被创建时后端自动生成                   |
| address     | 订单收货地址                       |                                                              |
| console     | 游戏平台(console_id, console_name) | 订单游戏所选择平台对应的平台，可展示，不可修改               |
| contact_tel | 买家电话号码                       | 买家的电话号码，可展示，可修改                               |
| status      | 订单的状态                         | 待付款 / 待发货 / 待收货 / 待评价 / 已完成 对应 0 / 1 / 2 / 3 / 4 |
| price       | 价格                               | 订单的价格，小数点后两位                                     |
| user_id     | 买家的用户ID                       | Integer                                                      |
| user_name   | 买家的用户名                       |                                                              |
| game_id     | 游戏ID                             | Integer, 可展示，不可修改，后台专属，商家上架游戏时不需要展示 |
| game_name   | 游戏名称                           | 游戏的名称，商家上架时定义                                   |

- 响应数据

```json
{
    "status": "200",
    "error": null,
    "result": [
        {
            "order_id": 2,
            "order_date": "2019-11-30T16:00:00.000+0000",
            "address": "Address2",
            "console": {
                "consoleId": 1,
                "consoleName": "PS3"
            },
            "contact_tel": "17623857792",
            "status": 0,
            "price": 9.99,
            "user_id": 1000001,
            "user_name": "LudwigLiu-CN",
            "game_id": 2,
            "game_name": "The Last Of Us"
        },
        {
            "order_id": 3,
            "order_date": "2019-11-30T16:00:00.000+0000",
            "address": "Address3",
            "console": {
                "consoleId": 2,
                "consoleName": "PS4"
            },
            "contact_tel": "17623857792",
            "status": 0,
            "price": 19.99,
            "user_id": 1000001,
            "user_name": "LudwigLiu-CN",
            "game_id": 3,
            "game_name": "The Last Of Us Remastered"
        }
    ]
}
```

### 4.2. 修改订单状态

- 请求路径：alterOrder
- 请求方法：put
- 请求参数

| 参数名      | 参数说明     | 备注                                                         |
| ----------- | ------------ | ------------------------------------------------------------ |
| ORDER_ID    | 订单 ID      | 不能为空                                                     |
| STATUS      | 订单的状态   | 待付款 / 待发货 / 待收货 / 待评价 / 已完成 对应 0 / 1 / 2 / 3 / 4 |
| PRICE       | 价格         | 订单的价格，小数点后两位                                     |
| ADDRESS     | 订单收货地址 |                                                              |
| CONTACT_TEL | 买家电话号码 | 买家的电话号码，可展示，可修改                               |

- 请求数据说明
  - 数据类型是form-data，所有请求数据都是增量更新，如果参数为空，就不会更新该字段

- 响应数据

```json
{
    "status": "200",
    "error": "Altering success!",
    "result": [
        {
            "order_id": 2,
            "order_date": "2019-11-30T16:00:00.000+0000",
            "address": "Address3",
            "console": {
                "consoleId": 1,
                "consoleName": "PS3"
            },
            "contact_tel": "",
            "status": 1,
            "price": null,
            "user_id": 1000001,
            "user_name": "LudwigLiu-CN",
            "game_id": 2,
            "game_name": "The Last Of Us"
        }
    ]
}
```

