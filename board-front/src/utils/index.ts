export const convertUrlToFile = async (url: string) => {
    const response = await fetch(url);
    const data = await response.blob();
    const extend = url.split('.').pop(); // 확장자 추출
    const fileName = url.split('/').pop(); // url의 마지막 부분을 파일이름으로

    const meta = { type: `image/${extend}`}; // MIME 타입을 설정하기 위한 객체를 지정
    return new File([data], fileName as string, meta); // blob 기반 파일 객체 생성.
};

export const convertUrlsToFile = async (urls: string[]) => {
    const files: File[] = [];
    for (const url of urls) {
        const file = await convertUrlToFile(url);
        files.push(file);
    }
    return files;
}