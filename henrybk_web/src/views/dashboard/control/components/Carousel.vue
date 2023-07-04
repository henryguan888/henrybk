<template>
  <div class="block">
    <el-carousel trigger="click" height="200px">
      <el-carousel-item v-for="item in imageList" :key="item">
        <el-image :src="item" fit="scale-down" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>

import commonapi from '@/api/common'

export default {
  data() {
    return {
      imageList: [], //轮播图列表
      
    };
  },
  // 当页面加载时获取数据
  created() { //页面渲染之前执行
    this.getDictList()
  },

  methods: {
    // 获取轮播图列表
    getDictList() {
      commonapi.getConfigList("CAROUSEL_URL").then(resp => {
        this.imageList = resp.data.list
      })
    }
  }
  
};

</script>