const token = searchParam('token') // 토큰의 값

if (token){
    localStorage.setItem("access_token", token)
}

// 파라미터로 token이 넘어올 때 저장
function searchParam(key){
    return new URLSearchParams(location.search).get(key); // 쿼리 파라미터의 값 얻는 방법
}
/*
location.search: 현재 페이지 URL에서 쿼리 문자열을 가져옵니다.
new URLSearchParams(location.search): URLSearchParams 객체를 생성하여 쿼리 문자열을 파싱합니다.
params.get(key): URLSearchParams 객체에서 특정 키(key)에 해당하는 값을 가져옵니다.
 */