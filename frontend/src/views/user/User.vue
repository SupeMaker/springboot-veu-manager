<template>
  <div class="user-container">
    <el-form  :model="tableData.form" label-width="80px" :inline="true">
      <el-form-item label="用户名称">
        <el-input v-model="name" placeholder="请输入用户名称" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          class = "time-selection"
          v-model="minCreateTime"
          type="datetime"
          placeholder="起始时间"
        />
        <el-date-picker
          class = "time-selection"
          v-model="maxCreateTime"
          type="datetime"
          placeholder="截止时间"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-seach" size="mini" @click="getUserList">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 用户操作按钮 -->
    <div>
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleCreateUser">新增</el-button>
        <!-- 这里进行的是批量删除 -->
        <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleBatchDelete">删除</el-button>
        <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImportUser">导入</el-button>
    </div>
    <!-- table会按照tableData这个数组的长度来生成对应的行数，按照el-table-column来生成多少列 -->
    <el-table
      :data="tableData.list"
      @selection-change="val => tableData.selection = val"
      @sort-change="handleSortChange"
      >
      <el-table-column type="index" width="60"/>
      <el-table-column type="selection" width="50"/>
      <el-table-column width="50">
        <!-- template这里是固定用法 
            tableData是显示出来的数据，scope是table内部基于tableData生成出来的，
            通过scope.row.userName就可以读取每一行中的userName。
            scope.row代表的是当前行数据
        -->
        <template slot-scope="scope">
            <img :id="'avatar-'+scope.row.id" class="table-avatar"> 
        </template>
      </el-table-column>
      <!-- 
      使用prop="userName"可以将该列的数据绑定为tableData数组所对应的所有的对象的userName属性
      -->
      <el-table-column prop="userName" label="用户名" sortable="custom"/>
      <el-table-column prop="trueName" label="真实姓名" sortable="custom"/>
      <el-table-column prop="roleList" label="角色" sortable="custom"/>
      <el-table-column prop="createTime" label="创建时间" sortable="custom"/>
      <el-table-column prop="status" label="是否激活" sortable="custom" width="100">
        <template slot-scope="scope">
            <!-- 
            这里绑定到scope.row.status，即当前行的status属性。:active-value="1"和:inactive-value="0"分别定义了开关的激活和非激活状态对应的值。
            @change="handleSwitch(scope.row)"是一个事件监听器，当开关状态改变时，会调用handleSwitch方法，并传递当前行的数据作为参数。
            -->
            <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" @change="handleSwitch(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <!-- 这里进行的是单个用户删除 -->
            <el-button type="text" icon="el-icon-delete" size="mini" style="color:red" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>
    <!-- 分页
     @size-change="getUserList"：这是一个事件监听器，当每页显示的条目数发生变化时，会触发getUserList方法。这通常用于重新获取数据列表。
    @current-change="getUserList"：当当前页码发生变化时，触发getUserList方法，用于更新当前显示的数据页。
    :current-page.sync="tableData.pageNum"：使用.sync修饰符的v-bind指令，实现当前页码的双向绑定。这意味着当用户点击分页器上的页码时，tableData.pageNum的值会更新，反之亦然。
    :page-sizes="[10, 20, 30, 40]"：定义了每页可以显示的条目数的选项，用户可以从这个数组中选择每页显示的条目数。
    :page-size.sync="tableData.pageSize"：同样使用.sync修饰符的v-bind指令，实现每页显示条目数的双向绑定。
    layout="total, sizes, prev, pager, next, jumper"：定义了分页组件的布局。这里包括了总条目数显示(total)、每页条数选择器(sizes)、上一页按钮(prev)、页码列表(pager)、下一页按钮(next)和跳转到指定页码的输入框(jumper)。
    :total="tableData.total"：设置总条目数，这个值通常来源于从服务器获取的数据总数
    -->
    <el-pagination
      class="pagination"
      @size-change="getUserList"
      @current-change="getUserList"
      :current-page.sync="tableData.pageNum"
      :page-sizes="[10, 20, 30, 40]"
      :page-size.sync="tableData.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.total">
    </el-pagination>

    <!-- 用户编辑/创建窗口 -->
    <el-dialog
    class="user-edit-dialog" 
    :title="userEditForm.id ? '用户编辑' : '新增用户'"
    :visible.sync="userEditDialogVisable"
    width="50%"
    top="8vh"
    >
        <!-- 
        ref="userEditForm"：ref属性用于给表单组件注册引用信息。引用信息注册在父组件的$refs对象上。在这个例子中，表单组件的引用信息将注册为userEditForm。这允许开发者通过this.$refs.userEditForm访问到表单组件的实例。
        status-icon：这是一个属性，通常用于控制表单验证状态图标的显示。当设置为true时，表单验证失败时会在输入框旁边显示一个图标。
        :model="userEdiForm"：model属性用于表单数据的双向绑定。这里将表单的数据模型绑定到父组件的userEdiForm数据对象上。这意味着表单中的输入数据会与userEdiForm对象同步，反之亦然。
        label-width="80px"：这个属性用于设置表单内所有<el-form-item>组件的label标签的宽度。在这个例子中，标签宽度被设置为80像素。
        :rules="userEditForm.id ? userUpdateRules : userCreateRules"：这是使用v-bind指令的动态属性绑定。rules属性用于定义表单的验证规则。这里使用了三元运算符来决定使用哪一套验证规则：如果userEditForm.id存在（即表单数据对象具有id属性），则使用userUpdateRules作为验证规则；否则使用userCreateRules。这通常用于区分创建（新增）和更新操作的验证逻辑。
        -->
        <el-form 
        status-icon
        :model="userEditForm"
        label-width="80px"
        :rules="userEditForm.id ? userUpdateRules : userCreateRules"
        >
            <el-form-item label="用户名" prop="userName">
                <el-input v-model="userEditForm.userName" />
            </el-form-item>
            <el-form-item label="真实姓名" prop="trueName">
                <el-input v-model="userEditForm.trueName" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="userEditForm.password" />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="userEditForm.email" />
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="userEditForm.gender">
                    <el-radio :label="0">男</el-radio>
                    <el-radio :label="1">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="地址" prop="address">
                <el-input v-model="userEditForm.address" />
            </el-form-item>
            <el-form-item label="简介" prop="introduction">
                <el-input v-model="userEditForm.introduction" />
            </el-form-item>
            <el-form-item label="电话" prop="phone">
                <el-input v-model="userEditForm.phone" />
            </el-form-item>
            <el-form-item label="角色" prop="roleIds">
                <el-select v-model="userEditForm.roleIds" multiple placeholder="请选择角色">
                    <el-option v-for="role in allRoles" :key="role.id" :label="role.name" :value="role.id" />
                </el-select>
            </el-form-item>
            <!-- <el-form-item label="头像">
                <el-upload
                class="avatar-uploader"
                action=""
                :auto-upload="false"
                :show-file-list="false"
                :on-change="file => handleAvatarChange(file)"
                >
                    <img v-if="avatarUploadData.url" :src="avatarUploadData.url" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"/>
                </el-upload>
                <el-button v-if="avatarUploadData.raw" size="mini" @click="resetUploadData(false)">重置</el-button>
            </el-form-item> -->
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="userEditDialogVisable = false">取消</el-button>
            <el-button type="primary" @click="addOrUpdateUser">确定</el-button>
        </span>
    </el-dialog>
  </div>

</template>
<script>

import * as UserApi from '@/api/user'
import {getRoles} from '@/api/role'

export default {
  name: 'User',
  data() {
    return {
        tableData: {
            form: {
                name: '',
                minCreateTime: '',
                maxCreateTime: '',
            },
            list:
            [
                {
                    id: 1,
                    userName: 'supoMaker',
                    trueName: '王小虎',
                    roleList: [],
                    createTime: '2024-08-18',
                    status: true
                }
            ],
            selection: '',
            pageNum: 1,
            pageSize: 10,
            total: 24
        },
        userEditForm: {
            id: '',
            userName: '',
            trueName: '',
            password: '',
            email: '',
            gender: '',
            address: '',
            introduction: '',
            phone: '',
            roleIds: [],
            avatarUploadData:{
                url: '',
                raw: ''
            }
        },
        userEditDialogVisable:false,
        allRoles: [
            {
                id:1,
                name:"小明"
            }
        ],
        userCreateRules: {
            userName: [{ required:true, trigger: 'blur', validator: this.userNameValidator }],
            password: [{ required:true, trigger: 'change', validator: this.passwordValidator }],
            roleIds: [{ required:true, trigger: 'change', validator: this.roleValidator }],
        },
        userUpdateRules: {
            userName: [{ required:true, trigger: 'blur', validator: this.userNameValidator }],
            password: [{ required:true, validator: this.passwordValidator }],
            roleIds: [{ required:true, trigger: 'change', validator: this.roleValidator }],
        },
        // 当前编辑的行
        currentEditRow: {
            userName: null
        }
    }
  },
  mounted() {
    this.getUserList()
    this.getAllRoles()
  },
  methods: {
    handleCreateUser() {
        this.userEditForm.id="用户编辑"
        this.userEditDialogVisable=true;
    },
    // 批量删除用户
    handleBatchDelete() {
        // 将tableData.selection中的id提取出来，传递给handleDelete就可以
        
    },
    handleImportUser() {

    },
    getUserList() {
        UserApi.getUsers(this.tableData).then(res => {
            this.tableData.list = res.data.data.content
            this.tableData.total = res.data.data.totalElements
            // 更新头像
            // this.$nextTick(() => {
            //     this.tableData.list.forEach(row => {
            //         this.getAvatar(row.id, row)
            //     })
            // })
        })
    },
    // 切换用户状态，激活或失效
    handleSwitch() {

    },
    handleEdit(row) {
        this.currentEditRow = row
        for (const key in this.userEditForm) {
            this.userEditForm[key] = row[key]
        }
        this.userEditForm["id"]="编辑用户"
        this.userEditDialogVisable=true;
    },
    handleDelete(userIds) {
        // 弹出对话框
        this.$confirm('此操作将永久删除该用户,是否继续?', "提示", {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            //对接删除用户的接口
         })
    },
    userUpdateRules() {
        
    },
    userCreateRules() {

    },
    resetQuery() {
        this.tableData.userName = ''
        this.tableData.minCreateTime = ''
        this.tableData.maxCreateTime = ''
    },
    // 点击表格列，进行排序
    handleSortChange() {

    },

    // 用户名验证
    userNameValidator(rule, value, callback) {
        if(!value) {
            callback(new Error('请输入用户名'))
        } else if(this.userEditForm.id && value === this.currentEditRow.userName) {
            callback
        } else {
            // checkUserName(value).then(res => {
            //     callback(res.data.data ? new Error('用户名已存在'): undefined)
            // })
        }
    },
    // 密码验证函数
    passwordValidator(rule, value, callback) {
        if(!value && this.userEditForm.id) {
            callback()
        } else if(!value || value.length < 6) {
            callback(new Error('密码长度不能小于6位'))
        } else {
            callback()
        }
    },
    // 角色验证函数
    roleValidator(rule, value, callback) {
        if(!value || value.length === 0) {
            callback(new Error('角色不能为空'))
        } else {
            callback()
        }
    },
    addOrUpdateUser() {
        this.$refs.userEditForm.validate(valid => {
            if (valid) {
                // 如果验证通过就调用添加或更新用户的接口
            }
        })
    },
    getAllRoles() {
        getRoles().then(res => {
            this.allRoles = res.data.data
        })
    }
  }
}
</script>
<style scope>
.form-container{
    margin-top: 20px;
}
.time-selection{
    margin-right:10px;
    width: 20px
}
</style>
