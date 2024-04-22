/**
 * 게시물 작성(board write)시,
 * 업로드 버튼은 header에 있으나 
 * 게시물 view는 BoardWrite에 있으므로
 * Board write의 상태를 전역으로 둠
 */


import { create } from 'zustand';

interface BoardStore {
    title: string;
    content: string;
    boardImageFileList: File[];
    setTitle: (title: string) => void;
    setContent: (content: string) => void;
    setBoardImageFileList: (boardImageFileList: File[] ) => void;
    resetBoard: () => void;
};

const useBoardStore = create<BoardStore>(set => ({
    title: '',
    content: '',
    boardImageFileList: [],
    setTitle: (title) => set(state => ({...state, title})),
    setContent: (content) => set(state => ({...state, content})),
    setBoardImageFileList: (boardImageFileList) => set(state => ({...state, boardImageFileList})),
    resetBoard: () => set(state => ({...state, title: '', content: '', boardImageFileList: []}))
}));

export default useBoardStore;