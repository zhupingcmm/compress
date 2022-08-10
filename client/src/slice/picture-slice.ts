import { createSlice } from "@reduxjs/toolkit";

interface State {
  pictureIds: Number[];
}

interface Action {
  type?: string;
  payload: State;
}

export const initialState: State = {
  pictureIds: [],
};

export const pictureSlice = createSlice({
  name: "picture",
  initialState,
  reducers: {
    updatePictureIds(state: State, action: Action) {
      state.pictureIds = action.payload.pictureIds;
    },
  },
});
