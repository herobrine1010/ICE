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

- 请求路径：login
- 请求方法：post
- 请求参数

| 参数名   | 参数说明 | 备注     |
| -------- | -------- | -------- |
| username | 用户名   | 不能为空 |
| password | 密码     | 不能为空 |

- 相应参数

| 参数名   | 参数说明 | 备注            |
| -------- | -------- | --------------- |
| id       | 用户 ID  |                 |
| username | 用户名   |                 |
| mobile   | 手机号   |                 |
| email    | 邮箱     |                 |
| token    | 令牌     | 基于 jwt 的令牌 |

- 响应数据

```json
{
    "data": {
        "id": 500,
        "username": "admin",
        "mobile": "123",
        "email": "123@qq.com",
        "token": "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjUwMCwicmlkIjowLCJpYXQiOjE1MTI1NDQyOTksImV4cCI6MTUxMjYzMDY5OX0.eGrsrvwHm-tPsO9r_pxHIQ5i5L1kX9RX444uwnRGaIM"
    },
    "meta": {
        "msg": "登录成功",
        "status": 200
    }
}
```

## 3. 游戏管理

### 3.1. 游戏列表数据

- 请求路径：games
- 请求方法：get
- 请求参数

| 参数名   | 参数说明     | 备注     |
| -------- | ------------ | -------- |
| query    | 查询参数     | 可以为空 |
| pagenum  | 当前页码     | 不能为空 |
| pagesize | 每页显示条数 | 不能为空 |

- 响应参数

| 参数名       | 参数说明     | 备注                                                    |
| ------------ | ------------ | ------------------------------------------------------- |
| total        | 总共商品条数 |                                                         |
| pagenum      | 当前商品页数 |                                                         |
| GAMEID       | 游戏ID       | 可展示，不可修改，后台专属，商家上架游戏时不需要展示    |
| TITLE        | 游戏名称     | 游戏的名称，商家上架时定义                              |
| PRICE        | 价格         | 游戏的价格，商家上架时定义，小数点后两位                |
| DISCOUNT     | 是否打折     | boolean，是否处于打折状态，商家可修改，0:不打折，1:打折 |
| AVERAGE_RATE | 游戏评分     | 玩家打分，可展示，不可修改，小数点后一位，满分5.0       |
| RELEASE_DATE | 发行日期     | 游戏发行的日期，商家上架时定义，"YYYY-MM-DD"            |
| PRE_ORDER    | 是否预售     | boolean，是否可预售，商家可修改，0:不可预定，1:可预定   |
| RATE_COUNT   | 评分数       | 评分数，可展示，不可修改                                |
| DISCRIPTION  | 游戏简介     | 游戏简介，商家上架时定义                                |
| COVER        | 封面图片     | 游戏封面图片，只有一张                                  |
| PICTURES     | 详情图片     | 游戏详情图片，可以有多张，详情页展示                    |
| CONSOLES     | 游戏平台     | 游戏平台，商家上架时选择，可修改，以"\|"分隔            |
| CATEGORY     | 游戏分类     | 分类，商家上架时选择，只能选一个，不可修改              |
| TAGS         | 标签         | 标签，商家上架时选择，可多选，可修改，以"\|"分隔        |

- 响应数据

```json
{
    "data": {
        "total": 1,
        "pagenum": "1",
        "games": [
            {
                "GAMEID": 12345,
                "TITLE": "SUPERMARIO",
                "PRICE": 19.90,
                "DISCOUNT": 1,
                "AVERAGE_RATE": 4.8,
                "RELEASE_DATE": "1985-09-13",
                "PRE_ORDER": 0,
                "RATE_COUNT": 100,
                "DISCRIPTION": "The game takes place in the Mushroom Kingdom, and Mario begins his new adventure in order to rescue Princess Peach, kidnapped by Kuba.",
                "COVER": "/tmp_uploads/30f08d52c551ecb447277eae232304b8",
              	"PICTURES": [
                  "/tmp_uploads/30f08d52c551ecb447277eae232304b1",
                  "/tmp_uploads/30f08d52c551ecb447277eae232304b2",
                  "/tmp_uploads/30f08d52c551ecb447277eae232304b3"
                ],
              	"CONSOLES": [
                  "PS4",
                  "PS Vita",
                  "Xbox one"
                ],
              	"CATEGORY": "Adventure",
              	"TAGS": [
                  "CARTOON",
                  "POPULAR"
                ]
            }
        ]
    },
    "meta": {
        "msg": "GET SUCCESS",
        "status": 200
    }
}
```

### 3.2. 上架游戏

- 请求路径：games
- 请求方法：post
- 请求参数

