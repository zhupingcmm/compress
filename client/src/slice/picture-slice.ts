// import { UploadFile } from '@src/type';
import { RootState } from "./../store/index";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UploadFile } from "antd/lib/upload/interface";

interface State {
  pictures: UploadFile[];
  currentPicture: Partial<UploadFile> | null
}

interface Action {
  type?: string;
  payload: State;
}

export const initialState: State = {
  pictures: [],
  currentPicture: null
};

export const pictureSlice = createSlice({
  name: "picture",
  initialState,
  reducers: {
    addFile(state: State, action: PayloadAction<UploadFile>) {
      state.pictures.push(action.payload);
    },
    updateFiles(state: State, action: PayloadAction<UploadFile[]>) {
      state.pictures = action.payload;
    },
    updateCurrentFile(state: State, action: PayloadAction<Partial<UploadFile>>){
      state.currentPicture = action.payload
    },
    removePicture(state: State, action: PayloadAction<String>) {
      state.pictures = state.pictures.filter(p => p.uid != action.payload);
    }

  },
});

export const { addFile, updateFiles, updateCurrentFile, removePicture } = pictureSlice.actions;
export const pictureState = (state: RootState) => state.picture;
