import { CompressFile } from "./../type/type.d";
import { RootState } from "./../store/index";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface State {
  pictures: CompressFile[];
  currentPicture: Partial<CompressFile> | null;
  compressedPictures: CompressFile[];
}

interface Action {
  type?: string;
  payload: State;
}

export const initialState: State = {
  pictures: [],
  currentPicture: null,
  compressedPictures: [],
};

export const pictureSlice = createSlice({
  name: "picture",
  initialState,
  reducers: {
    addFile(state: State, action: PayloadAction<CompressFile>) {
      state.pictures.push(action.payload);
    },
    updateFiles(state: State, action: PayloadAction<CompressFile[]>) {
      state.pictures = action.payload;
    },
    updateCurrentFile(
      state: State,
      action: PayloadAction<Partial<CompressFile>>
    ) {
      state.currentPicture = action.payload;
    },
    removePicture(state: State, action: PayloadAction<String>) {
      state.pictures = state.pictures.filter((p) => p.uid != action.payload);
    },
    // updateCompressedPictures(
    //   state: State,
    //   action: PayloadAction<CompressFile[]>
    // ) {
    //   state.compressedPictures = action.payload;
    // },
  },
});

export const {
  addFile,
  updateFiles,
  updateCurrentFile,
  removePicture,
  // updateCompressedPictures,
} = pictureSlice.actions;
export const pictureState = (state: RootState) => state.picture;
