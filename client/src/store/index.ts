import { pictureSlice } from "./../slice/picture-slice";
import { configureStore } from "@reduxjs/toolkit";

export const rootReducer = {
  picture: pictureSlice.reducer,
};

export const store = configureStore({
  reducer: rootReducer,
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
