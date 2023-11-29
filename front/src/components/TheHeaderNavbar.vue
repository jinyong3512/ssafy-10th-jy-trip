<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useLoginStore } from "../stores/login";

const login = useLoginStore();
const router = useRouter();
const nickname = ref("");

onMounted(() => {
  const accessToken = sessionStorage.getItem("accessToken"); // 여기에 실제 accessToken을 넣어주세요.

  if (accessToken != null) {
    // JWT는 세 부분으로 나뉘어 있으므로 '.'을 기준으로 나눠줍니다.
    const tokenParts = accessToken.split(".");

    const encodedPayload = tokenParts[1]; // 두 번째 부분이 payload입니다.
    const base64 = encodedPayload.replace(/-/g, "+").replace(/_/g, "/");
    const rawData = window.atob(base64);
    const utf8Decoder = new TextDecoder("utf-8");
    const decodedPayload = utf8Decoder.decode(
      new Uint8Array([...rawData].map((char) => char.charCodeAt(0)))
    );
    const payloadObject = JSON.parse(decodedPayload);

    nickname.value = payloadObject.nickname;
    login.login = true;
  }
});

function getLogout() {
  sessionStorage.clear();
  nickname.value = "";
  login.login = false;
  router.push("/");
}
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body">
    <div class="container-fluid">
      <router-link class="navbar-brand" :to="{ name: 'trip' }"
        ><img src="@/img/logo.png" alt="" style="height: 50px"
      /></router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <!-- <li class="nav-item">
            <router-link :to="{ name: 'trip' }" class="nav-link">관광지</router-link>
          </li> -->
          <li class="nav-item">
            <router-link class="nav-link" to="/board">게시판</router-link>
          </li>
        </ul>

        <div v-if="nickname === ''">
          <router-link :to="{ name: 'login' }" class="btn btn-primary">로그인</router-link>
        </div>
        <div v-else>
          {{ nickname }}님 안녕하세요
          <router-link class="btn btn-primary" :to="{ name: 'myPage' }">마이페이지</router-link>
          <button class="btn" @click="getLogout">로그아웃</button>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
button {
  margin: 10px;
}
</style>
