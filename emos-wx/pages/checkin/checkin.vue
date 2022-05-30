<template>
	<view>
		<!-- 引入取景框   device-position:采用前置还是后置摄像头 flash 关闭闪光灯  -->
		<camera device-position="front" flash="off" class="camera" @error="error" v-if="showCamera"></camera>
		<image mode="widthFix" class="image" :src="photoPath" v-if="showImage"></image>
		<view class="operate-container">
			<button type="primary" class="btn" @click="clickBtn()">{{ btnText }}</button>
			<button type="warn" class="btn" @click="afresh()" :disabled="cancheckin">重拍</button>
		</view>
		<view class="notice-container">
			<text class="notice">注意事项</text>
			<text class="desc">拍照签到的时候,必须要拍摄自己的正面照片,侧面照片回导致无法识别</text>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			cancheckin: false, //是否
			photoPath: '',
			btnText: '拍照',
			showCamera: true,
			showImage: false
		};
	},
	methods: {
		//拍照
		clickBtn: function() {
			console.log(111);
			let that = this;
			//判断是否是拍照文字
			if (that.btnText == '拍照') {
				let ctx = uni.createCameraContext();
				ctx.takePhoto({
					quality: 'high', //拍照质量为高清
					success: function(resp) {
						//拍照成功获取响应对象
						console.log(resp.tempImagePath);
						that.photoPath = resp.tempImagePath; //拍照路径
						that.showCamera = false; //取景框隐藏
						that.showImage = true; // 图片显示
						that.btnText = '签到';
					}
				});
			} else {
				// 如果为签到,那么则执行签到流程
				// TODO: 执行签到方法
			}
		},
		//重拍
		afresh: function() {
			console.log(111);
			let that = this;
			that.showCamera = true;
			that.showImage = false;
			that.btnText = '拍照';
		}
	}
};
</script>

<style lang="less">
@import url('checkin.less');
</style>
