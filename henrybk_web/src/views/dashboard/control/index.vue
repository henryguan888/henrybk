<template>
  <div class="dashboard-editor-container">
    <Carousel></Carousel>
    <panel-group @handleSetEcharts="handleSetEcharts" />
    <div class="chart-container">
      <pie-chart v-if="clickType === 'countUser'" :chart-data="echartsData" />
      <line-chart v-else :chart-data="echartsData" @searchDateChange="searchDateChange" />
      
    </div>
  </div>
</template>

<script>
import { getUserByRole, getLoginAndOperData } from '@/api/login'
import moment from 'moment'

import Carousel from './components/Carousel'
import PanelGroup from './components/PanelGroup'
import PieChart from './components/PieChart'
import LineChart from './components/LineChart'

const defaultData = {
  userList: [], // 角色用户列表
  title: '', // echarts名称
  xData: [], // x轴数据
  yData: [], // y轴数据
}
export default {
  name: 'DashboardAdmin',
  components: {
    Carousel,
    PanelGroup,
    PieChart,
    LineChart
  },
  data() {
    return {
      clickType: 'countUser', //点击类型
      echartsData: defaultData, //图表数据
      searchDate: moment().format('YYYY-MM-DD'), // 查询日期
    }
  },
  // 当页面加载时获取数据
  created() { //页面渲染之前执行
    this.fetchData()
  },
  methods: {
    // 调用api层获取数据库中的数据
    fetchData() {
      this.handleSetEcharts(this.clickType)
    },
    // 点击四个中的某个图标后触发事件
    handleSetEcharts(type) {
      this.clickType = type
      if(type === 'countUser') {
        this.getUserByRole()
      } else {
        this.getLoginAndOperData(this.searchDate)
      }
    }, 
    // 点击数据日期时触发事件
    searchDateChange(searchDate) {
      this.getLoginAndOperData(searchDate)
    },
    // 获取饼状图数据
    getUserByRole() {
      getUserByRole().then(resp => {
        this.echartsData.title = '角色用户数'
        this.echartsData.userList = resp.data.list
        this.echartsData.xData = []
        this.echartsData.yData = []
      })
    },
    // 获取折线图数据
    getLoginAndOperData(searchDate) {
      getLoginAndOperData(searchDate).then(resp => {
        this.echartsData = defaultData
        this.echartsData.title = this.clickType === 'countLogin' ? '日活跃数' : '日操作数'
        this.echartsData.userList = []
        this.echartsData.xData = resp.data.dateList
        this.echartsData.yData = this.clickType === 'countLogin' ? resp.data.loginList : resp.data.operList
      })
    }
    
    
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
