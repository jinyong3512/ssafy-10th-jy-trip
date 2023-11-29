<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { $axios } from "@/utils/HttpCommons";
import { useLoginStore } from "../../stores/login";

const login = useLoginStore();
const router = useRouter();

const id = ref("");
const pw = ref("");

function loginHandler() {
  $axios
    .post(`/member/login`, JSON.stringify({ id: id.value, pw: pw.value }))
    .then(({ data }) => {
      sessionStorage.setItem("accessToken", data.accessToken);
      sessionStorage.setItem("refreshToken", data.refreshToken);
      login.login = true;
      router.push({ name: "main" });
    })
    .catch((error) => {
      alert(error.response.data.errorMessage);
    });
}
</script>

<template>
  <div class="d-flex">
    <div class="container w-25">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">로그인</h1>
        </div>
        <div class="modal-body">
          <form>
            <div class="mb-3">
              <label for="recipient-name" class="col-form-label">아이디</label>
              <input type="text" class="form-control" id="recipient-name" v-model="id" />
            </div>
            <div class="mb-3">
              <label for="message-text" class="col-form-label">비밀번호</label>
              <input class="form-control" id="inputPassword" type="password" v-model="pw" />
            </div>
          </form>
        </div>
        <div class="modal-footer d-flex justify-content-center">
          

          <button type="button" class="btn btn-primary" @click="loginHandler">로그인</button>
          <!-- <router-link :to="{ name: 'password' }" type="button" class="btn btn-danger"
            >비밀번호 찾기</router-link
          > -->
        </div>

        <div class="modal-footer d-flex justify-content-evenly">
          <router-link type="button" class="btn btn-danger" :to="{ name: 'signIn' }"
            >회원가입</router-link
          >
          <!-- <a
            href="https://kauth.kakao.com/oauth/authorize?client_id=e72716000d3f4bdfff6b1be157a55e2f&redirect_uri=http://localhost:8080/user/kakaoLogin&response_type=code"
            ><img src="@/img/kakao_login_medium_narrow.png"
          /></a> -->
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
