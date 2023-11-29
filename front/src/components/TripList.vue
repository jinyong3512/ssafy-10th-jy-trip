<script setup>
import { useTripListStore } from "@/stores/tripList";

const tripList = useTripListStore();
</script>

<template>
  <div id="basket" class="container w-25 shadow-lg rounded">
    <div class="col-md-9 w-100" style="float: none; margin: 0 auto">
      <!-- 중앙 content start -->
      <!-- <div class="container"> -->
      <div style="height: 30px"></div>
      <!-- 중앙 center content end -->
      <div>
        <h3>여행 찜</h3>
        <button class="btn btn-primary" @click="tripList.saveTripList()">저장</button>
        <button class="btn btn-success" @click="tripList.getTripList()">불러오기</button>
      </div>
      <div v-for="trip in tripList.list" :key="trip.title">
        <div class="card mx-auto border-2 border-dark shadow-lg">
          <!-- <button type="button" class="btn-close" @click=" tripList.deleteTrip(trip.title)"></button> -->
          <img :src="trip.firstimage" class="card-img-top" onerror="this.style.display=`none`" />
          <div class="card-body">
            <h5 class="card-title">{{ trip.title }}</h5>
          </div>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">{{ trip.addr1 }}</li>
            <li class="list-group-item">{{ trip.addr2 }}</li>
          </ul>
          <!-- <div class="card-body">
            <a :href="naver.com/${trip.title}" class="card-link">Card link</a>
            <a href="#" class="card-link">Another link</a>
          </div> -->
        </div>
      </div>

      <!-- </div> -->
      <!-- 중앙 content end -->
    </div>
  </div>
</template>

<style setup>
.card {
  margin: 10px;
}
</style>
