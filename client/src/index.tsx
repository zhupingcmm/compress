import React from "react";
import { render } from "react-dom";
import { App } from "@src/app";
import "./style/index.scss";
import { Provider } from "react-redux";
import { store } from "@src/store";
import { ReactNotifications } from 'react-notifications-component'

render(
  <Provider store={store}>
    <ReactNotifications/>
    <App />
  </Provider>,
  document.getElementById("root")
);
