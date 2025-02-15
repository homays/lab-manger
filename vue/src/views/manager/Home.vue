<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{ user?.name }}！欢迎使用本系统
    </div>

    <div style="display: flex; margin: 10px 0">
      <div style="width: 100%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">公告列表</div>
        <div>
          <el-timeline reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
        <div style="margin-top: 30px; display: flex">
          <div id="pie" style="height: 400px; flex: 1"></div>
          <div id="bar" style="height: 400px; flex: 1"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts"

let pieOptions = {
  title: {
    text: '', // 主标题
    subtext: '', // 副标题
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '', // 鼠标移上去显示内容
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        {value: 1048, name: '瑞幸咖啡'}, // 示例数据：name表示维度，value表示对应的值
        {value: 735, name: '雀巢咖啡'},
        {value: 580, name: '星巴克咖啡'},
        {value: 484, name: '栖巢咖啡'},
        {value: 300, name: '小武哥咖啡'}
      ]
    }
  ]
}
let barOptions = {
  title: {
    text: '', // 主标题
    subtext: '', // 副标题
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] // 示例数据：统计的维度（横坐标）
  },
  yAxis: {
    type: 'value'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130], // 示例数据：横坐标维度对应的值（纵坐标）
      type: 'bar',
      itemStyle: {
        normal: {
          color: function () {
            return "#" + Math.floor(Math.random() * (256 * 256 * 256 - 1)).toString(16);
          }
        },
      },
    }
  ]
}
export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
    this.loadPie()
    this.loadBar()
  },
  methods: {
    loadPie() {
      this.$request.get('/lab/pie').then(res => {
        if (res.code === '200') {
          let chartDom = document.getElementById('pie');
          let myChart = echarts.init(chartDom);
          pieOptions.title.text = res.data.text
          pieOptions.title.subtext = res.data.subtext
          pieOptions.series[0].name = res.data.name
          pieOptions.series[0].data = res.data.data
          myChart.setOption(pieOptions);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadBar() {
      this.$request.get('/lab/bar').then(res => {
        if (res.code === '200') {
          let chartDom = document.getElementById('bar');
          let myChart = echarts.init(chartDom);
          barOptions.title.text = res.data.text
          barOptions.title.subtext = res.data.subtext
          barOptions.xAxis.data = res.data.xAxis
          barOptions.series[0].data = res.data.yAxis
          myChart.setOption(barOptions);
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>
