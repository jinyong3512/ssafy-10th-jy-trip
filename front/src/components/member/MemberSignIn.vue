<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { $axios } from "@/utils/HttpCommons";

const router = useRouter();

const id = ref("");
const pw = ref("");
const nickname = ref("");
const email = ref("");
const check = ref(false);

function getRegister() {

    event.preventDefault();
    $axios
    .post(
      `/member/register`,
      JSON.stringify({ id: id.value, pw: pw.value, nickname: nickname.value, email: email.value })
    )
    .then(() => {
      alert('성공')
      router.push({ name: "main" });
    })
    .catch((error) => {
      alert(error.response.data.errorMessage);
    });
  
  
}
</script>

<template>
  <div class="SignIn-Box mx-auto">
    <form class="modal-body">
      <div class="mb-3">
        <label for="validationDefault01" class="form-label">아이디</label>
        <input type="text" class="form-control" id="validationDefault01" required v-model="id" />
      </div>
      <div class="mb-3">
        <label for="validationDefault02" class="form-label">비밀번호</label>
        <input type="text" class="form-control" id="validationDefault02" required v-model="pw" />
      </div>
      <div class="mb-3">
        <label for="validationDefault03" class="form-label">닉네임</label>
        <input
          type="text"
          class="form-control"
          id="validationDefault03"
          required
          v-model="nickname"
        />
      </div>
      <div class="mb-3">
        <label for="validationDefault04" class="form-label">이메일</label>
        <input type="text" class="form-control" id="validationDefault04" required v-model="email" />
      </div>
      <!-- <div class="col-md-4">
        <label for="validationDefaultUsername" class="form-label">닉네임</label>
        <div class="input-group">
          <input
            type="text"
            class="form-control"
            id="validationDefaultUsername"
            aria-describedby="inputGroupPrepend2"
            required
            v-model="nickname"
          />
        </div>
      </div> -->
      <!-- <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required />
          <label class="form-check-label" for="invalidCheck2"> 이용약관에 동의 </label>
        </div> -->
      
        <div class="modal-footer d-flex justify-content-center">
            <button class="btn btn-primary" @click="getRegister">회원가입</button>

        </div>
    </form>
  </div>
</template>

<style scoped>
.SignIn-Box {
  display: flex;
  width: 400px;
}
</style>
