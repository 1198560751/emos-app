import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
	...App
})
app.$mount()

// 封装全局变量
let baseUrl = "http://localhost:8080/emos-wx-api"
Vue.prototype.url = {
	register: baseUrl + "/user/register",
	login: baseUrl + "/user/login",
}


/**
 * @param {Object} url 请求地址
 * @param {Object} method 请求方式  GET/POST
 * @param {Object} data 请求数据
 * @param {Object} fun 匿名函数
 * 如果用户权限不够,则弹出提示信息
 * 如果后端出现异常,就提示异常信息
 * 如果后端验证令牌不正确,就提示消息
 * 如果后端正常处理请求,就判断响应中是否有Token,如果令牌刷新,就要在本地存储Token
 */
Vue.prototype.ajax = function(url, method, data, fun) {
	uni.request({
		"url": url,
		"method": method,
		"header": {
			// 用同步方法调用,异步方法需要再次success判断
			token: uni.getStorageSync('token')
		},
		"data": data,
		success: function(resp) {
			if (resp.statusCode == 401) { // 如果没有登录系统,就跳转到登录页面
				uni.redirectTo({
					url: '/pages/login/login.vue'
				})
			} else if (resp.statusCode == 200 && resp.data.code == 200) { //正常响应
				let data = resp.data
				if (data.hasOwnProperty("token")) { // 判断响应数据是否包含token
					let token = data.token;
					console.log(token)
					// 保存到Storage中
					uni.setStorageSync("token", token)
				}
				//执行匿名函数
				fun(resp)
			}
			//如果后端存在异常
			else {
				uni.showToast({ //使用面包屑给提示
					icon: "none", // 图标默认为对勾,需要取消图标 
					title: resp.data //打印响应数据
				})
			}
		}
	})
}
