<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { $axios } from "@/utils/HttpCommons";
import { useLoginStore } from "../../stores/login";

const login = useLoginStore();
const router = useRouter();
const subject = ref("");
const content = ref("");

function writeArticle() {
  $axios.defaults.headers.common["accessToken"] = `${sessionStorage.getItem("accessToken")}`;
  $axios.defaults.headers.common["refreshToken"] = "null";

  $axios
    .post(
      `/board/write`,
      JSON.stringify({
        subject: subject.value,
        content: content.value,
      })
    )
    .then((response) => {
      if (response.data.errorMessage === "accessTokenExpired") requestAccessToken(writeArticle);
      else if (response.data.errorMessage !== undefined) alert(response.data.errorMessage);
      else moveList();
    })
    .catch(() => {});
}

function requestAccessToken(function1) {
  $axios.defaults.headers.common["accessToken"] = `${sessionStorage.getItem("accessToken")}`;
  $axios.defaults.headers.common["refreshToken"] = `${sessionStorage.getItem("refreshToken")}`;

  $axios
    .post(`/member/requestNewAccessToken`)
    .then((response) => {
      if (response.data.errorMessage === "RefreshTokenExpired") {
        alert(response.data.errorMessage);
        sessionStorage.clear();
        login.login = false;
        router.push("/");
      } else if (response.data.errorMessage !== undefined) {
        alert(response.data.errorMessage);
      } else {
        sessionStorage.setItem("accessToken", response.data.accessToken);
        function1();
      }
    })
    .catch(() => {});
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <form @submit.prevent="writeArticle">
          <div class="mb-3">
            <label for="subject" class="form-label">제목 : </label>
            <input type="text" class="form-control" v-model="subject" placeholder="제목..." />
          </div>
          <div class="mb-3">
            <label for="content" class="form-label">내용 : </label>
            <textarea
              class="form-control"
              v-model="content"
              rows="10"
              placeholder="내용..."
            ></textarea>
          </div>
          <div class="col-auto text-center">
            <button type="submit" class="btn btn-outline-primary mb-3">글작성</button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="moveList">
              목록으로이동...
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
