<template>
  <div id="main-container" class="container">
    <div class="col-md-9 w-100" style="float: none; margin: 0 auto">
      <!-- 중앙 content start -->
      <!-- <div class="container"> -->
        <div style="height: 30px"></div>
        <!-- 중앙 center content end -->
        <div class="col-md-9" style="float: none; margin: 0 auto">
          <!-- <div class="alert alert-primary text-center fw-bold" role="alert">전국 관광지 정보</div> -->
          <!-- 관광지 검색 start -->
          <form class="d-flex my-3" onsubmit="return false;" role="search">
            <select id="search-area" class="form-select me-2">
              <option value="0" selected>검색 할 지역 선택</option>
            </select>
            <select id="search-content-id" class="form-select me-2">
              <option value="0" selected>관광지 유형</option>
              <option value="12">관광지</option>
              <option value="14">문화시설</option>
              <option value="15">축제공연행사</option>
              <option value="25">여행코스</option>
              <option value="28">레포츠</option>
              <option value="32">숙박</option>
              <option value="38">쇼핑</option>
              <option value="39">음식점</option>
            </select>
            <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어" />
            <button id="btn-search" class="btn btn-outline-success" type="button" @click="search">검색</button>
          </form>
          <!-- kakao map start -->

          <div id="map" class="mt-3 w-auto" style="width: 100%; height: 600px"></div>
          <!-- kakao map end -->
          <div class="row">
            <table class="table table-striped" style="display: none">
              <thead>
                <tr>
                  <th>대표이미지</th>
                  <th>관광지명</th>
                  <th>주소</th>
                  <th>위도</th>
                  <th>경도</th>
                  <th></th>
                </tr>
              </thead>
              <tbody id="trip-list"></tbody>
            </table>
          </div>
          <!-- 관광지 검색 end -->
        </div>
      <!-- </div> -->
      <!-- 중앙 content end -->
    </div>
  </div>
</template>

<script setup>
import { ref, toRaw, watch, onMounted } from "vue";
import { useTripListStore } from "@/stores/tripList";

const tripStore = useTripListStore();

var map;
const positions = ref([]);
const markers = ref([]);

//지도 객체를 등록합니다.
//지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
const serviceKey = "xjHVHyU6txp6TjpGrcks6Vq376THXwdW41QFInF6lhK%2F2fpzqkhjMPQnjkjJCM10EP7BSvQ7%2FU8VPPyQ%2FeOGcw%3D%3D";
let areaUrl =
  'https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=' +
  serviceKey +
  '&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json';
fetch(areaUrl, { method: 'GET' })
  .then((response) => response.json())
  .then((data) => makeOption(data));

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // loadMarkers();
};

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src =
      "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=f8b1f9aae1adff2eb9c76f7999d84313&libraries=services,clusterer";
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
})

function makeList(data) {
  // 주어진 위경도로 지도 옮기는 함수
  document.querySelector('table').setAttribute('style', 'display: ;');
  console.log(data.response.body.items)
  let trips = data.response.body.items.item;
  
  let tripList = ``;

  positions.value.splice(0, positions.value.length);
  deleteMarkers()
  markers.value = [];
  trips.forEach((area) => {
    tripList += `
        <tr id="items">
          <td id="item" data="${area.title}"><img src="${area.firstimage}" width="100px"></td>
          <td>${area.title}</td>
          <td>${area.addr1} ${area.addr2}</td>
          <td id="mapy" data="${area.mapy}">${area.mapy}</td>
          <td id="mapx" data="${area.mapx}">${area.mapx}</td>
          <td><button id="addTripBtn" class="btn btn-success"
              firstimage="${area.firstimage}"
              title="${area.title}"
              addr1="${area.addr1}"
              addr2="${area.addr2}"
              mapy="${area.mapy}"
              mapx="${area.mapx}"
            >여행 추가</button></td>
        </tr>
      `;
    
    let markerInfo = {
      title: area.title,
      latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
    };
    positions.value.push(markerInfo);

  });
  document.getElementById('trip-list').innerHTML = tripList;

  document.querySelectorAll('#items').forEach((row, index) => {
    row.querySelector('#item').addEventListener('click', function () {
      let markerInfo = {
        title : row.querySelector('#item').getAttribute('data'),
        latlng: new kakao.maps.LatLng(
          row.querySelector('#mapy').getAttribute('data'),
          row.querySelector('#mapx').getAttribute('data')
        )
      }

      positions.value.splice(0, positions.value.length);
      positions.value.push(markerInfo)
      loadMarkers(trips)
    });
  });

  document.querySelectorAll('#addTripBtn').forEach((button, index) => {
    button.addEventListener('click', function () {
      let trip = {
        firstimage : button.getAttribute('firstimage'),
      title : button.getAttribute('title'),
      addr1 : button.getAttribute('addr1'),
      addr2 : button.getAttribute('addr2'),
      mapy : button.getAttribute('mapy'),
      mapx : button.getAttribute('mapx'),
      }

      tripStore.addTrip(trip)
      
    });
  });
  
  loadMarkers(trips)
}

