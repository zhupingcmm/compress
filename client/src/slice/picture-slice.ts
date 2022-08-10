import { RootState } from "./../store/index";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface State {
  pictures: File[];
}

interface Action {
  type?: string;
  payload: State;
}

export const initialState: State = {
  pictures: [],
};

export const pictureSlice = createSlice({
  name: "picture",
  initialState,
  reducers: {
    addFile(state: State, action: PayloadAction<File>) {
      state.pictures.push(action.payload);
    },
  },
});

export const { addFile } = pictureSlice.actions;
export const pictureState = (state: RootState) => state.picture;
