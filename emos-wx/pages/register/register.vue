<template>
	<view>
		<image src="../../static/logo-2.png" mode="widthFix" class="logo"></image>
		<view class="register-container">
			<input type="text" placeholder="请输入您的邀请码" class="register-code" maxlength="6" v-model="registerCode" />
			<view class="register-desc">管理员创建员工证账号之后,你可以从你的个人邮箱中注册邀请码</view>
			<button class="register-bth" open-type="getUserInfo" @tap="register()">执行注册</button>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			registerCode:'',
		}
	},
	methods: {
		register: function(){
			//前端数据验证
			let that = this   // this指当前函数
			if(that.registerCode == null || that.registerCode.length == 0){
				uni.showToast({
					icon:"none",
					title:"邀请码不能为空"
				})
				return
			}else if(/^[0-9]{6}$/.test(that.registerCode) == false){
				uni.showToast({
					icon:"none",
					title:"邀请码必须是6位数字"
				})
				return
			}

			uni.login({
				provider:'weixin',
				success: function(resp){
					// 获取用户临时授权字符串
					let code = resp.code
					// 获取 用户头像地址和微信昵称
					uni.getUserInfo({
						provider: 'weixin',
						success:function(resp){
							let nickName = resp.userInfo.nickName  //昵称
							let avararUrl = resp.userInfo.avatarUrl //头像
							let data={   //封装提交数据
								code:code,
								nickname: nickName, // 用户昵称
								ptoto: avararUrl,  //头像地址
								registerCode: that.registerCode //邀请码
							}
							//发送Ajax请求
							that.ajax(that.url.register,'POST',data,function(resp){
								let permission = resp.data.permission
								// 保存用户权限到sessionStorage
								uni.setStorageSync("permission",permission)
								console.log(permission)
								//跳转到index页面
								uni.switchTab({
									url:"../index/index"
								})
							})
						}
					})
				}
			})
		}
	}
}
</script>

<style lang="less">
@import url('register.less');
</style>