const loadMarkers = (trips) => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);

    var iwContent = `<div style="padding:5px;">${position.title}</div>`;
    var iwRemoveable = true;
    // 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({
      content: iwContent,
      removable : iwRemoveable
    });


    kakao.maps.event.addListener(marker, "click", function () {
      infowindow.open(map, marker);

      //선택한거
      let tripList = ``;
      trips.forEach((area) => {
        if (area.title === position.title) {
          tripList += `
            <tr>
              <td id="item"><img src="${area.firstimage}" width="100px"></td>
              <td>${area.title}</td>
              <td>${area.addr1} ${area.addr2}</td>
              <td>${area.mapy}</td>
              <td>${area.mapx}</td>
              <td><button id="addTripBtn" class="btn btn-success"
              firstimage="${area.firstimage}"
              title="${area.title}"
              addr1="${area.addr1}"
              addr2="${area.addr2}"
              mapy="${area.mapy}"
              mapx="${area.mapx}"
            >여행 추가</button></td>
            </tr>
          `;
        }
      });
      // 지도 중심을 부드럽게 이동시킵니다
      // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
      map.setLevel(map.getLevel()-5)
      map.panTo(position.latlng)
      document.getElementById('trip-list').innerHTML = tripList;       
      document.querySelector('#addTripBtn').addEventListener('click', function () {
      let trip = {
        firstimage : document.querySelector('#addTripBtn').getAttribute('firstimage'),
      title : document.querySelector('#addTripBtn').getAttribute('title'),
      addr1 : document.querySelector('#addTripBtn').getAttribute('addr1'),
      addr2 : document.querySelector('#addTripBtn').getAttribute('addr2'),
      mapy : document.querySelector('#addTripBtn').getAttribute('mapy'),
      mapx : document.querySelector('#addTripBtn').getAttribute('mapx'),
      }

      tripStore.addTrip(trip)
      
    });
  });
      
      
      
  });


  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

function makeOption(data) {
  let areas = data.response.body.items.item;
  let sel = document.getElementById('search-area');
  areas.forEach((area) => {
    let opt = document.createElement('option');
    opt.setAttribute('value', area.code);
    opt.appendChild(document.createTextNode(area.name));

    sel.appendChild(opt);
  });
}

function moveCenter(lat, lng) {
  this.map.setCenter(new kakao.maps.LatLng(lat, lng));
}

function search() {
  const serviceKey = "xjHVHyU6txp6TjpGrcks6Vq376THXwdW41QFInF6lhK%2F2fpzqkhjMPQnjkjJCM10EP7BSvQ7%2FU8VPPyQ%2FeOGcw%3D%3D";
  let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;

  let queryString = `serviceKey=${serviceKey}&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
  let areaCode = document.getElementById('search-area').value;
  let contentTypeId = document.getElementById('search-content-id').value;
  let keyword = document.getElementById('search-keyword').value;

  if (parseInt(areaCode)) queryString += `&areaCode=${areaCode}`;
  if (parseInt(contentTypeId)) queryString += `&contentTypeId=${contentTypeId}`;

  let service = ``;
  if (keyword) {
    service = `searchKeyword1`;
    queryString += `&keyword=${keyword}`;
  } else {
    service = `areaBasedList1`;
  }
  let searchUrl = baseUrl + service + '?' + queryString;

  fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => makeList(data));
}


function changeSize(size) {
  const container = document.getElementById("map");
  container.style.width = `${size}px`;
  container.style.height = `${size}px`;
  toRaw(this.map).relayout();
}

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  width: 400px;
  height: 400px;
}

#main-container {

}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}
</style>
