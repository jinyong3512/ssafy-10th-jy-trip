import {ref} from 'vue';
import { defineStore } from 'pinia';
import { $axios } from "@/utils/HttpCommons";
import { useLoginStore } from "../stores/login";

const login = useLoginStore();

export const useTripListStore = defineStore('trip', () => {
  const list = ref([])

    function addTrip(trip) {
        let flag = true;
        for (let i = 0; i < list.value.length; i++) {
            if (trip.title === list.value[i].title) {
                flag = false;
            }
        }
        
        if (flag) {
            list.value.push(trip);
        }
    }

    function deleteTrip(title) {
        for (let i = 0; i < list.value.length; i++) {
            if (list.value[i].title === title) {
                list.value.splice(i, 1);
                break;
            }
        }
    }

    function getTripList() {

        $axios.defaults.headers.common["accessToken"] = `${sessionStorage.getItem("accessToken")}`;
        $axios.defaults.headers.common["refreshToken"] = "null";

        $axios.get('/trip/getTripList')
            .then((response) => {
                if (response.data.errorMessage === "accessTokenExpired") requestAccessToken(getTripList);
                else if (response.data.errorMessage !== undefined) alert(response.data.errorMessage);
                else { 
                    response.data.tripList.forEach(trip => {
                        list.value.push(trip)
                    });
                }
              })
              .catch(() => {});
    }

    //저장
    function saveTripList() {
        // if (list.value.length === 0) {
        //     alert("찜한 여행이 없잖아")
        //     return;
        // }

        // for (let i = 0; i < list.value.length; i++) {
        //     console.log(list.value[i]);
        // }

        $axios.defaults.headers.common["accessToken"] = `${sessionStorage.getItem("accessToken")}`;
        $axios.defaults.headers.common["refreshToken"] = "null";

        $axios.post('/trip/saveTripList', list.value)
            .then((response) => {
                if (response.data.errorMessage === "accessTokenExpired") requestAccessToken(saveTripList);
                else if (response.data.errorMessage !== undefined) alert(response.data.errorMessage);
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
    
    return {list, addTrip, deleteTrip, saveTripList, getTripList}
});
