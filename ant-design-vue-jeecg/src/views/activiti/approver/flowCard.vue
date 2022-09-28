<template>
  <div v-loading="contentLoading" class="main-home" element-loading-background="rgba(0, 0, 0, 0)">
    <div
        name="createflow"
        class="card upload"
        @click="forwardtoapprover"
      >
        <div class="upload-text app">
          <i class="el-icon-upload"/>
          <div><em>点击新建流程</em></div>
        </div>
    </div>
    <div v-for="item in processList" :key="item.id" class="card app">
      <img
          style="cursor:pointer;"
          alt="图标"
          width="180"
          height="180"
          :src="`http://localhost:8080/jeecg-boot/activiti/models/export?id=${item.id}`"
          @click="exportdiagram(item)">
      <div class="app-name">{{item.name}}</div>
      <table>
        <tr>
          <td>模型key:</td>
          <td>{{item.processKey}}</td>
        </tr>
        <tr>
          <td>最新版本:</td>
          <td>{{item.version}}</td>
        </tr>
      </table>
      <div class="app-btn">
        <el-button size="mini" icon="el-icon-edit" round @click="initBasicSetting(item)">基础</el-button>
        <el-button size="mini" icon="el-icon-c-scale-to-original" round @click="initProcess(item)">流程</el-button>
        <el-button size="mini" icon="el-icon-view" round style="margin-left: 10px" @click="initForm(item)">表单</el-button>
        <!-- <a-popconfirm
          title="是否确认删除?"
          @confirm="deletelc(1,item)"
          @cancel="deletelc(0)"
          okText="Yes"
          cancelText="No"
        >
          <el-button size="mini" icon="el-icon-view" round style="margin-left: 10px">删除</el-button>
        </a-popconfirm> -->
      </div>
    </div>

    <!-- 基础信息修改 -->
    <a-modal
      title="编辑-基础设置"
      :visible="basicSettingObj.visible"
      width="90%"
      style="top: 20px;"
      @ok="editBasicSetting"
      @cancel="basicSettingObj.visible = false"
    >
      <BasicSetting
        ref="basicSetting" 
        :conf="basicSettingObj"
        v-show="true" 
        tabName="basicSetting" 
      />
    </a-modal>

    <!-- 流程信息修改 -->
    <a-modal
      title="编辑-流程信息"
      :visible="processObj.visible"
      width="90%"
      style="top: 20px;"
      @ok="editProcess"
      @cancel="processObj.visible = false"
    >
      <Flowable  
        ref="processDesign"
        v-show="true" 
        :deploymentid="processObj.deploymentId"/>
    </a-modal>

    <!-- 表单信息修改 -->
    <a-modal
      title="编辑-表单信息"
      :visible="formObj.visible"
      width="90%"
      style="top: 20px;"
      @ok="editForm"
      @cancel="formObj.visible = false"
    >
      <DynamicForm
        ref="formDesign"
        v-show="true"
        tabName="formDesign" />
    </a-modal>

    <!--查看图片-->
    <el-dialog 
      :title="viewTitle" width="90%"
      :visible.sync="viewImage" :footer="null"
    >
      <div style="min-height: 400px">
        <img :src="diagramUrl" :alt="viewTitle">
      </div>
    </el-dialog>
  </div>

</template>

