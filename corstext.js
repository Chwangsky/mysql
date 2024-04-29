fetch('http://localhost:8080/file/f614be4d-29a3-4c88-a794-97b991896039.jpg')
  .then(response => {
    if (!response.ok) {
      throw new Error('Request failed:', response.status);
    }
    return response.blob(); // 이미지 데이터를 가져옵니다.
  })
  .then(imageBlob => {
    // 이미지 데이터를 사용하여 작업을 수행합니다.
    const imageUrl = URL.createObjectURL(imageBlob);
    const imgElement = document.createElement('img');
    imgElement.src = imageUrl;
    document.body.appendChild(imgElement);
  })
  .catch(error => {
    console.error(error);
    console.log("에러");
});