<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="12" >
            <a-form-model-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="name">
              <a-input v-model="model.name" placeholder="请输入名称" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" placeholder="请输入描述" ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="生效日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="beginDate">
              <j-date placeholder="请选择生效日期" v-model="model.beginDate" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="失效日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="endDate">
              <j-date placeholder="请选择失效日期" v-model="model.endDate" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="12" >
            <a-form-model-item label="优先级" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="priority">
              <a-input-number v-model="model.priority" placeholder="请输入优先级" style="width: 100%" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="套账因子行" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="salAccountItemTable.loading"
          :columns="salAccountItemTable.columns"
          :dataSource="salAccountItemTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise,VALIDATE_NO_PASSED } from '@/utils/JEditableTableUtil'
  import { JEditableTableModelMixin } from '@/mixins/JEditableTableModelMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'SalAccountForm',
    mixins: [JEditableTableModelMixin],
    components: {
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        model:{
            beginDate:"1971-01-01",
            endDate:"2050-01-01",
            priority:1,
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
           priority: [
              { required: false},
              { pattern: /^-?\d+$/, message: '请输入整数!'},
           ],
        },
        refKeys: ['salAccountItem', ],
        tableKeys:['salAccountItem', ],
        activeKey: 'salAccountItem',
        // 套账因子行
        salAccountItemTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '薪资因子',
              key: 'factorName',
              type: FormTypes.popup,
              popupCode:"factor_pop",
              destFields:"factorId,factorName,factorCatalog",
              orgFields:"id,name,catalog",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '因子种类',
              key: 'factorCatalog',
              type: FormTypes.select,
              dictCode:"salary_catalog",
              disabled:true,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '工资条序号',
              key: 'sequence',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:1,
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '工资条显示',
              key: 'display',
              type: FormTypes.checkbox,
              customValue: ['Y', 'N'],
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:"Y",
            },
            {
              title: '基数',
              key: 'base',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:0,
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '操作数a',
              key: 'firstOprand',
              type: FormTypes.sel_search,
              dictCode:"sal_factor,name,code",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '操作符',
              key: 'operator',
              type: FormTypes.select,
              dictCode:"salary_operator",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
            },
            {
              title: '操作数b',
              key: 'secondOprand',
              type: FormTypes.sel_search,
              dictCode:"sal_factor,name,code",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:'',
              validateRules: [{ pattern: "n", message: "${title}格式不正确" }],
            },
            {
              title: '系数',
              key: 'coefficient',
              type: FormTypes.inputNumber,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:1,
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '计算精度',
              key: 'accuracy',
              type: FormTypes.select,
              dictCode:"salary_accuracy",
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue:"四舍五入",
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },

            {
              title: 'factor_id',
              key: 'factor_id',
              type:"hidden"
            },

            {
              title: 'factor_name',
              key: 'factor_name',
              type:"hidden"
            },

            {
              title: 'factor_catalog',
              key: 'factor_catalog',
              type:"hidden"
            },
          ]
        },
        url: {
          add: "/account/salAccount/add",
          edit: "/account/salAccount/edit",
          queryById: "/account/salAccount/queryById",
          salAccountItem: {
            list: '/account/salAccount/querySalAccountItemByMainId'
          },
        }
      }
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
    },
    methods: {
      addBefore(){
        this.salAccountItemTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        this.$nextTick(() => {
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.salAccountItem.list, params, this.salAccountItemTable)
        }
      },
      //校验所有一对一子表表单
      validateSubForm(allValues){
          return new Promise((resolve,reject)=>{
            Promise.all([
            ]).then(() => {
              resolve(allValues)
            }).catch(e => {
              if (e.error === VALIDATE_NO_PASSED) {
                // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
                this.activeKey = e.index == null ? this.activeKey : this.refKeys[e.index]
              } else {
                console.error(e)
              }
            })
          })
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          salAccountItemList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },

    }
  }
</script>

<style scoped>
</style>