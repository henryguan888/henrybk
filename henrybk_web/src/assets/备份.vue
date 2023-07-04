<template>
  <div class="dashboard-container">
    <div class="block">
      <el-carousel trigger="click" height="200px">
        <el-carousel-item v-for="item in imageList" :key="item">
          <el-image  :src="item" fit="scale-down" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="dashboard-text">
      <el-row :gutter="40" class="panel-group">
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="handleSetEcharts('countUser')" >
            <div class="card-panel-icon-wrapper icon-countUser">
              <svg-icon icon-class="peoples" />
            </div>
            <div class="card-panel-description">
              <el-statistic  class="card-panel-text" group-separator=","  :value="initForm.countUser" title="用户量"></el-statistic>
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="handleSetEcharts('countLogin')" >
            <div class="card-panel-icon-wrapper icon-countLogin">
              <i class="el-icon-user-solid" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <el-statistic  class="card-panel-text" group-separator=","  :value="initForm.countLogin" title="总活跃量"></el-statistic>
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="handleSetEcharts('countOper')" >
            <div class="card-panel-icon-wrapper icon-countOper">
              <i class="el-icon-user-solid" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <el-statistic  class="card-panel-text" group-separator=","  :value="initForm.countOper" title="总操作量"></el-statistic>
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel" @click="handleSetEcharts('countTask')" >
            <div class="card-panel-icon-wrapper icon-countTask">
              <i class="el-icon-user-solid" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <el-statistic  class="card-panel-text" group-separator=","  :value="initForm.countTask" title="定时任务量"></el-statistic>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="chart-container">
    <div class="block" v-if="showDate">
      <span class="demonstration">数据日期：</span>
      <el-date-picker v-model="searchDate"  type="date" placeholder="选择日期" value-format="yyyy-MM-dd" @change = "searchDateChange">
      </el-date-picker>
    </div>
        <div id="chart" ref="chart" class="chart" style="height:500px;width:100%"/>
    </div>

  </div>
</template>

<script>

import { getSystemData, getUserByRole, getLoginAndOperData } from '@/api/login'
import commonapi from '@/api/common'
import * as echarts from 'echarts'
import moment from 'moment'

export default {
  data() {
    return {
      imageList: [], //轮播图列表
      initForm: {countUser: 0,countLogin:0,countOper:0,countTask:0,}, //统计数据
      userList: [], // 角色用户列表
      
      clickType: '', //点击类型
      showDate: false, // 是否显示日期查询框
      searchDate: moment().format('YYYY-MM-DD'), // 查询日期
      title: '', // echarts名称
      xData: [], // x轴数据
      yData: [], // y轴数据
    };
  },
  // 当页面加载时获取数据
  created() { //页面渲染之前执行
    this.getDictList()
    this.fetchData()
  },

  methods: {
    // 获取字典数据
    getDictList() {
      commonapi.getConfigList("CAROUSEL_URL").then(resp => {
        this.imageList = resp.data.list
      })
    },
    // 调用api层获取数据库中的数据
    fetchData() {
      getSystemData().then(resp => {
        this.initForm = resp.data
      })
      this.handleSetEcharts('countUser')
    },
    // 点击图标触发事件
    handleSetEcharts(type) {
      switch (type) {
        case "countUser":
          this.showDate = false;
          this.clickType = 'countUser'
          this.showChartPie();
          break;
        case "countLogin":
          this.showDate = true;
          this.clickType = 'countLogin'
          this.showChartLine(this.clickType);
          break;
        case "countOper":
          this.showDate = true;
          this.clickType = 'countOper'
          this.showChartLine(this.clickType);
          break;
        default:
          break;
      }
    },
    // 选择查询日期触发事件
    searchDateChange() {
      if (this.searchDate !== null && this.searchDate !== '') {
        this.showChartLine(this.clickType)
      }
    },
    // 显示饼状图
    showChartPie() {
      getUserByRole().then(resp => {
        this.title = '用户角色数据';
        this.userList = resp.data.list
        // 基于准备好的dom，初始化echarts实例
        // 检测是否已经存在echarts实例，如果不存在，则不再去初始化
        let myChart = echarts.getInstanceByDom(
          document.getElementById('chart')
        )
        
        // 如果为空 则正常进行渲染 反之 不再进行初始化 
        if (myChart == null) {
          myChart = echarts.init(document.getElementById('chart'), null, { renderer: 'svg' })
        }
        myChart.clear()
        // 指定图表的配置项和数据
        var option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: '50%',
              data: this.userList,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option)
      })
    },
    // 显示折线图
    showChartLine(type) {
      getLoginAndOperData(this.searchDate).then(resp => {
        this.title = type === 'countLogin' ? '日活跃数' : '日操作数'
        this.xData = resp.data.dateList
        this.yData = type === 'countLogin' ? resp.data.loginList : resp.data.operList
        // 基于准备好的dom，初始化echarts实例
        // 检测是否已经存在echarts实例，如果不存在，则不再去初始化
        let myChart = echarts.getInstanceByDom(
          document.getElementById('chart')
        )
        // 如果为空 则正常进行渲染 反之 不再进行初始化 
        if (myChart == null) {
          myChart = echarts.init(document.getElementById('chart'), null, { renderer: 'svg' })
        }
        myChart.clear()
        // 指定图表的配置项和数据
        var option = {
          title: {
            text: ''
          },
          xAxis: {
            type: 'category',
            data: this.xData
          },
          yAxis: {
            type: 'value'
          },
          legend: {
            data: [this.title]
          },
          series: [
            {
              name: this.title,
              data: this.yData,
              type: 'line',
              smooth: true
            }
          ]
        };        
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option)
      })
    },

  }
};

</script>


<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-countUser {
        background: #40c9c6;
      }

      .icon-countLogin {
        background: #36a3f7;
      }

      .icon-countOper {
        background: #f4516c;
      }

      .icon-countTask {
        background: #34bfa3
      }
    }

    .icon-countUser {
      color: #40c9c6;
    }

    .icon-countLogin {
      color: #36a3f7;
    }

    .icon-countOper {
      color: #f4516c;
    }

    .icon-countTask {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 300px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
