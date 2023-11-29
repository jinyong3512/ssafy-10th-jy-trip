<template>
  <div class="d-flex">
    <TripList/>
    <KakaoMap />
  </div>
</template>

<script>
import KakaoMap from "../components/KaKaoMap.vue";
import TripList from "../components/TripList.vue";

export default {
  name: "AppTrip",
  components: {
    KakaoMap,
    TripList
},
};
</script>

<style>

</style>
