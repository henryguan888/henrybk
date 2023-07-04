<template>
  <div id="chart" ref="chart" class="chart" style="height:500px;width:100%" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
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
    setOptions({ title, userList } = {}) {
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
              name: title,
              type: 'pie',
              radius: '50%',
              data: userList,
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
      this.chart.setOption(option)
    },
    
  }
}
</script>
