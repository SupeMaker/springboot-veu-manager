<template>
  <div class="dashboard-container">
    <el-select
    v-model="roleForm.currentRoleName"
    filterable
    placeholder="请选择"
    remote
    :remote-method="query => {roleForm.searchName = query; getRoles()}"
    @change="handleCurrentRoleChange"
  >
      <el-option
      v-for="item in roleForm.roleList"
      :key="item.id"
      :label="item.name"
      :value="item.name">

      </el-option>
    </el-select>
    <!-- 重置、保存 -->
    <el-button type="primary" style="margin-left: 50px" @click="resetPermission">重置</el-button>
    <el-button type="primary" @click="savePermission">保存</el-button>
    <div style="height:20px"/>
    <el-table
    :data="permissionTree"
    style="margin-bottom: 20px;"
    row-key="id"
    border
    default-expand-all
    :tree-props="{children:'children', hasChildren:'hasChildren'}">
      <el-table-cloumn label="权限名称" width="300">
        <template slot-scope="scope">
          <el-checkbox
            v-model="scope.row.checked"
            :indeterminate="scope.row.indeterminate"
            style="margin-left: 5px; margin-right: 10px;"
            @change="handleCheckChange(scope.row)">
          </el-checkbox>
          <span v-if="scope.row.classify === 'platform'" style="font-weight: bold;">
            {{ scope.row.name }}
          </span>
          <span v-else>{{ scope.row.name }}</span>
        </template>
      </el-table-cloumn>
      <el-table-column label="权限代码" prop="code" width="200" />
      <el-table-column label="描述" prop="description" width="300" />
      <el-table-column label="创建时间" prop="createTime" width="200" />
  </el-table>
  </div>
</template>
<script>
export default {
  name: 'Permission',
  data() {
    return {
      roleForm: {
        searchName: '',
        roleList: [],
        currentRoleName: '',
        currentRole: {},
        loading: false
      },
      roleList: [],
      permissionTree: [{
        code:10001,
        description: '添加权限',
        createTime: '2024-08-18'
      }],
      permissionIdList: [],
      platformMap: {
        1: '展示平台',
        2: '管理平台'
      }
    }
  },
  mounted() {
    this.getRoles()
  },
  methods:{
    getRoles() {
      // this.roleForm.loading = true;
      // RoleApi.getRoles(this.roleForm.searchName).then((res) => {
      //   this.roleForm.roleList = res.data.data
      //   this.roleForm.total = res.data.data.length
      //   // 从页面params中获取roleId
      //   const roleId = this.$route.params.roleId
      //   if(roleId) {
      //     this.getPermissionOfRole(roleId)
      //     this.roleForm.currentRole = this.roleForm.roleList.find(item => item.id === roleId)
      //     this.roleForm.currentRoleName = this.roleForm.currentRole.name
      //     this.$route.params.roleId = undefined
      //   }
      // }).finally(() => {
      //   this.roleForm.loading = false
      // })
    },
  }
}
</script>
