<script setup>
import { RouterView } from "vue-router";
import TheHeaderNavbar from "../src/components/TheHeaderNavbar.vue";
import { useLoginStore } from "./stores/login";

const login = useLoginStore();
</script>

<template>
  <div>
    <TheHeaderNavbar :key="login.login" />
    <router-view></router-view>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "bm";
  src: url("@/font/BMHANNAPro.ttf");
}

* {
  font-family: "bm";
}
</style>