<script>
  import Vue from 'vue'
  import { ACCESS_TOKEN } from '@/store/mutation-types'
  import { getAction } from '@/api/manage'
  import BasicSetting from '@/components/BasicSetting'
  import Flowable from "@/components/Flowable";
  import DynamicForm from "@/components/FormGenerator/index/Home";

  export default {
    components: {BasicSetting, DynamicForm, Flowable},
    data() {
      return {
        //模型流程图
        viewImage:false,
        viewTitle:"",
        diagramUrl:"",
        contentLoading: true,
        
        /*流程设计器连接*/
        iframUrl:"",

        basicSettingObj: {
          visible: false,
          confirmLoading: false,
          flowName: '',
          flowImg: '',
          flowGroup: undefined,
          flowRemark: undefined,
          initiator: null
        },
        processObj: {
          visible: false,
          confirmLoading: false,
          deploymentId: "",
        },
        formObj: {
          visible: false,
          confirmLoading: false,
        },        
        activeFlow: undefined,
        processList: [],
        url: {
          list: "/activiti_process/listData",
          img: "/activiti/models/export",
          updateInfo: "/activiti_process/updateInfo",
        },
      };
    },
    mounted() {
      this.getProcessList();
    },
    methods: {
      getProcessList() {
        this.contentLoading = true;
        this.getAction(this.url.list,{}).then(res => {
          if (res.success) {
            this.processList = res.result||[];
          }else {
            this.$message.warning(res.message)
          }
        }).finally(()=>this.contentLoading = false);
      },
      exportdiagram(record) {
        this.viewTitle = "流程图片预览(" + record.name + ")";
        this.diagramUrl = `http://localhost:8080/jeecg-boot/activiti/models/export?id=${record.id}`;
        this.viewImage = true;
      },
      initBasicSetting(item) {
        this.basicSettingObj.visible = true
        this.basicSettingObj.flowName = item.name
        this.basicSettingObj.flowImg = ''
        this.basicSettingObj.flowGroup = item.categoryId
        this.basicSettingObj.flowRemark = item.description
        this.basicSettingObj.initiator = item.roles
        this.activeFlow = item
      },
      editBasicSetting() {
        var _this = this
        //判断基础信息是否填写完整 
        const getCmpData = name => _this.$refs[name].getData()
        const p1 = getCmpData('basicSetting') 

        //动态表单字段数是否大于1条
        let basic = _this.$refs.basicSetting.formData
        console.log(basic)

        let formData = {
          id: this.activeFlow.id,
          name: basic.flowName,
          categoryId: basic.flowGroup,
          roles: basic.initiator,
          description: basic.flowRemark
        }

        this.basicSettingObj.confirmLoading = true;
        this.postFormAction(this.url.updateInfo,formData).then(res => {
          if (res.success) {
            _this.$message.success("操作成功");
            _this.getProcessList();
            _this.basicSettingObj.visible = false;
          }else {
            _this.$message.error(res.message);
          }
        }).finally(() => _this.basicSettingObj.confirmLoading = false);
      },
      initProcess(item) {
        this.processObj.visible = true
        this.processObj.deploymentId = item.deploymentId
        console.log(this.processObj);
      },
      editProcess(item) {
        
      },
      initForm(item) {
        this.formObj.visible = true
      },
      editForm(item) {
        
      },      
      /*删除模型*/
      deletelc(y,row) {
        console.log(y,row);
        if (y){
          getAction(this.url.delete+row.id).then((res) => {
            if (res.success){
              this.$message.success(res.message);
            }else {
              this.$message.error(res.message);
            }
            this.getProcessList();
          });
        }
      },
      forwardtoapprover() {
        let token = Vue.ls.get(ACCESS_TOKEN)
        let url = `http://localhost:3000/approver/${token}`
        window.open(url)
      },
      
    },
  };
</script>

<style lang="scss" scoped>
@import "../style/variables";

.main-home {
  font-size: 20px;
  display: flex;
  flex-wrap: wrap;
  // width: 1020px;
  margin: 0 auto;
  padding: 20px 50px;

  .card {
    margin: 12px;
    width: 22.5%;
    height: 430px;
    background-color: #fff;
    padding: 0;
    transition: all .25s;

    i {
      transition: all .25s;
    }

    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 15px 30px rgba(0, 0, 0, .1);

      .app-name {
        color: #4a4a4a;
      }

      i {
        transform: scale(1.2);
      }
    }
  }

  .upload {
    background-color: $color-primary;
    cursor: pointer;

    i {
      color: white;
      font-size: 85px;
      margin-bottom: 25px;
    }

    .upload-text {
      color: white;
      font-size: 17px;
      margin-top: 135px;
    }
  }

  .app {
    position: relative;
    box-sizing: border-box;
    padding: 50px 45px 45px 45px;

    .app-name {
      font-size: 18px;
      margin: 25px 0;
    }

    table {
      width: 100%;
      font-size: 12px;

      td {
        padding: 4px 0;
      }

      td:last-child {
        padding-left: 10px;
        color: #1A1A1A;
        word-break: break-all;
      }
    }

    .app-btn {
      width: 100%;
      position: absolute;
      text-align: center;
      bottom: 30px;
      left: 0;
    }
  }
}
</style>
<style lang="scss">
.main-home {
  .card {
    .el-upload, .el-upload-dragger {
      width: 100%;
      height: 100%;
    }

    .el-upload-dragger {
      background-color: transparent;
      border: none;
    }

    .el-upload-dragger.is-dragover {
      background-color: rgba(0, 0, 0, 0.3);
      border-radius: 0;
    }
  }
}
</style>