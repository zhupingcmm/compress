import React from "react";
import { Button, message } from "antd";
import { Upload } from "@src/components/upload/Uploads";
import { useHttp } from "./utils/http";
import { Header } from "@src/components/header";
import { RoutesComponent } from "./routes";
export const App = () => {
  return (
    <div>
      <RoutesComponent />
      {/* <Header/>
            <Upload/>
            <Button href="http://localhost:8091/picture/1" type="link" >download</Button> */}
    </div>
  );
};
