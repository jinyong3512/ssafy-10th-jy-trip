import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: () => import("../views/AppTrip.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../components/member/MemberLogin.vue"),
    },
    {
      path: "/password",
      name: "password",
      component: () => import("../components/member/MemberPassword.vue"),
    },
    {
      path: "/signIn",
      name: "signIn",
      component: () => import("../components/member/MemberSignIn.vue"),
    },
    {
      path: "/trip",
      name: "trip",
      component: () => import("../views/AppTrip.vue"),
    },
    {
      path: "/myPage",
      name: "myPage",
      component: () => import("../views/AppMyPage.vue"),
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/AppBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        // {
        //   path: "view/:articleNumber",
        //   name: "article-view",
        //   component: () => import("@/components/board/BoardDetail.vue"),
        // },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        // {
        //   path: "modify/:articleNumber",
        //   name: "article-modify",
        //   component: () => import("@/components/board/BoardModify.vue"),
        // },
      ],
    },
  ],
});

export default router;
