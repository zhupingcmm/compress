import React from "react";
import { render } from "react-dom";
import { App } from "@src/app";
import "./style/index.scss";
import { Provider } from "react-redux";
import { store } from "@src/store";

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);
