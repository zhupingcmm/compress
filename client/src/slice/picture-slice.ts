import { CompressFile } from "./../type/type.d";
import { RootState } from "./../store/index";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import type { UploadFile } from 'antd/es/upload/interface';
interface State {
  originPictures: Partial<UploadFile[]>;
  compressedPictures: UploadFile[];
  currentPicture: String | null
}

interface Action {
  type?: String;
  payload: State;
}

export const initialState: State = {
  originPictures: [],
  compressedPictures: [],
  currentPicture: null
};

export const pictureSlice = createSlice({
  name: "picture",
  initialState,
  reducers: {
    updatePictures(state: State, action: PayloadAction<UploadFile[]>) {
      state.originPictures = action.payload;
    },
    updateCurrentPicture(state: State, action: PayloadAction<String>) {
      state.currentPicture = action.payload;
    },
    removePicture(state: State, action: PayloadAction<String>){
      state.originPictures = state.originPictures.filter(p => p?.name !== action.payload)
    }
  },
});

export const {
  updatePictures,
  updateCurrentPicture,
  removePicture
} = pictureSlice.actions;
export const pictureState = (state: RootState) => state.picture;
