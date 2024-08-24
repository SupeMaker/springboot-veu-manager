<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
    <h2>首页</h2>
  </div>
</template>

<script>
// import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
    }
  }
}
</script>