| 参数名       | 参数说明 | 备注                                                     |
| ------------ | -------- | -------------------------------------------------------- |
| TITLE        | 游戏名称 | 游戏的名称，商家上架时定义                               |
| PRICE        | 价格     | 游戏的价格，商家上架时定义，小数点后两位                 |
| DISCOUNT     | 是否打折 | boolean，是否处于打折状态，商家可修改，0:不打折，1:打折  |
| RELEASE_DATE | 发行日期 | 游戏发行的日期，商家上架时定义，"YYYY-MM-DD"             |
| PRE_ORDER    | 是否预售 | boolean，是否可预售，商家可修改，0:不可预定，1:可预定    |
| DISCRIPTION  | 游戏简介 | 游戏简介，商家上架时定义                                 |
| COVER        | 封面图片 | 游戏封面图片，只有一张                                   |
| PICTURES     | 详情图片 | 游戏详情图片，可以有多张，详情页展示，上传的图片临时路径 |
| CONSOLES     | 游戏平台 | 游戏平台，商家上架时选择，可修改                         |
| CATEGORY     | 游戏分类 | 分类，商家上架时选择，只能选一个，不可修改               |
| TAGS         | 标签     | 标签，商家上架时选择，可多选，可修改                     |

- 请求数据

```json
{
	"TITLE": "SUPERMARIO",
	"PRICE": 19.90,
	"DISCOUNT": 1,
	"RELEASE_DATE": "1985-09-13",
	"PRE_ORDER": 0,
	"DISCRIPTION": "The game takes place in the Mushroom Kingdom, and Mario begins his new adventure in order to rescue Princess Peach, kidnapped by Kuba.",
	"COVER": "/tmp_uploads/30f08d52c551ecb447277eae232304b8",
	"PICTURES": [
		"/tmp_uploads/30f08d52c551ecb447277eae232304b1",
		"/tmp_uploads/30f08d52c551ecb447277eae232304b2",
		"/tmp_uploads/30f08d52c551ecb447277eae232304b3"
	],
	"CONSOLES": [
		"PS4",
		"PS Vita",
		"Xbox one"
	],
	"CATEGORY": "Adventure",
	"TAGS": [
		"CARTOON",
		"POPULAR"
	]
}
```

- 响应参数

| 参数名       | 参数说明     | 备注                                                     |
| ------------ | ------------ | -------------------------------------------------------- |
| total        | 总共商品条数 |                                                          |
| pagenum      | 当前商品页数 |                                                          |
| GAMEID       | 游戏 ID      | 游戏上架时后台数据库自动创建                             |
| TITLE        | 游戏名称     | 游戏的名称，商家上架时定义                               |
| PRICE        | 价格         | 游戏的价格，商家上架时定义，小数点后两位                 |
| DISCOUNT     | 是否打折     | boolean，是否处于打折状态，商家可修改，0:不打折，1:打折  |
| RELEASE_DATE | 发行日期     | 游戏发行的日期，商家上架时定义，"YYYY-MM-DD"             |
| PRE_ORDER    | 是否预售     | boolean，是否可预售，商家可修改，0:不可预定，1:可预定    |
| DISCRIPTION  | 游戏简介     | 游戏简介，商家上架时定义                                 |
| COVER        | 封面图片     | 游戏封面图片，只有一张                                   |
| PICTURES     | 详情图片     | 游戏详情图片，可以有多张，详情页展示，上传的图片临时路径 |
| CONSOLES     | 游戏平台     | 游戏平台，商家上架时选择，可修改                         |
| CATEGORY     | 游戏分类     | 分类，商家上架时选择，只能选一个，不可修改               |
| TAGS         | 标签         | 标签，商家上架时选择，可多选，可修改                     |

- 响应数据

```json
{
	"data": {
		"TITLE": "SUPERMARIO",
		"PRICE": 19.90,
		"DISCOUNT": 1,
		"RELEASE_DATE": "1985-09-13",
		"PRE_ORDER": 0,
		"DISCRIPTION": "The game takes place in the Mushroom Kingdom, and Mario begins his new adventure in order to rescue Princess Peach, kidnapped by Kuba.",
		"COVER": "/tmp_uploads/30f08d52c551ecb447277eae232304b8",
		"PICTURES": [
			"/uploads/30f08d52c551ecb447277eae232304b1",
			"/uploads/30f08d52c551ecb447277eae232304b2",
			"/uploads/30f08d52c551ecb447277eae232304b3"
		],
		"CONSOLES": [
			"PS4",
			"PS Vita",
			"Xbox one"
		],
		"CATEGORY": "Adventure",
		"TAGS": [
			"CARTOON",
			"POPULAR"
		]
	},
	"meta": {
		"msg": "ADD GAME SUCCESS",
		"status": 201
	}
}
```

