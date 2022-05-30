<template>
	<view>
		<image src="../../static/logo-1.png" class="logo" mode="widthFix"></image>
		<view class="logo-title">EMOS企业在线办公系统</view>
		<view class="logo-subtitle">Ver 2050.2</view>
		<!-- 获取用户信息  open-type="getUserInfo" -->
		<button class="login-btn" open-type="getUserInfo" @tap="login()">登录系统</button>
		<view class="register-container">
			没有账号?
			<text class="register" @tap="toRegister">立即注册</text>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {};
	},
	methods: {
		toRegister() {
			uni.navigateTo({
				url: '../register/register'
			});
		},
		login: function() {
			let that = this
			uni.login({
				provider: 'weixin',
				success: function(resp) {
					let code = resp.code;
					that.ajax(that.url.login, 'POST', { "code": code } ,function(resp){
						// 从响应里面得到权限列表
						let permission = resp.permission
						uni.setStorageSync("permission",permission)
					});
					//跳转到主页
					uni.switchTab({
						url:"../index/index"
					})
				},
				fail:function(e){
					console.log(e)
					uni.showToast({
						icon:"none",
						title:"执行异常"
					})
				}
			});
		}
	}
};
</script>

<style lang="less">
@import url('./login.less');
</style>
