<template>
  <div className="chart-container">
    <div className="chart-group">
      <div className="chart" ref="barChart"></div>
      <div className="chart" ref="columnChart"></div>
    </div>
    <div className="chart-group">
      <div className="chart" ref="pieChart"></div>
      <div className="chart" ref="categoryPieChart"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";

export default {
  name: "ChartExample",
  data() {
    return {
      categoryData: [],
      bookData: [],
      userData: [],
      bookCategoryData: []
    };
  },
  mounted() {
    this.getCategoryData();
    this.getBookData();
    this.getUserData();
    this.getBookCategoryData();
  },
  methods: {
    async getCategoryData() {
      try {
        const response = await axios.get("/api/lend/countByCategory");
        this.categoryData = response.data;
        this.initPieChart();
      } catch (error) {
        console.error("获取分类数据失败", error);
      }
    },
    async getBookData() {
      try {
        const response = await axios.get("/api/lend/countByBookName");
        this.bookData = response.data;
        this.initColumnChart();
      } catch (error) {
        console.error("获取图书数据失败", error);
      }
    },
    async getUserData() {
      try {
        const response = await axios.get("/api/lend/countByUseridTop10");
        this.userData = response.data;
        this.initBarChart();
      } catch (error) {
        console.error("获取用户数据失败", error);
      }
    },
    async getBookCategoryData() {
      try {
        const response = await axios.get("/api/book/getBookNumByCategory");
        this.bookCategoryData = response.data;
        this.initCategoryPieChart();
      } catch (error) {
        console.error("获取图书分类数据失败", error);
      }
    },
    initBarChart() {
      const barChart = echarts.init(this.$refs.barChart);
      const top10Users = this.userData
          .sort((a, b) => b.borrow_count - a.borrow_count)
          .slice(0, 10);
      const option = {
        title: {
          text: "借阅次数最多的前10名用户",
          top: 20, // 将标题上移
          left: 'center'
        },
        tooltip: {},
        legend: {
          data: ["借阅次数"],
          top: 40 // 将图例上移
        },
        xAxis: {
          type: "category",
          data: top10Users.map(item => `用户${item.user_id}`)
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            name: "借阅次数",
            type: "bar",
            data: top10Users.map(item => item.borrow_count)
          }
        ]
      };
      barChart.setOption(option);
    },
    initColumnChart() {
      const columnChart = echarts.init(this.$refs.columnChart);
      const top5Books = this.bookData
          .sort((a, b) => b.count - a.count)
          .slice(0, 5);
      const option = {
        title: {
          text: "借阅次数最多的前5名图书",
          top: 20, // 将标题上移
          left: 'center'
        },
        tooltip: {},
        legend: {
          data: ["借阅次数"],
          top: 40 // 将图例上移
        },
        xAxis: {
          type: "category",
          data: top5Books.map(item => item.bookname)
        },
        yAxis: {
          type: "value"
        },
        series: [
          {
            name: "借阅次数",
            type: "bar",
            data: top5Books.map(item => item.count)
          }
        ]
      };
      columnChart.setOption(option);
    },
    initPieChart() {
      const pieChart = echarts.init(this.$refs.pieChart);
      const option = {
        title: {
          text: "图书分类借阅次数",
          top: 20, // 将标题上移
          left: 'center'
        },
        tooltip: {
          trigger: "item"
        },
        legend: {
          orient: "vertical",
          left: "left",
          top: 40 // 将图例上移
        },
        series: [
          {
            name: "图书分类",
            type: "pie",
            radius: "50%",
            data: this.categoryData.map(item => ({
              value: item.count,
              name: item.category
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      };
      pieChart.setOption(option);
    },
    initCategoryPieChart() {
      const categoryPieChart = echarts.init(this.$refs.categoryPieChart);
      const option = {
        title: {
          text: "图书分类统计",
          top: 20, // 将标题上移
          left: 'center'
        },
        tooltip: {
          trigger: "item"
        },
        legend: {
          orient: "vertical",
          left: "left",
          top: 40 // 将图例上移
        },
        series: [
          {
            name: "图书分类",
            type: "pie",
            radius: "50%",
            data: this.bookCategoryData.map(item => ({
              value: item.book_num,
              name: item.category
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      };
      categoryPieChart.setOption(option);
    }
  }
};
</script>

<style scoped>
.chart-container {
  position: fixed; /* 设置为固定定位 */
  top: 30px; /* 距离顶部 50px */
  right: 378px; /* 距离右侧 100px */
  flex-direction: column;
  align-items: center;
  padding: 20px; /* 增加容器内边距 */
  border-radius: 8px; /* 添加圆角效果 */
}

.chart-group {

  background: #d9e0ff;
  display: flex;
  justify: -content space-around;
  width: 100%;
  padding: 10px; /* 增加分组内边距 */

}

.chart {
  border-radius: 40px; /* 添加圆角效果 */
  width: 300px;
  height: 260px;
  background-color: white;
  margin: 20px; /* 增加图表间距 */
}






</style>