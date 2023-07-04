<template>
  <div class="chart-container">
    <div class="block">
      <span class="demonstration">数据日期：</span>
      <el-date-picker v-model="searchDate"  type="date" placeholder="选择日期" value-format="yyyy-MM-dd" @change="searchDateChange">
      </el-date-picker>
    </div>
    <div id="chart" ref="chart" class="chart" style="height:500px;width:100%" />
  </div>
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import moment from 'moment'

export default {
  mixins: [resize],
  props: {
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      searchDate:moment().format('YYYY-MM-DD'), // 查询日期
      chart: null, // Echarts图表
    }
  },
  watch: { 
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    // 初始化echarts
    initChart() {
      this.chart = echarts.init(document.getElementById('chart'), 'macarons')
      this.setOptions(this.chartData)
    },
    // 设置配置项
    setOptions({ title, xData, yData } = {}) {
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: ''
        },
        xAxis: {
          type: 'category',
          data: xData
        },
        yAxis: {
          type: 'value'
        },
        legend: {
          data: [title]
        },
        series: [
          {
            name: title,
            data:yData,
            type: 'line',
            smooth: true
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      this.chart.setOption(option)
    },
    // 数据日期改变事件
    searchDateChange() {
      if (this.searchDate !== null && this.searchDate !== '') {
        this.$emit('searchDateChange', this.searchDate)
      }
      this.setOptions(this.chartData)
    },
  }
}


</script>