<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { $axios } from "@/utils/HttpCommons";

const router = useRouter();

const articles = ref([]);

onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  $axios
    .get(`/board/list`)
    .then(({ data }) => {
      articles.value = data.list;

      for (let article of articles.value) {
        // for...of 루프로 수정
        const dateString = article.registerTime; // 기존 코드에서 value 제거
        const date = new Date(dateString);

        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        const hours = String(date.getHours()).padStart(2, "0");
        const minutes = String(date.getMinutes()).padStart(2, "0");
        const seconds = String(date.getSeconds()).padStart(2, "0");

        const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        article.registerTime = formattedDate; // value를 직접 수정
      }
    })
    .catch((error) => {
      alert(error.response.data.errorMessage);
    });
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글목록</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <!-- <th scope="col">글번호</th> -->
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="text-center"
              v-for="article in articles"
              :key="article.articleNumber"
              :article="article"
            >
              <!-- <th scope="row">{{ article.articleNumber }}</th> -->
              <td class="text-start">
                <!-- <router-link
                  :to="{ name: 'article-view', params: { articleNumber: article.articleNumber } }"
                  class="article-title link-dark"
                >
                  {{ article.subject }}
                </router-link> -->
                {{ article.subject }}
              </td>
              <td>{{ article.nickname }}</td>
              <td>{{ article.registerTime }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