### 3.3. 下架游戏

- 请求路径：games/:id
- 请求方法：delete
- 请求参数

| 参数名 | 参数说明 | 备注                  |
| ------ | -------- | --------------------- |
| GAMEID | 游戏 ID  | 不能为空`携带在url中` |

- 响应数据

```json
{
	"data": null,
	"meta": {
		"msg": "DELETE SUCCESS",
		"status": 200
	}
}
```

## 4. 订单管理

### 4.1. 订单数据列表

- 请求路径：orders
- 请求方法：get
- 请求参数

| 参数名   | 参数说明     | 备注     |
| -------- | ------------ | -------- |
| query    | 查询参数     | 可以为空 |
| pagenum  | 当前页码     | 不能为空 |
| pagesize | 每页显示条数 | 不能为空 |

- 响应参数

| 参数名       | 参数说明     | 备注                                                         |
| ------------ | ------------ | ------------------------------------------------------------ |
| total        | 总共商品条数 |                                                              |
| pagenum      | 当前商品页数 |                                                              |
| ORDER_ID     | 订单ID       | 可展示，不可修改，订单被创建时后端自动生成                   |
| DATE         | 订单创建时间 | 可展示，不可修改，订单被创建时后端自动生成                   |
| ADDRESS      | 订单收货地址 |                                                              |
| CONSOLE_ID   | 游戏平台ID   | 订单游戏所选择平台对应的平台ID，可展示，不可修改             |
| CONSOLE_NAME | 订单游戏平台 | 订单游戏所选择的平台，可展示，不可修改                       |
| CONTACT_TEL  | 买家电话号码 | 买家的电话号码，可展示，可修改                               |
| STATUS       | 订单的状态   | 待付款 / 待发货 / 待收货 / 待评价 / 已完成 对应 0 / 1 / 2 / 3 / 4 |
| USER_ID      | 买家的用户ID |                                                              |
| USER_NAME    | 买家的用户名 |                                                              |
| GAMEID       | 游戏ID       | 可展示，不可修改，后台专属，商家上架游戏时不需要展示         |
| GAME_NAME    | 游戏名称     | 游戏的名称，商家上架时定义                                   |
| PRICE        | 价格         | 订单的价格，小数点后两位                                     |

- 响应数据

```json
{
	"data": {
		"total": 1,
		"pagenum": 1,
		"orders": [{
			"ORDER_ID": 47,
			"DATE": "2019 - 11 - 28",
			"ADDRESS": "上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区学生公寓20号楼",
			"CONSOLE_ID": 322,
			"CONSOLE_NAME": "PS4",
			"CONTACT_TEL": "17745644456",
			"STATUS": 0,
			"USER_ID": 9934258,
			"USER_NAME": "budi",
			"GAMEID": 2135329,
			"GAME_NAME": "SUPERMARIO",
			"PRICE": 19.90
		}]
	},
	"meta": {
		"msg": "GET SUCCESS",
		"status": 200
	}
}
```

### 4.2. 修改订单状态

- 请求路径：orders/:id
- 请求方法：put
- 请求参数

| 参数名      | 参数说明     | 备注                                                         |
| ----------- | ------------ | ------------------------------------------------------------ |
| ORDER_ID    | 订单 ID      | 不能为空`携带在url中`                                        |
| STATUS      | 订单的状态   | 待付款 / 待发货 / 待收货 / 待评价 / 已完成 对应 0 / 1 / 2 / 3 / 4 |
| PRICE       | 价格         | 订单的价格，小数点后两位                                     |
| ADDRESS     | 订单收货地址 |                                                              |
| CONTACT_TEL | 买家电话号码 | 买家的电话号码，可展示，可修改                               |

- 请求数据说明
  - 所有请求数据都是增量更新，如果参数不填写，就不会更新该字段

- 响应数据

```json
{
    "data": {
			"ORDER_ID": 47,
			"DATE": "2019 - 11 - 28",
			"ADDRESS": "上海市嘉定区安亭镇曹安公路4800号同济大学嘉定校区学生公寓20号楼",
			"CONSOLE_ID": 322,
			"CONSOLE_NAME": "PS4",
			"CONTACT_TEL": "17745644456",
			"STATUS": 0,
			"USER_ID": 9934258,
			"USER_NAME": "budi",
			"GAMEID": 2135329,
			"GAME_NAME": "SUPERMARIO",
			"PRICE": 19.90
		},
    "meta": {
        "msg": "PUT SUCCESS",
        "status": 200
    }
}
```

