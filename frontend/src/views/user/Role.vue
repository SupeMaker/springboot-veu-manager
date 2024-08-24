<template>
  <div class="dashboard-container">
    <el-form  :model="tableData" label-width="80px" :inline="true">
      <el-form-item label="搜索内容">
        <el-input v-model="searchContent" placeholder="请输入搜索内容" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-seach" size="mini" @click="getRoleList">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleCreateRole">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 用户操作按钮 -->
    <div>
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleCreateRole">新增</el-button>
        <!-- 这里进行的是批量删除 -->
        <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleBatchDelete">删除</el-button>
        <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImportRole">导入</el-button>
    </div>
    <!-- table会按照tableData这个数组的长度来生成对应的行数，按照el-table-column来生成多少列 -->
    <el-table
      :data="tableData.list"
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
      <el-table-column type="index" width="60"/>
      <el-table-column type="selection" width="50"/>
      <el-table-column prop="roleName" label="角色名称" sortable="custom"/>
      <el-table-column prop="description" label="角色描述" sortable="custom"/>
      <el-table-column prop="createTime" label="创建时间" sortable="custom"/>
      <el-table-column prop="updateTime" label="更新时间" sortable="custom"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <!-- 这里进行的是单个用户删除 -->
            <el-button type="text" icon="el-icon-delete" size="mini" style="color:red" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination
      class="pagination"
      @size-change="getRoleList"
      @current-change="getRoleList"
      :current-page.sync="tableData.pageNum"
      :page-sizes="[10, 20, 30, 40]"
      :page-size.sync="tableData.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.total">
    </el-pagination>

    <el-dialog
    class="user-edit-dialog" 
    :title="roleEditForm.id ? '角色编辑' : '新增角色'"
    :visible.sync="roleEditDialogVisable"
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
        :model="roleEditForm"
        label-width="80px"
        :rules="roleEditForm.id ? roleUpdateRules : roleCreateRules"
        >
            <el-form-item label="角色名称" prop="roleName">
                <el-input v-model="roleEditForm.roleName" />
            </el-form-item>
            <el-form-item label="角色描述" prop="description">
                <el-input v-model="roleEditForm.description" />
            </el-form-item>
            
           
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="roleEditDialogVisable = false">取消</el-button>
            <el-button type="primary" @click="addOrUpdateUser">确定</el-button>
        </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'Role',
  data() {
    return {
      tableData: {
            searchContent: '',
            list:
            [
                {
                    id: 1,
                    roleName: '系统管理员',
                    description: '描述',
                    createTime: '2024-08-18',
                    updateTime: '2024-08-18',
                }
            ],
            pageNum: 1,
            pageSize: 10,
            total: 24
        },
      roleEditForm: {
        id: '',
      },
      roleEditDialogVisable: false,
    }
  },
  methods:{
    getRoleList() {

    },
    handleCreateRole() {
      for(const key in this.roleEditForm) {
        this.roleEditForm[key] = ''
      }
      this.openRoleEditDialog()
    },
    //打开角色编辑窗口
    // 这是一个Vue实例方法，用于延迟回调的执行直到下次DOM更新循环之后。这意味着你可以在这个回调函数中访问更新后的DOM。
    openRoleEditDialog() {
      this.roleEditDialogVisable=true;
      this.$nextTick(() => {
        this.$refs.roleEditForm.clearValidate()
      })  
    },
    handleDelete(id) {
        // 弹出对话框
        this.$confirm('此操作将永久删除该角色,是否继续?', "提示", {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            //对接删除用户的接口
         }).catch(() => {
            this.$message.info("已取消删除")
         })
    },
  }

}
</script>
